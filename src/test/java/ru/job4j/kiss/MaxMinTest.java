package ru.job4j.kiss;

import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

public class MaxMinTest {
    @Test
    public void whenListIsEmpty() {
        var maxMin = new MaxMin();
        List<Integer> numbers = List.of();
        assertNull(maxMin.max(numbers,
                Comparator.naturalOrder()));
    }

    @Test
    public void whenMaxFromListOfInteger() {
        var maxMin = new MaxMin();
        List<Integer> numbers = List.of(1, 2, 3, 9, 4, 5, 0);
        assertEquals(9L, (long) maxMin.max(numbers,
                Comparator.naturalOrder()));
    }

    @Test
    public void whenMinFromListOfInteger() {
        var maxMin = new MaxMin();
        List<Integer> numbers = List.of(1, 2, 3, 9, 4, 5, 0);
        assertEquals(0L, (long) maxMin.min(numbers,
                Comparator.naturalOrder()));
    }

    @Test
    public void whenMaxFromListOfString() {
        var maxMin = new MaxMin();
        List<String> words = List.of("aa", "ab", "ac", "ba", "za");
        assertEquals("za", maxMin.max(words,
                Comparator.naturalOrder()));
    }

    @Test
    public void whenMinFromListOfString() {
        var maxMin = new MaxMin();
        List<String> words = List.of("aa", "ab", "ac", "ba", "za");
        assertEquals("aa", maxMin.min(words,
                Comparator.naturalOrder()));
    }
}