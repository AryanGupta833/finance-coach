package com.financecoach.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.YearMonth;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.financecoach.model.MonthlyReport;
import com.financecoach.repository.TransactionRepository;

class FinanceEngineMonthlyReportTest {

    @TempDir
    Path tempDir;

    @Test
    void summarizesOnlyDatedTransactionsInSelectedMonth() throws Exception {
        Path dataFile = tempDir.resolve("transactions.csv");
        Files.writeString(
            dataFile,
            "Income,1000,Pay,2026-10-01\n"
                + "needs,500,Rent,2026-10-31\n"
                + "Wants,250,Leisure,2026-10-15\n"
                + "Savings,100,Transfer,2026-10-20\n"
                + "Debt,100,Payment,2026-10-21\n"
                + "Travel,50,Trip,2026-10-22\n"
                + "Income,900,Next month,2026-11-01\n"
                + "Needs,200,Undated legacy\n"
        );
        FinanceEngine engine = new FinanceEngine(
            new TransactionRepository(dataFile)
        );

        MonthlyReport report = engine.generateMonthlyReport(
            YearMonth.of(2026, 10)
        );

        assertEquals(1000, report.income());
        assertEquals(500, report.needs());
        assertEquals(250, report.wants());
        assertEquals(100, report.savings());
        assertEquals(100, report.debt());
        assertEquals(50, report.other());
        assertEquals(50, report.needsPercent());
        assertEquals(25, report.wantsPercent());
        assertEquals(20, report.savingsDebtPercent());
    }

    @Test
    void leavesPercentagesUnavailableWhenIncomeIsNotPositive() {
        FinanceEngine engine = new FinanceEngine(
            new TransactionRepository(tempDir.resolve("empty.csv"))
        );

        MonthlyReport report = engine.generateMonthlyReport(
            YearMonth.of(2026, 10)
        );

        assertEquals(0, report.income());
        assertNull(report.needsPercent());
        assertNull(report.wantsPercent());
        assertNull(report.savingsDebtPercent());
    }
}