package com.practice.loanproperties.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Loan_properties")
@Getter
@Setter
public class LoanProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "global_rate")
    private Integer globalRate;

    @Column(name = "insurance_price")
    private BigDecimal insurancePrice;

    @Column(name = "insurance_rate")
    private Integer insuranceRate;

    @Column(name = "salary_rate")
    private Integer salaryRate;

    @Column(name = "min_client_age")
    private Long minClientAge;

    @Column(name = "max_client_age")
    private Long maxClientAge;

    @Column(name = "min_total_work_experience")
    private Integer minTotalWorkExperience;

    @Column(name = "min_current_work_experience")
    private Integer minCurrentWorkExperience;

    @Column(name = "salary_quantity")
    private Integer salaryQuantity;

    @Column(name = "min_man_age_for_special_rate")
    private Long minManAgeForSpecialRate;

    @Column(name = "max_man_age_for_special_rate")
    private Long maxManAgeForSpecialRate;

    @Column(name = "min_woman_age_for_special_rate")
    private Long minWomanAgeForSpecialRate;

    @Column(name = "max_woman_age_for_special_rate")
    private Long maxWomanAgeForSpecialRate;

    @Column(name = "monthly_payment_start_day")
    private Integer monthlyPaymentStartDay;
}
