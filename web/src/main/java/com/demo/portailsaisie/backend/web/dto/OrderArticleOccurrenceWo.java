package com.demo.portailsaisie.backend.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderArticleOccurrenceWo {
    private Long id;
    private String designation;
    private Double quantity;
    private Double priceNetHT;
    private Double line;
    private String textLigne;
    private String uniteVente;
    private String typeLivraison;
    private Date date;
    private Long idOrder;
    private Boolean show;
    private Double totalMonthQte;
    private Double totalPrice;
    private boolean soumis;
}
