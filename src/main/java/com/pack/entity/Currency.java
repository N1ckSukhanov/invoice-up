package com.pack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table
@Data
public class Currency {
    @Id
    @GeneratedValue
    private Integer id;

    private String cur;

    private String cost;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "MM/dd/yyyy")
    private LocalDate date;
}
