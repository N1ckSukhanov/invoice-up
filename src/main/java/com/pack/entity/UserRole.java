package com.pack.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class UserRole {
    @Id
    @GeneratedValue
    private Integer id;

    private String login;
    private String password;
    @ManyToOne
    private Role role;
    private String fullName;

}
