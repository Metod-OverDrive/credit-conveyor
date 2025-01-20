package com.practice.conveyor.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoanProperties implements Serializable {

    private Long id;
    private LocalDateTime updateTime;
    private Integer globalRate;
    private BigDecimal insurancePrice;
    private Integer insuranceRate;
    private Integer salaryRate;
    private Long minClientAge;
    private Long maxClientAge;
    private Integer minTotalWorkExperience;
    private Integer minCurrentWorkExperience;
    private Integer salaryQuantity;
    private Long minManAgeForSpecialRate;
    private Long maxManAgeForSpecialRate;
    private Long minWomanAgeForSpecialRate;
    private Long maxWomanAgeForSpecialRate;
    private Integer monthlyPaymentStartDay;
}
