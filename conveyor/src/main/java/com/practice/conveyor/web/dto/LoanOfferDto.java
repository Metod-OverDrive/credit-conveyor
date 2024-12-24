package com.practice.conveyor.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class LoanOfferDto {

    private Long applicationId;
    private BigDecimal requestedAmount;
    // Total sum with insurance
    private BigDecimal totalAmount;
    // Loan term in months
    private Integer term;
    private BigDecimal monthlyPayment;
    private BigDecimal rate;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

}
