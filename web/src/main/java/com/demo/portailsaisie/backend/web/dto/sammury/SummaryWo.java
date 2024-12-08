package com.demo.portailsaisie.backend.web.dto.sammury;

import com.demo.portailsaisie.backend.core.enums.UniteVente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SummaryWo {
    private String designation;
    private Double priceNetHT;
    private UniteVente uniteVente;
    private Double totalMonthQte;
    private Double totalPrice;
    private boolean soumis;
    private List<SummaryItemWo> summaryItems;
}