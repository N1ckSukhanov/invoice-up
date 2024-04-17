package com.pack.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "CLDPF")
@Getter
public class Reservation {
    @Id
    @Column(name = "CLDDT")
    @Temporal(TemporalType.DATE)
    private LocalDate payDate;

    @Column(name = "CLDTSKMAX")
    private Integer taskCountMax;

    @Column(name = "CLDTSKC")
    private Integer taskCount;

    @Column(name = "CLDTSKV")
    private Integer taskCountVacant;

    @Column(name = "CLDUSER")
    private String user;

    @Column(name = "CLDCDT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime changeDate;
}
