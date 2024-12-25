package com.practice.deal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practice.deal.model.enums.Gender;
import com.practice.deal.model.enums.MaritalStatus;
import com.practice.deal.web.dto.EmploymentDto;
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
public class UserExtendDetails {

    private Integer dependentAmount;
    private LocalDate passportIssueDate;
    private String passportIssueBranch;
    private String account;

    private Employment employment;
    private Gender gender;
    private MaritalStatus maritalStatus;

}
