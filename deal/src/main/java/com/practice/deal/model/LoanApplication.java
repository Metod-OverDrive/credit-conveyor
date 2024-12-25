package com.practice.deal.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class LoanApplication {

    private Long id;
    private BigDecimal amount;
    private Integer term;
    private String name;
    private String surname;
    private String middleName;
    private String email;
    private LocalDate birthday;
    private String passportSeries;
    private String passportNumber;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

}
