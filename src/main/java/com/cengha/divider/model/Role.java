package com.cengha.divider.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by TRKoseCe on 20.07.2017.
 */

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    @Transient
    private List<User> users;

}
