package com.gdb.logging;

import com.gdb.command.TransactionCommand;
import java.io.*;
import java.util.*;

public class TransactionLog {
    private static final String FILE_PATH = "data/transactions.ser";

    // ============================================================
    // Helper: AppendableObjectOutputStream (COMPLETE — non-placeholder)
    // ============================================================
    private static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }
        @Override
        protected void writeStreamHeader() throws IOException {
            // do not write header when appending to an existing stream
        }
    }

    // ============================================================
    // 📝 STEP 7: Implement log(TransactionCommand cmd)
    //
    // INSTRUCTIONS:
    //   1. Create parent directory (data/) if missing.
    //   2. If file does not exist or is empty (length == 0), use new ObjectOutputStream(...).
    //   3. If file already has data, use new AppendableObjectOutputStream(new FileOutputStream(file, true)).
    //   4. Write cmd object, flush, and close stream.
    // ============================================================
    public synchronized void log(TransactionCommand cmd) throws IOException {
        File file = new File(FILE_PATH);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        boolean append = file.exists() && file.length() > 0;
        try (ObjectOutputStream out = append
                ? new AppendableObjectOutputStream(new FileOutputStream(file, true))
                : new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(cmd);
            out.flush();
        }
    }

    // ============================================================
    // 📝 STEP 8: Implement readAll()
    //
    // INSTRUCTIONS:
    //   1. If file does not exist, return empty List.
    //   2. Open ObjectInputStream and loop calling readObject() until EOFException.
    //   3. Collect and return List<TransactionCommand>.
    // ============================================================
    public synchronized List<TransactionCommand> readAll() throws IOException, ClassNotFoundException {
        List<TransactionCommand> commands = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            return commands;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    commands.add((TransactionCommand) in.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        }
        return commands;
    }

    // ============================================================
    // 📝 STEP 9: Implement clear()
    //
    // INSTRUCTIONS:
    //   Delete the log file if it exists.
    // ============================================================
    public synchronized void clear() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            file.delete();
        }
    }
}
