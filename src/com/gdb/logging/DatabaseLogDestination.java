package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import com.gdb.db.SimulatedDatabase;
import java.util.*;

public class DatabaseLogDestination implements LogDestination {
    private static final String TABLE = "transaction_log";

    // ============================================================
    // 📝 STEP 11: Declare Field
    // ============================================================
    private final SimulatedDatabase db;

    // ============================================================
    // 📝 STEP 12: Constructor
    // ============================================================
    public DatabaseLogDestination(SimulatedDatabase db) {
        this.db = db;
    }

    // ============================================================
    // 📝 STEP 13: write(cmd)
    // ============================================================
    @Override
    public void write(TransactionCommand cmd) {
        db.insert(TABLE, cmd);
    }

    // ============================================================
    // 📝 STEP 14: readAll()
    //
    // INSTRUCTIONS:
    //   1. Call db.selectAll(TABLE).
    //   2. Map/cast each Object to TransactionCommand.
    //   3. Collect and return List<TransactionCommand>.
    // ============================================================
    @Override
    public List<TransactionCommand> readAll() {
        List<TransactionCommand> commands = new ArrayList<>();
        for (Object row : db.selectAll(TABLE)) {
            commands.add((TransactionCommand) row);
        }
        return commands;
    }

    // ============================================================
    // 📝 STEP 15: clear()
    // ============================================================
    @Override
    public void clear() {
        db.deleteAll(TABLE);
    }

    // ============================================================
    // 📝 STEP 16: getDestinationName()
    // ============================================================
    @Override
    public String getDestinationName() {
        return "DATABASE";
    }
}
