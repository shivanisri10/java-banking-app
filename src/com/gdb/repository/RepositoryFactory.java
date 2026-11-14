package com.gdb.repository;

import java.io.*;
import java.util.Properties;

/**
 * Factory creating and supplying repository instances based on persistence.properties configuration.
 */
public class RepositoryFactory {

    private static AccountRepository accountRepositoryInstance;
    private static TransactionRepository transactionRepositoryInstance;

    // ============================================================
    // 📝 STEP 14: Read persistence.mode from Configuration
    //
    // INSTRUCTIONS:
    //   1. Load properties from "config/persistence.properties" or classpath resource.
    //   2. Return property value for "persistence.mode" (default to "memory" if missing).
    // ============================================================
    public static String getPersistenceMode() {
        Properties properties = new Properties();
        try (InputStream input = RepositoryFactory.class.getClassLoader()
                .getResourceAsStream("config/persistence.properties")) {
            if (input != null) {
                properties.load(input);
            }
        } catch (IOException e) {
            System.err.println("Could not load persistence.properties: " + e.getMessage());
        }
        return properties.getProperty("persistence.mode", "memory").trim().toLowerCase();
    }

    // ============================================================
    // 📝 STEP 15: Implement getAccountRepository()
    //
    // INSTRUCTIONS:
    //   1. Check persistence mode:
    //      - "memory" -> return singleton InMemoryAccountRepository
    //      - "jdbc"   -> throw UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22")
    //      - "file"   -> throw UnsupportedOperationException("File repository not implemented yet")
    //      - default  -> return InMemoryAccountRepository
    // ============================================================
    public static synchronized AccountRepository getAccountRepository() {
        String mode = getPersistenceMode();
        if ("jdbc".equals(mode)) {
            throw new UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22");
        }
        if ("file".equals(mode)) {
            throw new UnsupportedOperationException("File repository not implemented yet");
        }
        if (accountRepositoryInstance == null) {
            accountRepositoryInstance = new InMemoryAccountRepository();
        }
        return accountRepositoryInstance;
    }

    // ============================================================
    // 📝 STEP 16: Implement getTransactionRepository()
    //
    // INSTRUCTIONS:
    //   1. Check persistence mode:
    //      - "memory" -> return singleton InMemoryTransactionRepository
    //      - "jdbc"   -> throw UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22")
    //      - "file"   -> throw UnsupportedOperationException("File repository not implemented yet")
    //      - default  -> return InMemoryTransactionRepository
    // ============================================================
    public static synchronized TransactionRepository getTransactionRepository() {
        String mode = getPersistenceMode();
        if ("jdbc".equals(mode)) {
            throw new UnsupportedOperationException("JDBC repository not implemented yet - coming in Activity 22");
        }
        if ("file".equals(mode)) {
            throw new UnsupportedOperationException("File repository not implemented yet");
        }
        if (transactionRepositoryInstance == null) {
            transactionRepositoryInstance = new InMemoryTransactionRepository();
        }
        return transactionRepositoryInstance;
    }
}
