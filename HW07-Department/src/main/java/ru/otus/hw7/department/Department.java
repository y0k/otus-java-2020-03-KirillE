package ru.otus.hw7.department;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw7.atm.Atm;
import ru.otus.hw7.department.chain.AtmProcessor;
import ru.otus.hw7.department.memento.Originator;
import ru.otus.hw7.department.memento.State;
import ru.otus.hw7.department.observer.EventProducer;
import java.util.ArrayList;
import java.util.List;

public class Department implements DepartmentFunc {
    private static final Logger logger = LoggerFactory.getLogger(Department.class);
    private final List<Atm> atms = new ArrayList<>();
    private final EventProducer producer = new EventProducer();
    private final List<Originator> originatorList = new ArrayList<>();

    public Department(Atm... atms) {
        for (Atm atm : atms) {
            Originator originator = new Originator();
            originator.saveState(new State(atm));
            this.atms.add(atm);
            this.producer.addListener(atm);
            this.originatorList.add(originator);
        }
    }


    @Override
    public void gatherAtms() {
        for (int i = 0; i < atms.size(); i++) {
            AtmProcessor cursor = atms.get(i);
            if (atms.size() > 1 && i != atms.size() - 1) {
                cursor.setNext(atms.get(i + 1));
            }
            cursor.process((Atm) cursor);
            logger.info("gathered Atms: " + atms.get(i));
        }
    }

    @Override
    public void getAtmsMoney() {
        producer.event();
    }

    @Override
    public void restoreZeroCondition() {
        for (int i = 0; i < atms.size(); i++) {
            Atm zeroCond = originatorList.get(i).restoreState().getAtm();
            atms.get(i).setCas(zeroCond.getCas());
        }
    }
}
