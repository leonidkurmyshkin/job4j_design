package ru.job4j.iterator;

import static org.hamcrest.Matchers.is;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertThat;

public class ListUtilsTest {

    @Test
    public void whenAddBefore() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 1, 2);
        assertThat(Arrays.asList(1, 2, 3), is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddBeforeWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(Arrays.asList(1, 3));
        ListUtils.addBefore(input, 2, 2);
    }

    @Test
    public void whenAddAfterLast() {
        List<Integer> input = new ArrayList<>(Arrays.asList(0, 1, 2));
        ListUtils.addAfter(input, 2, 3);
        assertThat(Arrays.asList(0, 1, 2, 3), is(input));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenAddAfterWithInvalidIndex() {
        List<Integer> input = new ArrayList<>(List.of(1, 3));
        ListUtils.addAfter(input, 2, 2);
    }

    @Test
    public void whenRemoveIf() {
        List<Integer> input = new ArrayList<>(List.of(0, 1, 0, 2, 0));
        ListUtils.removeIf(input, el -> el == 0);
        assertThat(input, is(List.of(1, 2)));
    }

    @Test
    public void whenReplaceIf() {
        List<Integer> input = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        ListUtils.replaceIf(input, el -> el < 2 || el > 4, 9);
        assertThat(input, is(List.of(9, 2, 3, 4, 9)));
    }

    @Test
    public void whenRemoveAll() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        List<Integer> delNumbers = List.of(1, 5, 6, 9);
        ListUtils.removeAll(numbers, delNumbers);
        assertThat(numbers, is(List.of(2, 3, 4)));
    }
}