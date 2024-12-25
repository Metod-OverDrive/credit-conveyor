package com.practice.deal.model;

import com.practice.deal.model.enums.Gender;
import com.practice.deal.model.enums.MaritalStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ScoringData {

    private BigDecimal amount;
    private Integer term;
    private String name;
    private String surname;
    private String middleName;
    private LocalDate birthday;
    private String passportSeries;
    private String passportNumber;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private Integer dependentAmount;
    private String bankAccount;
    private Boolean isInsuranceEnabled;
    private Boolean isSalaryClient;

    private Gender gender;
    private Employment employment;
    private MaritalStatus maritalStatus;
}
