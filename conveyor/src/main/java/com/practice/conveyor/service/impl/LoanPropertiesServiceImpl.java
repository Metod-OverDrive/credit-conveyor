package com.practice.conveyor.service.impl;

import com.practice.conveyor.model.LoanProperties;
import com.practice.conveyor.service.LoanPropertiesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class LoanPropertiesServiceImpl implements LoanPropertiesService {

    @Value("${url.loan-properties}")
    private String URL_LOAN;

    private final RestTemplate restTemplate;

    @Override
    @Cacheable(value = "actualLoanPropertes", key = "'loanKey'")
    public LoanProperties getActualProperties() {
        String url = URL_LOAN + "/actual";
        var response = restTemplate.getForEntity(url, LoanProperties.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(
                    "Loan properties server return response with wrong status: " + response.getStatusCode());
        }

        var properties = response.getBody();

        if (properties == null || properties.getGlobalRate() == null) {
            throw new RuntimeException(
                    "Loan properties server return not correct result " + properties);
        }

        return properties;
    }

}
