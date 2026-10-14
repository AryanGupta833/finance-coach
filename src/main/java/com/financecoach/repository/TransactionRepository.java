package com.financecoach.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.financecoach.model.Transaction;

public class TransactionRepository {
    private final Path filePath;

    public TransactionRepository() {
        this(Path.of("src/main/resources/finance_data.csv"));
    }

    public TransactionRepository(Path filePath) {
        this.filePath = filePath;
    }

    public List<Transaction> load() {
        List<Transaction> transactions = new ArrayList<>();
        File file = filePath.toFile();

        if (!file.exists()) {
            return transactions;
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = parseCsvLine(line);

                if (parts.length != 3 && parts.length != 4) {
                    continue;
                }

                String category = parts[0];
                double amount = Double.parseDouble(parts[1]);
                String description = parts[2];
                LocalDate date = parts.length == 4
                    ? LocalDate.parse(parts[3])
                    : null;

                transactions.add(
                    new Transaction(category, amount, description, date)
                );
            }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println(
                "Coach: Error loading history: " + e.getMessage()
            );
        }

        return transactions;
    }

    public void save(Transaction transaction) {
        try (
            FileWriter writer = new FileWriter(filePath.toFile(), true);
            PrintWriter printer = new PrintWriter(writer)
        ) {
            printer.println(
                csvField(transaction.getCategory())
                    + "," + transaction.getAmount()
                    + "," + csvField(transaction.getDescription())
                    + "," + (transaction.getDate() == null
                        ? ""
                        : transaction.getDate())
            );
        } catch (IOException e) {
            System.err.println(
                "Coach: Error saving data: " + e.getMessage()
            );
        }
    }

    private static String csvField(String value) {
        String escaped = value.replace("\"", "\"\"");
        return escaped.contains(",") || escaped.contains("\"")
                ? "\"" + escaped + "\""
                : escaped;
    }

    private static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean quoted = false;

        for (int index = 0; index < line.length(); index++) {
            char current = line.charAt(index);
            if (current == '"') {
                if (quoted && index + 1 < line.length()
                    && line.charAt(index + 1) == '"') {
                    field.append('"');
                    index++;
                } else {
                    quoted = !quoted;
                }
            } else if (current == ',' && !quoted) {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(current);
            }
        }

        fields.add(field.toString());
        return fields.toArray(String[]::new);
    }
}