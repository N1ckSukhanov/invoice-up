package com.pack.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class InvoiceFile {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    private String name;
    @Lob
    private byte[] file;

    public InvoiceFile(String name, byte[] file, Task task) {
        this.name = name;
        this.file = file;
        this.task = task;
    }

    @Override
    public String toString() {
        return "InvoiceFile{" +
                ", name='" + name + '\'' +
                '}';
    }
}
