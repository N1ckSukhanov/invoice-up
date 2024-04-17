package com.pack.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskInvoice {
    private MultipartFile file;
}
