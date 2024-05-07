package com.pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Role {
    @Id
    @GeneratedValue
    private Integer id;

    private String code;
    private String info;

    public String value() {
        return String.valueOf(id);
    }

    public String text() {
        return info;
    }
}
