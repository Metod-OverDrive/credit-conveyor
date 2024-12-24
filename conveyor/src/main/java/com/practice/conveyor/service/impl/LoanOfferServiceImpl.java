package com.practice.conveyor.service.impl;

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

    @Value("${loan.global-rate}")
    private Integer GLOBAL_RATE;
    @Value("${loan.insurance-price}")
    private BigDecimal INSURANCE_PRICE;
    @Value("${loan.insurance-rate}")
    private Integer INSURANCE_RATE;
    @Value("${loan.salary-rate}")
    private Integer SALARY_CLIENT_RATE;

    private final LoanCalcUtil loanCalcUtil;

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
        BigDecimal monthlyPayment = new BigDecimal(GLOBAL_RATE);

        if (isInsuranceEnabled && isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(INSURANCE_RATE))
                    .subtract(new BigDecimal(SALARY_CLIENT_RATE));

        } else if (isInsuranceEnabled) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(INSURANCE_RATE));

        } else if (isSalaryClient) {
            monthlyPayment = monthlyPayment
                    .subtract(new BigDecimal(SALARY_CLIENT_RATE));
        }

        return monthlyPayment;
    }

    private BigDecimal calcAmountWithInsurance(BigDecimal amount, Boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(INSURANCE_PRICE) : amount;
    }

    private BigDecimal calculateTotalPayment(BigDecimal monthlyPayment, Integer term) {
        return monthlyPayment.multiply(new BigDecimal(term)).setScale(2, RoundingMode.HALF_EVEN);
    }


}
