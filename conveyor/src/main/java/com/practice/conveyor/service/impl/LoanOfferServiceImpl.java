package com.practice.conveyor.service.impl;

import com.practice.conveyor.config.properties.LoanProperties;
import com.practice.conveyor.model.LoanApplication;
import com.practice.conveyor.model.LoanOffer;
import com.practice.conveyor.service.LoanOfferService;
import com.practice.conveyor.service.utils.LoanCalcUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanOfferServiceImpl implements LoanOfferService {

    private final LoanCalcUtil loanCalcUtil;
    private final LoanProperties loanProperties;

    @Override
    public LoanOffer preScoringLoan(LoanApplication application) {
        Integer term = application.getTerm();
        Boolean isInsuranceEnabled = application.getIsInsuranceEnabled();

        BigDecimal rate = calcRate(isInsuranceEnabled, application.getIsSalaryClient());

        BigDecimal monthlyPayment = loanCalcUtil.calculateMonthlyPayment(
                term,
                calcAmountWithInsurance(application.getAmount(), isInsuranceEnabled),
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

    private BigDecimal calcRate(Boolean isInsuranceEnabled, Boolean isSalaryClient) {
        BigDecimal monthlyPayment = new BigDecimal(loanProperties.getGlobalRate());

        if (isInsuranceEnabled && isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(loanProperties.getInsuranceRate()))
                    .subtract(new BigDecimal(loanProperties.getSalaryRate()));

        } else if (isInsuranceEnabled) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(loanProperties.getInsuranceRate()));

        } else if (isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(loanProperties.getSalaryRate()));
        }

        return monthlyPayment;
    }

    private BigDecimal calcAmountWithInsurance(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(loanProperties.getInsurancePrice()) : amount;
    }

    private BigDecimal calculateTotalPayment(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(new BigDecimal(term)).setScale(2, RoundingMode.HALF_EVEN);
    }


}
