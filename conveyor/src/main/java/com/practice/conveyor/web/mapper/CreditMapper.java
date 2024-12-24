package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.Credit;
import com.practice.conveyor.web.dto.CreditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper extends Mappable<Credit, CreditDto> {
}
