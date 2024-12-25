package com.practice.deal.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.deal.model.enums.Gender;
import com.practice.deal.model.enums.MaritalStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserExtendDetailsDTO {

    @NotNull
    private Gender gender;

    @NotNull
    private MaritalStatus maritalStatus;

    @PositiveOrZero
    private Integer dependentAmount;

    @NotNull
    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate passportIssueDate;

    @NotBlank
    private String passportIssueBranch;

    @Valid
    @NotNull
    private EmploymentDto employment;

    @NotBlank
    @Pattern(regexp = "\\d{20}", message = "Счет должен содержать 20 цифр")
    private String account;

}
