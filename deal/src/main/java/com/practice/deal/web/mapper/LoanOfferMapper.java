package com.practice.deal.web.mapper;

import com.practice.deal.model.LoanOffer;
import com.practice.deal.web.dto.LoanOfferDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanOfferMapper extends Mappable<LoanOffer, LoanOfferDto> {

}
