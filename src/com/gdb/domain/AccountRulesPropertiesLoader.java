package com.gdb.domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class AccountRulesPropertiesLoader {
    private Properties properties = new Properties();

    public AccountRulesPropertiesLoader(String configPath) {
        loadProperties(configPath);
    }

    // Load key=value pairs from the classpath first, falling back to the file system.
    private void loadProperties(String configPath) {
        InputStream in = null;
        try {
            in = getClass().getClassLoader().getResourceAsStream(configPath);
            if (in == null && new File(configPath).exists()) {
                in = new FileInputStream(configPath);
            }
            if (in == null) {
                System.out.println("[Config] Warning: rules file not found: " + configPath);
                return;
            }
            properties.load(in);
        } catch (Exception e) {
            System.out.println("[Config] Warning: could not load " + configPath + ": " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public double getDouble(String key, double defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public int getInt(String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null) return defaultValue;
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
