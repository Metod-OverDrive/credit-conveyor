package com.practice.deal.web.mapper;

import com.practice.deal.model.LoanPaymentSchedule;
import com.practice.deal.web.dto.LoanPaymentScheduleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanPaymentScheduleMapper extends Mappable<LoanPaymentSchedule, LoanPaymentScheduleDto> {

    List<LoanPaymentScheduleDto> toDto(List<LoanPaymentSchedule> entities);

    List<LoanPaymentSchedule> toEntity(List<LoanPaymentScheduleDto> dtos);
}
