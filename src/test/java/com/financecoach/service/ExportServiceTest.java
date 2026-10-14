package com.financecoach.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.financecoach.model.Transaction;
import com.financecoach.model.MonthlyReport;

public class ExportServiceTest {

    private final Path exportPath =
        Path.of("exports/monthly_summary.csv");

    @BeforeEach
    void cleanBeforeTest() throws Exception {

        Files.deleteIfExists(exportPath);
    }

    @AfterEach
    void cleanAfterTest() throws Exception {

        Files.deleteIfExists(exportPath);
    }

    @Test
    void exportCreatesFileAndWritesTransactionData() throws Exception {

        List<Transaction> transactions = List.of(
            new Transaction(
                "Income",
                50000,
                "Monthly Salary"
            ),
            new Transaction(
                "Needs",
                5000,
                "Rent"
            )
        );

        ExportService exporter = new ExportService();

        exporter.exportMonthlySummary(transactions);

        // Check that the CSV file was created
        assertTrue(
            Files.exists(exportPath),
            "CSV export file should be created"
        );

        List<String> lines = Files.readAllLines(exportPath);

        // Header + 2 transaction rows
        assertEquals(3, lines.size());

        assertEquals(
            "Date,Category,Description,Amount",
            lines.get(0)
        );

        assertEquals(
            transactions.get(0).getDate()
                + ",Income,Monthly Salary,50000.0",
            lines.get(1)
        );

        assertEquals(
            transactions.get(1).getDate()
                + ",Needs,Rent,5000.0",
            lines.get(2)
        );
    }

    @Test
    void rendersAndExportsMonthlyTextReport() throws Exception {
        Path reportPath = Path.of("exports/monthly_report_2026-10.txt");
        Files.deleteIfExists(reportPath);
        MonthlyReport report = new MonthlyReport(
            YearMonth.of(2026, 10),
            1000,
            500,
            250,
            100,
            100,
            50,
            50.0,
            25.0,
            20.0
        );
        ExportService exporter = new ExportService();

        String rendered = exporter.renderMonthlyReport(report);
        Path exportedPath = exporter.exportMonthlyReport(report);

        assertEquals(reportPath, exportedPath);
        assertTrue(Files.exists(reportPath));
        assertTrue(rendered.contains("Monthly Financial Report - 2026-10"));
        assertTrue(rendered.contains("Total Income: $1000.00"));
        assertTrue(rendered.contains("Total Savings: $100.00"));
        assertTrue(rendered.contains("Savings + Debt (target 20%): 20.0%"));
        assertEquals(rendered, Files.readString(reportPath));

        Files.deleteIfExists(reportPath);
    }
}