package com.practice.deal.web.mapper;

import com.practice.deal.model.Credit;
import com.practice.deal.web.dto.CreditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditMapper extends Mappable<Credit, CreditDto> {
}
