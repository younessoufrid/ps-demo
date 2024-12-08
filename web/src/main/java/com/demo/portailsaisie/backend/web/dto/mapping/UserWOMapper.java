package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.user.UserRequestDto;
import com.demo.portailsaisie.backend.core.dto.user.UserResponseDto;
import com.demo.portailsaisie.backend.web.dto.user.UserRequestWo;
import com.demo.portailsaisie.backend.web.dto.user.UserResponseWo;
import org.mapstruct.Mapper;

@Mapper
public interface UserWOMapper {
    UserRequestDto map(UserRequestWo wo);
    UserResponseWo map(UserResponseDto dto);
}
