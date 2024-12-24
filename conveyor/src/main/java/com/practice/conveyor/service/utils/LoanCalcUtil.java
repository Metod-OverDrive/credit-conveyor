package com.practice.conveyor.service.utils;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LoanCalcUtil {

    private static final String MONTHS_IN_YEAR = "12";
    private static final String NUMBER_PERCENTAGE = "0.01";
    private static final String ONE_RATIO = "1";

    public BigDecimal calculateMonthlyPayment(Integer term, BigDecimal amount, BigDecimal rate) {

        BigDecimal ratePerMonth = rate.divide(
                new BigDecimal(MONTHS_IN_YEAR), 5, RoundingMode.HALF_UP
        );

        BigDecimal numericRatePerMonth = ratePerMonth.multiply(
                new BigDecimal(NUMBER_PERCENTAGE)
        );

        BigDecimal pow = (new BigDecimal(ONE_RATIO)
                .add(numericRatePerMonth))
                .pow(term);

        BigDecimal annuityRate = numericRatePerMonth
                .multiply(pow)
                .divide((pow.subtract(new BigDecimal(ONE_RATIO))),
                        5, RoundingMode.HALF_UP
                );

        return amount.multiply(annuityRate).setScale(2, RoundingMode.HALF_EVEN);
    }

}
