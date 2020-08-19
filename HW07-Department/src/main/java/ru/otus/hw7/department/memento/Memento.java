package ru.otus.hw7.department.memento;

public class Memento {
    private final State state;

    Memento(State state) { this.state = new State(state); }

    State getState() { return state; }
}
