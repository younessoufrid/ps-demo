package com.demo.portailsaisie.backend.web.api;

import com.demo.portailsaisie.backend.core.service.interfaces.AuthService;
import com.demo.portailsaisie.backend.web.PsvUri;
import com.demo.portailsaisie.backend.web.dto.auth.AuthRequestWo;
import com.demo.portailsaisie.backend.web.dto.auth.AuthResponseWo;
import com.demo.portailsaisie.backend.web.dto.mapping.AuthWoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PsvUri.API_AUTH)
@RequiredArgsConstructor
public class AuthWebApi {


    private final AuthService authService;
    private final AuthWoMapper authWoMapper;


    @PostMapping("/oidc")
    public AuthResponseWo keycloakAuthentication(@RequestBody AuthRequestWo authRequest) {
        return authWoMapper.map(authService.authenticateWithKeycloak(authWoMapper.map(authRequest)));
    }
}
