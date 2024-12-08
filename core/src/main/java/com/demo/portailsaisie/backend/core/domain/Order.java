package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "label", length = 128)
    private String label;

    @Column(name = "number", length = 64, nullable = false)
    private String number;

    @Column(name = "total_price")
    private double totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_project")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_affair")
    private Affair affair;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_order_artocc",
            joinColumns = @JoinColumn(name = "id_order", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_article_occurrence", referencedColumnName = "id")
    )
    private List<ArticleOccurrence> articleOccurrences;

}
