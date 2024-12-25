package com.practice.deal.web.mapper;

import com.practice.deal.model.ScoringData;
import com.practice.deal.web.dto.ScoringDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper extends Mappable<ScoringData, ScoringDataDto> {
}
