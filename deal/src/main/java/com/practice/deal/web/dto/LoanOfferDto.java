package com.practice.deal.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class LoanOfferDto {

    @NotNull
    private Long applicationId;
    @Positive
    private BigDecimal requestedAmount;
    @Positive
    private BigDecimal totalAmount;
    @Positive
    private Integer term;
    @Positive
    private BigDecimal monthlyPayment;
    @Positive
    private BigDecimal rate;
    @NotNull
    private Boolean isInsuranceEnabled;
    @NotNull
    private Boolean isSalaryClient;

}
