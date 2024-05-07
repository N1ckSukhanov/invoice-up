package com.pack.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "CLPF")
@ToString
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "CLID", length = 20)
    private String clientID;

    @Column(name = "CL1C")
    private String client1CName;

    @Column(name = "CLNM")
    private String clientShortName;

    @Column(name = "CLCUS", length = 20)
    private String clientInvoiceName;

    @Column(name = "CLNMR")
    private String clientReportName;

    @Column(name = "CLDO", length = 3)
    private String clientDocTransfer;

    @Column(name = "CLBICS", length = 20)
    private String clientSwiftBic;

    @Column(name = "CLBIC", length = 20)
    private String clientBic;

    @Column(name = "CLPRSB", length = 5)
    private String clientPrServiceBureau;

    @Column(name = "CLPRS", length = 5)
    private String clientPrSanctions;

    @Column(name = "CLPRCAL", length = 5)
    private String clientPrSpecialCalendar;

    @Column(name = "CLSTS", length = 5)
    private String clientStatus;

    @Column(name = "CLUSER", length = 20)
    private String user;

    @Column(name = "CLСDT")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime changeDate;
}

