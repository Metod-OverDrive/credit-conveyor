package com.practice.deal.web.dto;

import com.practice.deal.model.enums.Gender;
import com.practice.deal.model.enums.MaritalStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDto {

    private Long id;
    private String name;
    private String surname;
    private String middleName;
    private LocalDate birthday;
    private String email;
    private Integer dependentAmount;
    private String account;

    private Gender gender;
    private MaritalStatus maritalStatus;
    private PassportDto passportInfo;
    private EmploymentDto employmentInfo;
}
