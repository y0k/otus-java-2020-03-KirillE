package ru.otus.hw7.department.memento;

import java.util.ArrayDeque;
import java.util.Deque;

public class Originator {
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void saveState(State state) { stack.push(new Memento(state)); }

    public State restoreState() { return stack.pop().getState(); }
}
