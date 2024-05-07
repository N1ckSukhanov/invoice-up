package com.pack.parser;

import lombok.SneakyThrows;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class PdfParser {
    private static final File baseDirectory = new File("invoices");

    public static void main(String... args) {
        Arrays.stream(Objects.requireNonNull(baseDirectory.listFiles()))
                .map(PdfParser::parseFile)
                .forEach(System.out::println);
    }

    @SneakyThrows
    public static InvParser parseFile(File file) {
        try (var document = Loader.loadPDF(file)) {
            var stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            String invoiceNumber = null;
            float invoiceTotal = 0f;
            float invoiceTaxes = 0f;

            var services = new ArrayList<String>();
            boolean servicesFlag = false;

            for (String line : text.split("\n")) {
                line = line.replace("\r", "");
                line = line.strip();

                if (line.isBlank())
                    continue;

                if (line.contains("Invoice number")) {
                    invoiceNumber = line.substring("Invoice number".length()).strip();
                    continue;
                }

                if (line.contains("Total taxes ")) {
                    invoiceTaxes = parseNumber(line.substring("Total taxes ".length()));
                    continue;
                }

                // Отслеживаем, что дальше пойдут сервисы
                if (line.contains("Description") && line.contains("Total (EUR)") && services.isEmpty()) {
                    servicesFlag = true;
                    continue;
                }

                // Это или сервисы, или общее количество
                if (servicesFlag) {
                    if (line.contains("Invoice total ")) {
                        servicesFlag = false;
                        invoiceTotal = parseNumber(line.substring("Invoice total ".length()));
                    } else {
                        services.add(line.split("\\s{2,}")[0]); // Короче первая часть - сервис, вторая - цена
                    }
                }
            }

            if (invoiceNumber == null)
                throw new IllegalStateException("Invoice number not found!");

            if (invoiceTotal == 0f)
                throw new IllegalStateException("Invoice total not found!");

            if (services.isEmpty())
                throw new IllegalStateException("Services not found!");

            return new InvParser(invoiceNumber, "N", invoiceTotal, invoiceTaxes, services);
        }
    }

    // Переводит представление числа из документа в нормальное число
    private static float parseNumber(String text) {
        return Float.parseFloat(text.strip().replace(",", ""));
    }
}