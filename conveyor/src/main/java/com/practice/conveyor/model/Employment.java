package com.practice.conveyor.model;

import com.practice.conveyor.model.enums.EmploymentStatus;
import com.practice.conveyor.model.enums.Position;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@ToString
public class Employment {

    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
