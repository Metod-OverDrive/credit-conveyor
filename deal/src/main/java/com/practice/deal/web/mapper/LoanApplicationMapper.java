package com.practice.deal.web.mapper;

import com.practice.deal.model.LoanApplication;
import com.practice.deal.web.dto.LoanApplicationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanApplicationMapper extends Mappable<LoanApplication, LoanApplicationDto> {
}
