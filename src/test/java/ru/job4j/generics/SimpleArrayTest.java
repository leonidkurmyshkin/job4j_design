package ru.job4j.generics;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
import org.junit.Test;

public class SimpleArrayTest {
    @Test
    public void addStringThenGet() {
        SimpleArray<String> words = new SimpleArray<>(5);
        words.add("one");
        assertThat(words.get(0), is("one"));
    }

    @Test
    public void addNullThenGet() {
        SimpleArray<String> words = new SimpleArray<>(5);
        words.add(null);
        assertNull(words.get(0));
    }

    @Test
    public void addIntThenSet() {
        SimpleArray<Integer> numbers = new SimpleArray<>(5);
        numbers.add(1);
        numbers.set(0, 5);
        assertThat(numbers.get(0), is(5));
    }

    @Test
    public void add1ThenAdd2ThenRemove1ThenRemove2() {
        SimpleArray<Integer> numbers = new SimpleArray<>(5);
        numbers.add(1);
        numbers.add(2);
        numbers.remove(0);
        assertThat(numbers.get(0), is(2));
        assertThat(numbers.getSize(), is(1));
        numbers.remove(0);
        assertThat(numbers.getSize(), is(0));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetException() {
        SimpleArray<Integer> numbers = new SimpleArray<>(1);
        numbers.add(1);
        numbers.get(1);
    }

    @Test
    public void testIterator() {
        SimpleArray<Integer> numbers = new SimpleArray<>(3);
        numbers.add(1);
        numbers.add(null);
        numbers.add(3);
        var it = numbers.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertNull(it.next());
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
    }
}