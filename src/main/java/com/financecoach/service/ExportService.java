package com.financecoach.service;

import com.financecoach.model.MonthlyReport;
import com.financecoach.model.Transaction;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.List;

public class ExportService {

    private static final String EXPORT_PATH =
            "exports/monthly_summary.csv";

    public String renderMonthlyReport(MonthlyReport report) {
        StringBuilder text = new StringBuilder();
        text.append("Monthly Financial Report - ")
            .append(report.month())
            .append(System.lineSeparator());
        text.append("----------------------------------------")
            .append(System.lineSeparator());
        appendAmount(text, "Total Income", report.income());
        appendAmount(text, "Total Needs", report.needs());
        appendAmount(text, "Total Wants", report.wants());
        appendAmount(text, "Total Savings", report.savings());
        appendAmount(text, "Total Debt", report.debt());
        appendAmount(text, "Other", report.other());
        text.append(System.lineSeparator())
            .append("50/30/20 Breakdown")
            .append(System.lineSeparator());
        appendPercentage(text, "Needs (target 50%)", report.needsPercent());
        appendPercentage(text, "Wants (target 30%)", report.wantsPercent());
        appendPercentage(
            text,
            "Savings + Debt (target 20%)",
            report.savingsDebtPercent()
        );
        return text.toString();
    }

    public Path exportMonthlyReport(MonthlyReport report) throws IOException {
        Path exportPath = Path.of(
            "exports",
            "monthly_report_" + report.month() + ".txt"
        );
        Files.createDirectories(exportPath.getParent());
        Files.writeString(exportPath, renderMonthlyReport(report));
        return exportPath;
    }

    public void exportMonthlySummary(List<Transaction> transactions) {

        File exportFile = new File(EXPORT_PATH);

        File parentFolder = exportFile.getParentFile();

        if (parentFolder != null) {
            parentFolder.mkdirs();
        }

        try (
            FileWriter writer = new FileWriter(exportFile);
            PrintWriter printer = new PrintWriter(writer)
        ) {

            printer.println("Date,Category,Description,Amount");

            for (Transaction transaction : transactions) {

                printer.println(
                    (transaction.getDate() == null
                        ? ""
                        : transaction.getDate())
                        + ","
                        + transaction.getCategory()
                        + ","
                        + transaction.getDescription()
                        + ","
                        + transaction.getAmount()
                );
            }

            System.out.println(
                "Monthly summary exported successfully to "
                    + EXPORT_PATH
            );

        } catch (IOException e) {

            System.err.println(
                "Coach: Error exporting summary: "
                    + e.getMessage()
            );
        }
    }

    private void appendAmount(StringBuilder text, String label, double amount) {
        text.append(String.format(Locale.ROOT, "%s: $%.2f%n", label, amount));
    }

    private void appendPercentage(StringBuilder text, String label, Double value) {
        text.append(label).append(": ");
        if (value == null) {
            text.append("N/A");
        } else {
            text.append(String.format(Locale.ROOT, "%.1f%%", value));
        }
        text.append(System.lineSeparator());
    }
}