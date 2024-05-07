package com.pack.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table
@NoArgsConstructor
public class RolePage {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String link;

    private boolean read = false;
    private boolean write = false;

    @ManyToOne
    private Role role;

    public RolePage(String name, String link, Role role) {
        this.name = name;
        this.link = link;
        this.role = role;
    }
}
