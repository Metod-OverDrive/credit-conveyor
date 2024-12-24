package com.practice.conveyor.web.dto;

import com.practice.conveyor.model.enums.EmploymentStatus;
import com.practice.conveyor.model.enums.Position;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class EmploymentDto {

    @NotNull
    private EmploymentStatus employmentStatus;

    private String employerINN;

    @PositiveOrZero
    private BigDecimal salary;

    private Position position;

    @PositiveOrZero
    private Integer workExperienceTotal;

    @PositiveOrZero
    private Integer workExperienceCurrent;
}
