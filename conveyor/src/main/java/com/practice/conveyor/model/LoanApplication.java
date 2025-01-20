package com.practice.conveyor.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
