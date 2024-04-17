package com.pack.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "CONPF")
public class Contact {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    private String fullName;

    private String email;

    private String phone;

    private String position;

    private String comment;

    private String accepts;

    private String user_;

    private Date date;
}
