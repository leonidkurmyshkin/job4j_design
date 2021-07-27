package ru.job4j.io;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;

public class AnalysisTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();


    @Test
    public void whenPermanentAvailable() throws IOException {
        String source = "./data/whenPermanentAvailable.log";
        var target = folder.newFile("whenPermanentAvailableRsl.csv");
        Analysis.unavailable(source, target.getAbsolutePath());
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), isEmptyOrNullString());
        }
    }

    @Test
    public void whenOneRangeUnavailable() throws IOException {
        String source = "./data/whenOneRangeUnavailable.log";
        var target = folder.newFile("whenOneRangeUnavailableRsl.csv");
        Analysis.unavailable(source, target.getAbsolutePath());
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;11:02:02;"));
        }
    }

    @Test
    public void whenTwoRangeUnavailable() throws IOException {
        String source = "./data/whenTwoRangeUnavailable.log";
        var target = folder.newFile("whenTwoRangeUnavailableRsl.csv");
        Analysis.unavailable(source, target.getAbsolutePath());
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;10:59:01;"));
            assertThat(in.readLine(), is("11:01:02;11:02:02;"));
        }
    }

    @Test
    public void whenEndingIsUnknown() throws IOException {
        String source = "./data/whenEndingIsUnknown.log";
        var target = folder.newFile("whenEndingIsUnknownRsl.csv");
        Analysis.unavailable(source, target.getAbsolutePath());
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}