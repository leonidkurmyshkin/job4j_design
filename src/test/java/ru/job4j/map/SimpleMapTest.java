package ru.job4j.map;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleMapTest {
    @Test
    public void whenPutThenGet() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertThat(map.get("Жёлтый"), is(1));
        assertTrue(map.put(null, 10));
        assertThat(map.get(null), is(10));
    }

    @Test
    public void whenPutThenCollisionPutThenGet() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertFalse(map.put("Жёлтый", 5));
        assertThat(map.get("Жёлтый"), is(1));
    }

    @Test
    public void whenMultiplePutThenRehashThenGet() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertTrue(map.put("Белый", 2));
        assertTrue(map.put("Оранжевый", 3));
        assertTrue(map.put("Зеленый", 4));
        assertTrue(map.put("Серый", 5));
        assertTrue(map.put("Черный", 6));
        assertTrue(map.put("Бирюзовый", 11));
        assertTrue(map.put("Лимонный", 12));
        assertThat(map.get("Лимонный"), is(12));
        assertThat(map.get("Белый"), is(2));
    }

    @Test
    public void whenNoMappingGet() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertNull(map.get("Белый"));
    }

    @Test
    public void whenAddThenRemove() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertFalse(map.remove("Белый"));
        assertTrue(map.remove("Жёлтый"));
        assertNull(map.get("Жёлтый"));
    }

    @Test
    public void whenIteratorNext() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertTrue(map.put("Белый", 2));
        assertTrue(map.put("Оранжевый", 3));
        var iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());
        assertNotNull(iterator.next());
        assertNotNull(iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void whenGetEmptyFromIterator() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        var iterator = map.iterator();
        assertNotNull(iterator.next());
        assertNotNull(iterator.next());
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void IteratorFailFastBehaviourAfterRemove() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertTrue(map.put("Белый", 2));
        assertTrue(map.put("Оранжевый", 3));
        var iterator = map.iterator();
        assertNotNull(iterator.next());
        assertTrue(map.remove("Жёлтый"));
        iterator.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void IteratorFailFastBehaviourAfterPut() {
        var map = new SimpleMap<String, Integer>();
        assertTrue(map.put("Жёлтый", 1));
        assertTrue(map.put("Белый", 2));
        assertTrue(map.put("Оранжевый", 3));
        var iterator = map.iterator();
        assertNotNull(iterator.next());
        assertTrue(map.put("Фиолетовый", 15));
        iterator.next();
    }
}