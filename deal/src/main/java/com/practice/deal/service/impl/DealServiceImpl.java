package com.practice.deal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.deal.model.*;
import com.practice.deal.model.enums.ApplicationStatus;
import com.practice.deal.repository.ApplicationRepository;
import com.practice.deal.repository.ClientRepository;
import com.practice.deal.service.DealService;
import com.practice.deal.service.util.ApplicationHistoryStatusUtil;
import com.practice.deal.service.util.ClientUtil;
import com.practice.deal.service.util.ScoringDataFactory;
import com.practice.deal.web.dto.CreditDto;
import com.practice.deal.web.exception.NotFoundEntityException;
import com.practice.deal.web.mapper.CreditMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DealServiceImpl implements DealService {

    @Value("${url.conveyor}")
    private String conveyorUrl;

    private final ClientUtil clientUtil;
    private final ScoringDataFactory scoringDataFactory;
    private final ApplicationHistoryStatusUtil applicationStatusUtil;
    private final RestTemplate restTemplate;
    private final CreditMapper creditMapper;

    private final ApplicationRepository applicationRepository;
    private final ClientRepository clientRepository;

    @Override
    public LoanOffer calculateOffers(LoanApplication loanApplication) {
        Client client = clientRepository.findByPassportSeriesAndNumber(
                loanApplication.getPassportSeries(), loanApplication.getPassportNumber());

        if (client == null) {
            client = clientUtil.initClient(loanApplication);
        }

        Application application = new Application();
        application.setClient(client);
        Application updatedStatusHistoryApplication = applicationStatusUtil
                .changeStatusHistory(application, ApplicationStatus.PREAPPROVAL);
        Application savedApplication = applicationRepository.save(updatedStatusHistoryApplication);

        loanApplication.setId(savedApplication.getId());

        return preCalculateLoan(loanApplication);
    }

    @Override
    public void chooseOffer(LoanOffer offer) {
        Application application = getApplication(offer.getApplicationId());

        application.setLoanOffer(offer);
        Application updatedStatusHistoryApplication = applicationStatusUtil
                .changeStatusHistory(application, ApplicationStatus.APPROVED);

        applicationRepository.save(updatedStatusHistoryApplication);
    }

    @Override
    public void calculateCredit(UserExtendDetails details, Long applicationId) {
        Application application = getApplication(applicationId);
        ScoringData scoringData = scoringDataFactory.getScoringData(application, details);
        Employment employment = details.getEmployment();

        Client client = application.getClient();
        Client fullClient = clientUtil.fillAdditionalClientInfo(client, details, employment);
        clientRepository.save(fullClient);

        try {
            CreditDto creditDto = calculateLoan(scoringData);

            application.setCredit(creditMapper.toEntity(creditDto));
            Application updatedStatusHistoryApplication = applicationStatusUtil
                    .changeStatusHistory(application, ApplicationStatus.CC_APPROVED);
            applicationRepository.save(updatedStatusHistoryApplication);

        } catch (Exception e) {
            log.error(e.toString());
            Application updatedStatusHistoryApplication = applicationStatusUtil
                    .changeStatusHistory(application, ApplicationStatus.CC_DENIED);
            applicationRepository.save(updatedStatusHistoryApplication);
        }
    }

    private LoanOffer preCalculateLoan(LoanApplication loanApplication) {
        String url = conveyorUrl + "/offers";
        var response = restTemplate.postForEntity(url, loanApplication, LoanOffer.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Fail precalculate");
        }

        return response.getBody();
    }

    private CreditDto calculateLoan(ScoringData scoringData) {
        String url = conveyorUrl + "/calculation";

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<ScoringData> entity = new HttpEntity<>(scoringData, headers);
        var response = restTemplate.postForEntity(url, entity, CreditDto.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Fail precalculate");
        }

        return response.getBody();
    }

    private Application getApplication(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new NotFoundEntityException("Application with id " + applicationId + " not exist"));
    }
}
