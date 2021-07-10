package ru.job4j.collection;

public class SimpleQueue<T> {
    private final SimpleStack<T> in = new SimpleStack<>();
    private int inSize = 0;
    private final SimpleStack<T> out = new SimpleStack<>();
    private int outSize = 0;

    public T poll() {
        if (outSize == 0) {
            outSize = inSize;
            for (; inSize > 0; inSize--) {
                out.push(in.pop());
            }
        }
        outSize--;
        return out.pop();
    }

    public void push(T value) {
        in.push(value);
        inSize++;
    }
}