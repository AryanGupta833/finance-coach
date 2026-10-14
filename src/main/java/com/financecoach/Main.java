package com.financecoach;

import java.util.Scanner;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

import com.financecoach.engine.FinanceEngine;
import com.financecoach.model.MonthlyReport;
import com.financecoach.repository.TransactionRepository;
import com.financecoach.service.ExportService;
import com.financecoach.utils.InputUtils;

public class Main {

    public static void main(String[] args) {

        try (
            InputUtils input = new InputUtils(new Scanner(System.in))
        ) {

            TransactionRepository repository =
                new TransactionRepository();

            FinanceEngine coach =
                new FinanceEngine(repository);

            ExportService exporter =
                new ExportService();

            System.out.println(
                "Welcome to your AI Personal Finance Coach!"
            );

            while (true) {

                System.out.println("\n--- MENU ---");
                System.out.println("1. Add Income");
                System.out.println(
                    "2. Add Expense (Needs, Wants, Debt, Savings)"
                );
                System.out.println("3. Get AI Coaching Report");
                System.out.println("4. View/Export Monthly Text Report");
                System.out.println("5. Export Transactions CSV");
                System.out.println("6. Exit");

                int choice = input.askInt(
                    "Choose an option: ",
                    "Please enter a number (1-6)."
                );

                if (choice == 6) {
                    break;
                }

                switch (choice) {

                    case 1 -> {

                        double amount = input.askDouble(
                            "Enter Income Amount: "
                        );

                        if (amount < 0) {
                            System.out.println(
                                "Please enter a valid income amount."
                            );
                            break;
                        }

                        coach.addTransaction(
                            "Income",
                            amount,
                            "Monthly Salary"
                        );

                        System.out.println(
                            "Income added successfully!"
                        );
                    }

                    case 2 -> {

                        String category = input.askString(
                            "Category (Needs/Wants/Debt/Savings): "
                        );

                        double amount = input.askDouble(
                            "Amount: ",
                            "Please enter a valid expense amount."
                        );

                        if (amount < 0) {
                            System.out.println(
                                "Enter a valid expense amount!"
                            );
                            break;
                        }

                        coach.addTransaction(
                            category,
                            amount,
                            "User Expense"
                        );

                        System.out.println(
                            "Expense logged."
                        );
                    }

                    case 3 -> {

                        coach.generateCoachingReport();
                    }

                    case 4 -> {
                        YearMonth month = askReportMonth(input);
                        MonthlyReport report = coach.generateMonthlyReport(month);
                        String reportText = exporter.renderMonthlyReport(report);
                        System.out.print(reportText);

                        String saveReport = input.askString(
                            "Save this report as a text file? (y/n): "
                        );
                        if (saveReport.equalsIgnoreCase("y")) {
                            try {
                                System.out.println(
                                    "Report saved to "
                                        + exporter.exportMonthlyReport(report)
                                );
                            } catch (java.io.IOException e) {
                                System.err.println(
                                    "Coach: Error saving report: "
                                        + e.getMessage()
                                );
                            }
                        }
                    }

                    case 5 -> {

                        exporter.exportMonthlySummary(
                            coach.getTransactions()
                        );
                    }

                    default -> {

                        System.out.println(
                            "Invalid choice. Try 1-5."
                        );
                    }
                }
            }

            System.out.println(
                "Stay financially healthy! Goodbye."
            );
        }
    }

    private static YearMonth askReportMonth(InputUtils input) {
        while (true) {
            String value = input.askString("Report month (YYYY-MM): ");
            try {
                YearMonth month = YearMonth.parse(value);
                if (month.toString().equals(value)) {
                    return month;
                }
            } catch (DateTimeParseException ignored) {
            }
            System.out.println("Enter a valid month in YYYY-MM format.");
        }
    }
}