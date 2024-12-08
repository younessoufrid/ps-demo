package com.demo.portailsaisie.backend.web.dto.filter;

import com.demo.portailsaisie.backend.web.dto.*;
import com.demo.portailsaisie.backend.web.dto.*;
import com.demo.portailsaisie.backend.web.dto.sammury.SummaryWo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterResponseWo {

    private List<SaleSiteWo> saleSites;
    private List<ResponsibilityCenterWo> cdrs;
    private List<ClientWo> clients;
    private List<AffairWo> affairs;
    private List<OrderWo> orders;
    private List<OrderArticleOccurrenceWo> orderLines;
    private List<SummaryWo> summary;
}
