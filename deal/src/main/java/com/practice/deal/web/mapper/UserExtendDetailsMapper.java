package com.practice.deal.web.mapper;

import com.practice.deal.model.UserExtendDetails;
import com.practice.deal.web.dto.UserExtendDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserExtendDetailsMapper extends Mappable<UserExtendDetails, UserExtendDetailsDTO> {
}
