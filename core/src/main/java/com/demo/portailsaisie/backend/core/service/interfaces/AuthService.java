package com.demo.portailsaisie.backend.core.service.interfaces;


import com.demo.portailsaisie.backend.core.dto.auth.AuthRequestDto;
import com.demo.portailsaisie.backend.core.dto.auth.AuthResponseDto;

public interface AuthService {
    AuthResponseDto authenticateWithKeycloak(AuthRequestDto authRequestDto);
}
