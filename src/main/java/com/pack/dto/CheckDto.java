package com.pack.dto;

import com.pack.entity.InvoiceFile;
import com.pack.entity.Task;
import com.pack.parser.InvParser;
import lombok.Data;

import java.util.List;

@Data
public class CheckDto {
    private List<InvoiceFile> invoiceFiles;
    private List<InvParser> invParsers;

    private String planPayDate;
    private Double sumCur;
    private Double taxCur;
    private Double creditNoteSum;
    private String creditNoteInvoice;
    private String creditNoteInvoiceForPay;

    private String totalEuro;
    private String totalTaxes;

    public CheckDto(Task task) {
        this.invoiceFiles = task.getInvoiceFiles();
        this.invParsers = task.getInvoiceParsers();
        this.totalEuro = String.format("%.2f", invParsers.stream().mapToDouble(InvParser::getTotalEuro).sum());
        this.totalTaxes = String.format("%.2f", invParsers.stream().mapToDouble(InvParser::getTaxesEuro).sum());
        this.planPayDate = task.getPlanPayDate();
        this.sumCur = task.getSumCur();
        this.taxCur = task.getTaxCur();
        this.creditNoteSum = task.getCreditNoteSum();
        this.creditNoteInvoice = task.getCreditNoteInvoice();
        this.creditNoteInvoiceForPay = task.getCreditNoteInvoiceForPay();
    }
}
