package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "responsibility_center")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponsibilityCenter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(name = "reference", length = 64)
    private String reference;

    @Column(name = "label", length = 128)
    private String label;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_responsibility_center_client",
            joinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_client", referencedColumnName = "id")
    )
    private List<Client> clients;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_responsibility_center_sales_site",
            joinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_sales_site", referencedColumnName = "id")
    )
    private List<SalesSite> salesSites;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_user_responsibility_center",
            joinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id")
    )
    private List<User> users;

}
