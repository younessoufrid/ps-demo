package com.demo.portailsaisie.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffairWo {
    private Long id;
    private String reference;
    private String label;
    private Date startService;
    private Date endService;
}
