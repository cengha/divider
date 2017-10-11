package com.cengha.divider.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by TRKoseCe on 20.07.2017.
 */

@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private Boolean  enabled=true;

    @Column(nullable = false)
    private LocalDateTime created=LocalDateTime.now();

    @Transient
    private Set<Role> roles;
    @Transient
    private Set<Long> roleIds;


}
