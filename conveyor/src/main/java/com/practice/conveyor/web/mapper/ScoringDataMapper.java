package com.practice.conveyor.web.mapper;

import com.practice.conveyor.model.ScoringData;
import com.practice.conveyor.web.dto.ScoringDataDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ScoringDataMapper extends Mappable<ScoringData, ScoringDataDto> {
}
