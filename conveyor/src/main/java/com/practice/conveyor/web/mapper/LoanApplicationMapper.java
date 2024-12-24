package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.LoanApplication;
import com.practice.conveyor.web.dto.LoanApplicationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper extends Mappable<LoanApplication, LoanApplicationDto> {
}
