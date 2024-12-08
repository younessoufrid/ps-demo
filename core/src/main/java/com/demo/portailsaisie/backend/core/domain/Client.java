package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "client")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "name", length = 128)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_client_affair",
            joinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_affair", referencedColumnName = "id")
    )
    private List<Affair> affairs;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_responsibility_center_client",
            joinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id")
    )
    private List<ResponsibilityCenter> responsibilityCenters;



}
