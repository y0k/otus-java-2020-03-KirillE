package ru.otus.hw7.department.memento;

import ru.otus.hw7.atm.Atm;

public class State {
    private final Atm atm;

    public State(Atm atm) { this.atm = atm; }

    public State(State state) { this.atm = state.getAtm().getCopy(); }

    public Atm getAtm() { return atm; }

    @Override
    public String toString() {
        return "State{" + "atm=" + atm + '}';
    }
}
