package ru.otus.hw7.department.chain;

import ru.otus.hw7.atm.Atm;

public abstract class AtmProcessor {
    private AtmProcessor next;

    private AtmProcessor getNext() { return next; }

    public void setNext(AtmProcessor next) {
        this.next = next;
    }

    public void process(Atm atm) {
        if (getNext() != null) {
            getNext().process(atm);
        }
    }
}
