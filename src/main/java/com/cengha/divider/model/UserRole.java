package com.cengha.divider.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames ={"user_id","role_id"}))
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name="user_id")
    private String userId;

    @Column(name="role_id")
    private String roleId;
}
