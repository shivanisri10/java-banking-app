package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import java.util.List;

/**
 * Bridge abstraction class decoupling high-level logger API from pluggable storage backends.
 */
public class TransactionLogger {
    // ============================================================
    // 📝 STEP 17: Declare Field
    // ============================================================
    protected LogDestination destination;

    // ============================================================
    // 📝 STEP 18: Constructor
    // ============================================================
    public TransactionLogger(LogDestination destination) {
        this.destination = destination;
    }

    // ============================================================
    // 📝 STEP 19: setDestination
    // ============================================================
    public void setDestination(LogDestination destination) {
        this.destination = destination;
    }

    // ============================================================
    // 📝 STEP 20: log(TransactionCommand cmd)
    // ============================================================
    public void log(TransactionCommand cmd) {
        destination.write(cmd);
    }

    // ============================================================
    // 📝 STEP 21: readAll()
    // ============================================================
    public List<TransactionCommand> readAll() {
        return destination.readAll();
    }

    // ============================================================
    // 📝 STEP 22: clear()
    // ============================================================
    public void clear() {
        destination.clear();
    }

    // ============================================================
    // 📝 STEP 23: getDestinationName()
    // ============================================================
    public String getDestinationName() {
        return destination.getDestinationName();
    }
}
