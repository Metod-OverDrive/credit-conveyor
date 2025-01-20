package com.practice.conveyor.service.impl;

import com.practice.conveyor.model.LoanApplication;
import com.practice.conveyor.model.LoanOffer;
import com.practice.conveyor.model.LoanProperties;
import com.practice.conveyor.service.LoanOfferService;
import com.practice.conveyor.service.LoanPropertiesService;
import com.practice.conveyor.service.utils.LoanCalcUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanOfferServiceImpl implements LoanOfferService {

    private final LoanPropertiesService loanPropertiesService;
    private final LoanCalcUtil loanCalcUtil;

    @Override
    public LoanOffer preScoringLoan(LoanApplication application) {
        LoanProperties props = loanPropertiesService.getActualProperties();
        Integer term = application.getTerm();
        Boolean isInsuranceEnabled = application.getIsInsuranceEnabled();

        BigDecimal rate = calcRate(isInsuranceEnabled, application.getIsSalaryClient(), props);

        BigDecimal amountWithInsurance = calcAmountWithInsurance(application.getAmount(), isInsuranceEnabled, props);
        BigDecimal monthlyPayment = loanCalcUtil.calculateMonthlyPayment(
                term,
                amountWithInsurance,
                rate
        );

        BigDecimal totalPayment = calculateTotalPayment(monthlyPayment, term);

        LoanOffer offer = LoanOffer.builder()
                .applicationId(application.getId())
                .requestedAmount(application.getAmount())
                .term(term)
                .monthlyPayment(monthlyPayment)
                .totalAmount(totalPayment)
                .rate(rate)
                .isInsuranceEnabled(application.getIsInsuranceEnabled())
                .isSalaryClient(application.getIsSalaryClient())
                .build();

        log.info(offer.toString());

        return offer;
    }

    public BigDecimal calcRate(Boolean isInsuranceEnabled, Boolean isSalaryClient, LoanProperties props) {
        BigDecimal monthlyPayment = new BigDecimal(props.getGlobalRate());

        if (isInsuranceEnabled && isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(props.getInsuranceRate()))
                    .subtract(new BigDecimal(props.getSalaryRate()));

        } else if (isInsuranceEnabled) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(props.getInsuranceRate()));

        } else if (isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(props.getSalaryRate()));
        }

        return monthlyPayment;
    }

    private BigDecimal calcAmountWithInsurance(BigDecimal amount, Boolean isInsuranceEnabled, LoanProperties props) {
        return isInsuranceEnabled ? amount.add(props.getInsurancePrice()) : amount;
    }

    private BigDecimal calculateTotalPayment(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(new BigDecimal(term)).setScale(2, RoundingMode.HALF_EVEN);
    }


}
