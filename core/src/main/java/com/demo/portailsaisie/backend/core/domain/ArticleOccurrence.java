package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "article_occurrence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleOccurrence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "activity", length = 128)
    private String activity;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_order_artocc",
            joinColumns = @JoinColumn(name = "id_article_occurrence", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_order", referencedColumnName = "id")
    )
    private List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

}
