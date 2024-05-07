package com.pack.parser;

import com.pack.entity.Task;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class InvParser {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String number, signCN;
    private float totalEuro, taxesEuro;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> services;

    public InvParser(String number, String signCN, float totalEuro, float taxesEuro, List<String> services) {
        this.number = number;
        this.signCN = signCN;
        this.totalEuro = totalEuro;
        this.taxesEuro = taxesEuro;
        this.services = services;
    }

    @Override
    public String toString() {
        return "InvParser{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", signCN='" + signCN + '\'' +
                ", totalEuro=" + totalEuro +
                ", taxesEuro=" + taxesEuro +
                ", services=" + services +
                '}';
    }
}
