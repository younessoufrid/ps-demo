package com.demo.portailsaisie.backend.core.service.interfaces;

import com.demo.portailsaisie.backend.core.dto.user.UserRequestDto;
import com.demo.portailsaisie.backend.core.dto.user.UserResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponseDto create(UserRequestDto user);
    boolean isExistsByUsername(String username);
}
