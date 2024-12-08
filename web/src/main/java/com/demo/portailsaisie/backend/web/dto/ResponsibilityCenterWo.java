package com.demo.portailsaisie.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponsibilityCenterWo {
    private Long id;
    private String reference;
    private String label;
}
