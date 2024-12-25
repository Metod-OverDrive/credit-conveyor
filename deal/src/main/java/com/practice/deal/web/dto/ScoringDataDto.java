package com.practice.deal.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.deal.model.enums.Gender;
import com.practice.deal.model.enums.MaritalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ScoringDataDto {

    @Min(10000)
    @NotNull
    private BigDecimal amount;

    @Min(6)
    @NotNull
    private Integer term;

    @NotBlank
    @Size(min = 2, max = 30, message = "Name must be contain from 2 to 30 character")
    private String name;

    @NotBlank
    @Size(min = 2, max = 30, message = "Surname must be contain from 2 to 30 character")
    private String surname;

    @NotBlank
    @Size(min = 2, max = 30, message = "Middle name must be contain from 2 to 30 character")
    private String middleName;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "Passport series must be contain 4 character")
    private String passportSeries;

    @NotBlank
    @Pattern(regexp = "\\d{6}", message = "Passport number must be contain 6 character")
    private String passportNumber;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @NotBlank
    private String passportIssueBranch;

    @NotNull
    private Integer dependentAmount;

    @NotBlank
    @Pattern(regexp = "\\d{20}", message = "Account must be contain 20 digital")
    private String bankAccount;

    @NotNull
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;

    @NotNull
    private Gender gender;

    @NotNull
    @Valid
    private EmploymentDto employment;

    @NotNull
    private MaritalStatus maritalStatus;
}
