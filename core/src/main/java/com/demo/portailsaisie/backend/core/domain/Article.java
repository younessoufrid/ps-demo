package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference_bu", length = 64)
    private String referenceBu;

    @Column(name = "label", length = 128)
    private String label;

    @OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
    private List<ArticleOccurrence> articleOccurrences;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;


}
