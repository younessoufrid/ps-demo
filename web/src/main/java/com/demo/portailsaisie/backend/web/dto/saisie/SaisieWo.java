package com.demo.portailsaisie.backend.web.dto.saisie;

import com.demo.portailsaisie.backend.core.enums.UniteVente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaisieWo {
    private Long id;
    private String designation;
    private String textLigne;
    private Double quantity;
    private Double priceNetHT;
    private UniteVente uniteVente;
    private Boolean show;
    private Date date;
    private Long line;
    private Long idOrder;
    private Double totalMonthQte;
    private Double totalPrice;
}