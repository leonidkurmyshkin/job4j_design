package ru.job4j.cache;

public interface Action {
    String takeName();

    boolean execute(Input in, Emulator emul);
}
