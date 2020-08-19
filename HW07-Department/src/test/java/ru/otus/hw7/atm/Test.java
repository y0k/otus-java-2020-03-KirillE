package ru.otus.hw7.atm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.hw7.department.Department;

public class Test {
    private static final Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        Atm atmV = new Atm("VTB");
        Atm atmS = new Atm("Sber");
        atmS.addMoney(Nominal.TEN, 3);
        atmV.addMoney(Nominal.FIFTY, 3);

        Department department = new Department(atmS, atmV);

        department.gatherAtms();
        logger.info("Money in ATMs ");
        department.getAtmsMoney();
        atmS.addMoney(Nominal.TEN, 3);
        atmV.addMoney(Nominal.FIVE_HUNDRED, 3);
        logger.info("Money in ATMs after put some ");
        department.getAtmsMoney();
        department.restoreZeroCondition();
        logger.info("Zero condition balance");
        department.getAtmsMoney();
    }
}
