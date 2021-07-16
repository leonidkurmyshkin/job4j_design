package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

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
        var index = getIndex(key);
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

    private int getIndex(K key) {
        return key == null
                ? 0
                : indexFor(hash(key.hashCode()));
    }

    private void expand() {
        capacity *= 2;
        var oldTable = table;
        table = new MapEntry[capacity];
        for (var entry : oldTable) {
            if (entry != null) {
                table[getIndex(entry.key)] = entry;
            }
        }
    }

    @Override
    public V get(K key) {
        var entry = table[getIndex(key)];
        return entry != null && Objects.equals(key, entry.key)
                ? entry.value
                : null;
    }

    @Override
    public boolean remove(K key) {
        var index = getIndex(key);
        var rsl = table[index] != null
                && Objects.equals(key, table[index].key);
        if (rsl) {
            table[index] = null;
            count--;
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