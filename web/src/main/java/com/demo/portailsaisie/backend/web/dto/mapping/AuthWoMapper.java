package com.demo.portailsaisie.backend.web.dto.mapping;

import com.demo.portailsaisie.backend.core.dto.auth.AuthRequestDto;
import com.demo.portailsaisie.backend.core.dto.auth.AuthResponseDto;
import com.demo.portailsaisie.backend.web.dto.auth.AuthRequestWo;
import com.demo.portailsaisie.backend.web.dto.auth.AuthResponseWo;
import org.mapstruct.Mapper;

@Mapper
public interface AuthWoMapper {
    AuthRequestDto map(AuthRequestWo wo);
    AuthResponseWo map(AuthResponseDto dto);
}
