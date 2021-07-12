package ru.job4j.collection;

import java.util.*;

public class SimpleArray<T> implements Iterable<T> {
    private T[] array;
    private  int size = 0;
    private int modCount = 0;

    public SimpleArray(int length) {
        array = (T[]) new Object[length];
    }

    public SimpleArray() {
        this(5);
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        return array[index];
    }

    public void add(T model) {
        if (size == array.length) {
            array = Arrays.copyOf(array, size + size / 2 + 1);
        }
        array[size++] = model;
        modCount++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int expectedModCount = modCount;
            private int pointer = 0;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return pointer < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[pointer++];
            }
        };
    }
}
