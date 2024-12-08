package com.demo.portailsaisie.backend.core.mapping;

import com.demo.portailsaisie.backend.core.domain.User;
import com.demo.portailsaisie.backend.core.dto.user.UserRequestDto;
import com.demo.portailsaisie.backend.core.dto.user.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponseDto map(User user);
    User map(UserRequestDto dto);
}
