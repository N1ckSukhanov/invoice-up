package com.pack.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TaskDto {
    private MultipartFile invoiceScan;

    private String planPayDate;
    private Double sumCur;
    private Double taxCur;
    private Double creditNoteSum;
    private String creditNoteInvoice;
    private String creditNoteInvoiceForPay;
}
