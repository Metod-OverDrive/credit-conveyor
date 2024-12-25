package com.practice.deal.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoanOffer {

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
