package com.pack.entity;

import com.pack.parser.InvParser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "TSKPF")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue
    private Integer ID;

    @OneToMany(mappedBy = "task")
    private List<InvoiceFile> invoiceFiles;

    @OneToMany(mappedBy = "task")
    private List<InvParser> invoiceParsers;

    public enum Status {
        WORK,
        CHKGB,
        CHECK,
        FILL,
        DONE
    }

    public Task(String planPayDate, Double sumCur, Double taxCur, Double creditNoteSum, String creditNoteInvoice, String creditNoteInvoiceForPay) {
        this.planPayDate = planPayDate;
        this.sumCur = sumCur;
        this.taxCur = taxCur;
        this.creditNoteSum = creditNoteSum;
        this.creditNoteInvoice = creditNoteInvoice;
        this.creditNoteInvoiceForPay = creditNoteInvoiceForPay;
    }

    @Column(name = "TSKDCNUM", length = 20)
    private String id;

    @Column(name = "TSKID", length = 20)
    private String docNumber;

    @Column(name = "TSKDTIN")
    private Date receivingDate;

    @Column(name = "TSKSTS", length = 5)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(name = "TSKADTP")
    private String planPayDate;

    @Column(name = "TSKASE", precision = 15)
    private Double sumCur;

    @Column(name = "TSKAVE", precision = 15)
    private Double taxCur;

    @Column(name = "TSKASVE", precision = 15)
    private Double totalCur;

    @Column(name = "TSKACNS", precision = 15)
    private Double creditNoteSum;

    @Column(name = "TSKACND", length = 20)
    private String creditNoteInvoice;

    @Column(name = "TSKACNR", length = 20)
    private String creditNoteInvoiceForPay;

    @Column(name = "TSKISE", precision = 15)
    private Double invoicesSumCur;

    @Column(name = "TSKIVE", precision = 15)
    private Double invoicesTaxCur;

    @Column(name = "TSKISVE", precision = 15)
    private Double invoicesTotalCur;

    @Column(name = "TSKISECN", precision = 15)
    private Double invoicesCNSumCur;

    @Column(name = "TSKIVECN", precision = 15)
    private Double invoicesCNTaxCur;

    @Column(name = "TSKISVECN", precision = 15)
    private Double invoicesCNTotalCur;

    @Column(name = "TSKFCCOD", length = 5)
    private String finControlCode;

    @Column(name = "TSKRSCN", precision = 15)
    private Long sumRub;

    @Column(name = "TSKRVCN", precision = 15)
    private Long taxRub;

    @Column(name = "TSKRSVCN", precision = 15)
    private Long totalRub;

    @Column(name = "TSKRBAL", precision = 15)
    private Long balanceRub;

    @Column(name = "TSKRPAY", precision = 15)
    private Long topUpSum;

    @Column(name = "TSKDBT", precision = 15)
    private Long debtRub;

    @Column(name = "TSKDTF")
    private Date factPayDate;

    @Column(name = "TSKUSER", length = 20)
    private String user;

    @Column(name = "TSKCDT")
    private Timestamp changeDate;
}
