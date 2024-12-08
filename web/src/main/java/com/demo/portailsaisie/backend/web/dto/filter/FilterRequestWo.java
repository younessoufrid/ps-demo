package com.demo.portailsaisie.backend.web.dto.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterRequestWo {
    private String saleSite;
    private String cdr;
    private Long client;
    private Long affair;
    private Long order;
    private String date;
    private String action;
}
