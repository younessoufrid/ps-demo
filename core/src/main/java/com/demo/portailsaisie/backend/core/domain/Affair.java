package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.*;


@Entity
@Table(name = "affair")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Affair implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "label", length = 128)
    private String label;

    @Column(name = "start_service")
    private Date startService;

    @Column(name = "end_service")
    private Date endService;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_client_affair",
            joinColumns = @JoinColumn(name = "id_affair", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id")
    )
    private List<Client> clients;

    @OneToMany(mappedBy = "affair", fetch = FetchType.LAZY)
    private List<Order> orders;

}
