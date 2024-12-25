package com.practice.deal.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
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
public class LoanApplicationDto {

    private Long id;

    @NotNull
    @Min(10000)
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

    @NotBlank
    @Pattern(regexp = "[\\w\\.]{2,50}@[\\w\\.]{2,20}",
            message = "Incorrect email")
    private String email;

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
    private Boolean isInsuranceEnabled;

    @NotNull
    private Boolean isSalaryClient;

}
