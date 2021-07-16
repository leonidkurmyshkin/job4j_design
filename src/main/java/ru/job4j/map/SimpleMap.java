package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleMap<K, V> implements Map<K, V> {
    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int modCount = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    @Override
    public boolean put(K key, V value) {
        if ((float) count / capacity >= LOAD_FACTOR) {
            expand();
        }
        var index = key == null ? 0
                : indexFor(hash(key.hashCode()));
        var rsl = table[index] == null;
        if (rsl) {
            table[index] = new MapEntry<>(key, value);
            count++;
            modCount++;
        }
        return rsl;
    }

    private int hash(int hashCode) {
        return hashCode ^ (hashCode >>> 16);
    }

    private int indexFor(int hash) {
        return hash & (capacity - 1);
    }

    private void expand() {
        capacity *= 2;
        var oldTable = table;
        table = new MapEntry[capacity];
        for (var entry : oldTable) {
            if (entry != null) {
                var index = entry.key == null ? 0
                        : indexFor(hash(entry.key.hashCode()));
                table[index] = entry;
            }
        }
    }

    @Override
    public V get(K key) {
        var index = key == null ? 0
                : indexFor(hash(key.hashCode()));
        var entry = table[index];
        return entry == null ? null : entry.value;
    }

    @Override
    public boolean remove(K key) {
        var index = key == null ? 0
                : indexFor(hash(key.hashCode()));
        var rsl = table[index] != null;
        if (rsl) {
            table[index] = null;
            modCount++;
        }
        return rsl;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private int cursor = 0;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                var rsl = false;
                for (; cursor < table.length; cursor++) {
                    if (table[cursor] != null) {
                        rsl = true;
                        break;
                    }
                }
                return rsl;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[cursor++].key;
            }
        };
    }

    private static class MapEntry<K, V> {
        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}