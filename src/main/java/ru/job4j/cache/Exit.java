package ru.job4j.cache;

public class Exit implements Action {
    @Override
    public String takeName() {
        return "Выйти из программы";
    }

    @Override
    public boolean execute(Input in, Emulator emul) {
        return false;
    }
}
