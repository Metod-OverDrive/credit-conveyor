package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.LoanPaymentSchedule;
import com.practice.conveyor.web.dto.LoanPaymentScheduleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanPaymentScheduleMapper extends Mappable<LoanPaymentSchedule, LoanPaymentScheduleDto> {

    List<LoanPaymentScheduleDto> toDto(List<LoanPaymentSchedule> entities);

    List<LoanPaymentSchedule> toEntity(List<LoanPaymentScheduleDto> dtos);
}
