package com.demo.portailsaisie.backend.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseWo {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
}
