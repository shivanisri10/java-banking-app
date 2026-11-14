package com.gdb.tests;

import com.gdb.db.ConnectionProvider;
import com.gdb.db.JdbcConnectionProvider;
import com.gdb.db.SchemaInitializer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJdbcConnection {
    public static void main(String[] args) throws Exception {
        ConnectionProvider provider = new JdbcConnectionProvider("jdbc:sqlite:test_gdb.db");
        try (Connection conn = provider.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT 1")) {
            if (rs.next()) System.out.println("Connection successful: " + rs.getInt(1));
        }
        SchemaInitializer.initialize(provider);
        try (Connection conn = provider.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' ORDER BY name")) {
            while (rs.next()) System.out.println("Table: " + rs.getString(1));
        }
        provider.shutdown();
        System.out.println("Activity 22 JDBC tests complete.");
    }
}
