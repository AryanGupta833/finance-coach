package com.financecoach.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.financecoach.model.Transaction;

class TransactionRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void loadsLegacyRowsUndatedAndRoundTripsDatedRows() throws Exception {
        Path dataFile = tempDir.resolve("transactions.csv");
        Files.writeString(dataFile, "Needs,75.5,Old rent\n");
        TransactionRepository repository = new TransactionRepository(dataFile);

        List<Transaction> legacy = repository.load();
        assertEquals(1, legacy.size());
        assertNull(legacy.get(0).getDate());

        Transaction dated = new Transaction(
            "Needs",
            120,
            "Rent, utilities",
            LocalDate.of(2026, 10, 1)
        );
        repository.save(dated);

        List<Transaction> loaded = repository.load();
        assertEquals(2, loaded.size());
        assertNull(loaded.get(0).getDate());
        assertEquals(dated.getDate(), loaded.get(1).getDate());
        assertEquals(dated.getDescription(), loaded.get(1).getDescription());
    }
}