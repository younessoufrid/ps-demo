package com.demo.portailsaisie.backend.core.domain;

import com.demo.portailsaisie.backend.core.enums.OrderLineState;
import com.demo.portailsaisie.backend.core.enums.UniteVente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "a_order_artocc",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"ligne", "id_order", "date"}, name = "uq_ligne_id_order")})
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AOrderArticleOccurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "price_net_ht")
    private double priceNetHT;

    @Column(name = "ligne")
    private long line;

    @Column(name = "text_ligne", length = 300)
    private String textLigne;

    @Column(name = "designation")
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "unite_vente", length = 5)
    private UniteVente uniteVente;

    @Column(name = "type_livraison")
    private String typeLivraison;

    @Column(name = "show")
    private boolean show;

    @Column(name = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date;

    @Column(name = "init")
    private Boolean init;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_article_occurrence")
    private ArticleOccurrence articleOccurrence;

    @Transient
    private double totalMonthQte;

    @Transient
    private double totalPrice;

    @Column(name = "soumis")
    private boolean soumis;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private OrderLineState state;
}
