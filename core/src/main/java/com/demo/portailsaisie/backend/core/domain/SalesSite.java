package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "sales_site")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "label", length = 128)
    private String label;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_responsibility_center_sales_site",
            joinColumns = @JoinColumn(name = "id_sales_site", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id")
    )
    private List<ResponsibilityCenter> responsibilityCenters;

}
