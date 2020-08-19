package ru.otus.hw7.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cassete implements Comparable<Cassete> {
    private static final Logger logger = LoggerFactory.getLogger(Cassete.class);
    private final Nominal nominal;
    private final int nominalValue;
    private int countMoney = 0;

    public Cassete(Nominal nominal) {
        this.nominal = nominal;
        this.nominalValue = nominal.value;
    }

    public void addNominal(int count) { this.countMoney = this.countMoney + count; }

    public void rmvNominal(int count) {
        if(countMoney - count >= 0) {
            this.countMoney = this.countMoney - count;
        } else {
            logger.info("Has no some nominal");
        }
    }

    public int getNominalValue() { return nominalValue; }

    public int getTotalMoney() { return countMoney * nominalValue; }

    public int getCountMoney() { return countMoney; }

    public Nominal getNominal() {
        return nominal;
    }

    @Override
    public int compareTo(Cassete o) { return  (o.getNominalValue() - this.getNominalValue()); }

    @Override
    public String toString() {
        return "Cassete{" +"Nominal=" + nominal + " nominalValue=" + nominalValue + " countMoney=" + countMoney + '}';
    }
}
