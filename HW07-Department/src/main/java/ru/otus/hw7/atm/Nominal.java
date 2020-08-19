package ru.otus.hw7.atm;

public enum Nominal {

        TEN(10),
        FIFTY(50),
        HUNDRED(100),
        FIVE_HUNDRED(500),
        THOUSAND(1000);

        public int value;
        Nominal(int value) {
            this.value = value;
        }

}