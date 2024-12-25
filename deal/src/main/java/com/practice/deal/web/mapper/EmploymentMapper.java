package com.practice.deal.web.mapper;

import com.practice.deal.model.Employment;
import com.practice.deal.web.dto.EmploymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper extends Mappable<Employment, EmploymentDto> {
}
