package ru.otus.hw7.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw7.department.chain.AtmProcessor;
import ru.otus.hw7.department.observer.Listener;
import java.util.*;

public class Atm extends AtmProcessor implements AtmFunc, Listener {
    private static final Logger logger = LoggerFactory.getLogger(Atm.class);
    private Set<Cassete> cas = new TreeSet<>();
    private final String name;

    public Atm(String name) {
        this.name = name;
        for (Nominal nominal : Nominal.values()) {
            cas.add(new Cassete(nominal));
        }
    }

    @Override
    public int getTotal() {
        return cas.stream().map(x -> x.getTotalMoney()).reduce(0, Integer::sum);
    }

    @Override
    public void addMoney(Nominal nominal, int count) {
        cas.stream().filter(cassete -> cassete.getNominalValue() == nominal.value)
                .forEach(cassete -> cassete.addNominal(count));
    }

    @Override
    public void getMoney(int count) {
        int takenCount = count;
        Map takenNominal = new HashMap<Nominal, Integer>();
        for (Cassete cassete : cas) {
            int countNominal = 0;
            for (int i = cassete.getCountMoney(); i > 0 && cassete.getNominalValue() <= takenCount; i--) {
                takenNominal.put(cassete.getNominalValue(), ++countNominal);
                takenCount = takenCount - cassete.getNominalValue();
            }
        }

        if (takenCount == 0) {
            for (Cassete cassete : cas) {
                int nominalValue = cassete.getNominalValue();
                if (takenNominal.containsKey(nominalValue)) {
                    cassete.rmvNominal((Integer) takenNominal.get(nominalValue));
                }
            }
            logger.info("Take: " + count);
            logger.info("Used nominals:" + takenNominal);
        } else {
            logger.info("Has no money");
        }
    }

    public Atm getCopy() {
        Atm copy = new Atm(this.getName());
        for (Cassete cassete : this.getCas()) {
            copy.addMoney(cassete.getNominal(), cassete.getCountMoney());
        }
        return copy;
    }

    public String getName() {
        return name;
    }

    public Set<Cassete> getCas() {
        return cas;
    }

    public void setCas(Set<Cassete> cas) {
        this.cas = cas;
    }

    @Override
    public String toString() {
        return "Atm{" + "name=" + name + ", cas='" + cas + '\'' + '}';
    }

    public void onUpdate() {
        logger.info("Atm: " + getName() + "; Total: " + getTotal());
    }
}
