package com.pack.repository;

import com.pack.entity.InvoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceFileRepository extends JpaRepository<InvoiceFile, Integer> {
    List<InvoiceFile> findAllByNameEquals(String name);
}
