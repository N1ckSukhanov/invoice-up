package com.pack.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "DCPF")
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany(mappedBy = "contract")
    private List<Contact> contacts;

    @Override
    public String toString() {
        return "Contract{" +
                "id=" + id +
                ", clientID=" + clientID +
                ", contractNumber='" + contractNumber + '\'' +
                ", contractStatus='" + contractStatus + '\'' +
                ", signingDate='" + signingDate + '\'' +
                ", planFinishDate=" + planFinishDate +
                ", actualFinishDate=" + actualFinishDate +
                ", index='" + index + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", contractType='" + contractType + '\'' +
                ", user='" + user + '\'' +
                ", changeDate='" + changeDate + '\'' +
                '}';
    }

    private String organization;

    @Column(name = "DCID")
    private String clientID;
    @Column(name = "DCNUM")
    private String contractNumber;
    @Column(name = "DCSTS")
    private String contractStatus;
    @Column(name = "DCDTS")
    private String signingDate;
    @Column(name = "DCDTEP")
    private String planFinishDate;
    @Column(name = "DCDTEF")
    private String actualFinishDate;
    @Column(name = "DCIND")
    private String index;
    @Column(name = "DCCITY")
    private String city;
    @Column(name = "DCADR")
    private String address;
    @Column(name = "DCTYP")
    private String contractType;
    @Column(name = "DCUSER")
    private String user;
    @Column(name = "DCDT")
    private String changeDate;
}
