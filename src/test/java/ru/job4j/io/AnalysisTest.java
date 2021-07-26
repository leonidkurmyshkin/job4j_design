package ru.job4j.io;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.junit.Assert.*;

public class AnalysisTest {
    @Test
    public void whenPermanentAvailable() {
        String source = "./data/whenPermanentAvailable.log";
        String target = "./data/unavailable.csv";
        Analysis.unavailable(source, target);
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), isEmptyOrNullString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenOneRangeUnavailable() {
        String source = "./data/whenOneRangeUnavailable.log";
        String target = "./data/unavailable.csv";
        Analysis.unavailable(source, target);
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;11:02:02;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenTwoRangeUnavailable() {
        String source = "./data/whenTwoRangeUnavailable.log";
        String target = "./data/unavailable.csv";
        Analysis.unavailable(source, target);
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;10:59:01;"));
            assertThat(in.readLine(), is("11:01:02;11:02:02;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenEndingIsUnknown() {
        String source = "./data/whenEndingIsUnknown.log";
        String target = "./data/unavailable.csv";
        Analysis.unavailable(source, target);
        try (var in = new BufferedReader(new FileReader(target))) {
            assertThat(in.readLine(), is("10:57:01;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}