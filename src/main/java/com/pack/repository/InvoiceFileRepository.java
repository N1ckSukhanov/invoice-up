package com.pack.repository;

import com.pack.entity.InvoiceFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceFileRepository extends JpaRepository<InvoiceFile, Integer> {
    List<InvoiceFile> findAllByNameEquals(String name);
}
