package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.LoanOffer;
import com.practice.conveyor.web.dto.LoanOfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanOfferMapper extends Mappable<LoanOffer, LoanOfferDto> {

}
