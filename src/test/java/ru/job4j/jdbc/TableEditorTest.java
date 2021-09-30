package ru.job4j.jdbc;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLException;
import java.util.Properties;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TableEditorTest {
    TableEditor editor;

    @Before
    public void getEditor() throws Exception {
        var properties = new Properties();
        properties.load(new FileReader("./data/jdbc/app.properties"));
        editor = new TableEditor(properties);
    }

    @Test
    public void whenTableCreatedSuccessfully() throws Exception {
        var tableName = "animals";
        editor.createTable(tableName);
        editor.getTableScheme(tableName);
    }

    @Test(expected = SQLException.class)
    public void whenTableDeletedSuccessfully() throws Exception {
        var tableName = "animals";
        editor.createTable(tableName);
        editor.dropTable(tableName);
        editor.getTableScheme(tableName);
    }

    @Test
    public void whenColumnAddedSuccessfully() throws Exception {
        var tableName = "animals";
        editor.createTable(tableName);
        editor.addColumn(tableName, "name", "varchar");
        var added = Pattern.compile("(?m)^name\\s+\\|varchar");
        assertTrue(added.matcher(editor.getTableScheme(tableName)).find());
    }

    @Test
    public void whenColumnDeletedSuccessfully() throws Exception {
        var tableName = "animals";
        editor.createTable(tableName);
        editor.addColumn(tableName, "name", "varchar");
        var added = Pattern.compile("(?m)^name\\s+\\|varchar");
        editor.dropColumn(tableName, "name");
        assertFalse(added.matcher(editor.getTableScheme(tableName)).find());
    }

    @Test
    public void whenColumnRenamedSuccessfully() throws Exception {
        var tableName = "animals";
        editor.createTable(tableName);
        editor.addColumn(tableName, "name", "varchar");
        editor.renameColumn(tableName, "name", "firstname");
        var renamed = Pattern.compile("(?m)^firstname\\s+\\|varchar");
        assertTrue(renamed.matcher(editor.getTableScheme(tableName)).find());
    }
}