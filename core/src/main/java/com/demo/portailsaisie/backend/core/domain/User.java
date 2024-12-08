package com.demo.portailsaisie.backend.core.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", length = 64)
    private String firstname;

    @Column(name = "lastname", length = 64)
    private String lastname;

    @Column(name = "username", length = 64)
    private String username;

    @Column(name = "password", length = 64)
    @Transient
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "accountNonExpired")
    @Transient
    private boolean accountNonExpired;

    @Column(name = "accountNonLocked")
    @Transient
    private boolean accountNonLocked;

    @Column(name = "credentialsNonExpired")
    @Transient
    private boolean credentialsNonExpired;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "a_user_authorities",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_authority", referencedColumnName = "id")
    )
    private List<Authority> authorities;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "a_user_responsibility_center",
            joinColumns = @JoinColumn(name = "id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "id_responsibility_center", referencedColumnName = "id")
    )
    private List<ResponsibilityCenter> responsibilityCenters;
}
