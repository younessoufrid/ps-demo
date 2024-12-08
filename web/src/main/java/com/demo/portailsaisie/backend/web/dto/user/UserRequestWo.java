package com.demo.portailsaisie.backend.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestWo {
    private String firstname;
    private String lastname;
    private String username;
}
