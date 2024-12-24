package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.Employment;
import com.practice.conveyor.web.dto.EmploymentDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmploymentMapper extends Mappable<Employment, EmploymentDto> {
}
