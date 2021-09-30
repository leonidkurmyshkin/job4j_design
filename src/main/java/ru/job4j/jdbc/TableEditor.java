package ru.job4j.jdbc;

import java.sql.*;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {
    private Connection connection;
    private Properties properties;

    public TableEditor(Properties properties) throws Exception {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws Exception {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(
                properties.getProperty("url"),
                properties.getProperty("login"),
                properties.getProperty("password")
        );
    }

    public void createTable(String tableName) throws Exception {
        executeDDL("""
                CREATE TABLE IF NOT EXISTS %s (
                id serial PRIMARY KEY );
                """.formatted(tableName));
    }

    public void dropTable(String tableName) throws Exception {
        executeDDL("DROP TABLE IF EXISTS %s;"
                .formatted(tableName));
    }

    public void addColumn(String tableName, String columnName, String type) throws Exception {
        executeDDL("ALTER TABLE IF EXISTS %s ADD COLUMN IF NOT EXISTS %s %s;"
                .formatted(tableName, columnName, type));
    }

    public void dropColumn(String tableName, String columnName) throws Exception {
        executeDDL("ALTER TABLE IF EXISTS %s DROP COLUMN IF EXISTS %s;"
                .formatted(tableName, columnName));
    }

    public void renameColumn(String tableName, String columnName, String newColumnName) throws Exception {
        executeDDL("""
                ALTER TABLE IF EXISTS %s
                RENAME COLUMN %s TO %s;
                """.formatted(tableName, columnName, newColumnName));
    }

    private void executeDDL(String command) throws Exception {
        try (var statement = connection.createStatement()) {
            statement.execute(command);
        }
    }

    public String getTableScheme(String tableName) throws Exception {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }
}