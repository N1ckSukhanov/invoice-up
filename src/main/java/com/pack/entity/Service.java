package com.pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Service {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String taxPercent;

    private String vatPercent;
}
