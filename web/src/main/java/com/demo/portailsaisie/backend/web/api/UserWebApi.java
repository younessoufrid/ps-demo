package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.core.service.interfaces.UserService;
import com.demo.portailsaisie.backend.web.PsvUri;
import com.demo.portailsaisie.backend.web.dto.mapping.UserWOMapper;
import com.demo.portailsaisie.backend.web.dto.user.UserRequestWo;
import com.demo.portailsaisie.backend.web.dto.user.UserResponseWo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.demo.portailsaisie.backend.core.domain.User;

@RestController
@RequestMapping(PsvUri.API_USER)
@RequiredArgsConstructor
public class UserWebApi {

    private final UserService userService;

    private final UserWOMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseWo createPerson(@RequestBody UserRequestWo requestWo) {
        return mapper.map(
                userService.create(
                        mapper.map(requestWo)
                )
        );
    }
}
