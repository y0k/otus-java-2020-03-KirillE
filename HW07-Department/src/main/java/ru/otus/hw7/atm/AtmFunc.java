package ru.otus.hw7.atm;

public interface AtmFunc {
    int getTotal();
    void addMoney (Nominal nominal, int count);
    void getMoney (int count);
}
