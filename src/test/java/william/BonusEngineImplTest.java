package william;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import william.model.Bonus;
import william.model.Deposit;

import java.math.BigDecimal;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BonusEngineImplTest {


    @Test
    public void lessThan5() {
        var bEngine = new BonusEngineImpl();

        var res = bEngine.calculate(new Deposit(new BigDecimal("4")));

        Assertions.assertEquals(new Bonus(new BigDecimal("0")), res);

    }

    @Test
    public void between5and10() {
        var bEngine = new BonusEngineImpl();

        var res = bEngine.calculate(new Deposit(new BigDecimal("7")));
        Assertions.assertEquals(new Bonus(new BigDecimal("5")), res);

        res = bEngine.calculate(new Deposit(new BigDecimal("10")));
        Assertions.assertEquals(new Bonus(new BigDecimal("5")), res);

        res = bEngine.calculate(new Deposit(new BigDecimal("5")));
        Assertions.assertEquals(new Bonus(new BigDecimal("5")), res);

        for (int i = 0; i < 10000; i++) {
            // todo mock the chance variable
            res = bEngine.calculate(new Deposit(new BigDecimal("12")));
            Assertions.assertEquals(new Bonus(new BigDecimal("0")), res);
        }
    }


    @Test
    public void bonus25Percent() {
        var bEngine = new BonusEngineImpl();

        Bonus res;
        int success = 0;
        for (int i = 0; i < 10000; i++) {
            res = bEngine.calculate(new Deposit(new BigDecimal("12")));
            if (res.getWinAmont().compareTo(new BigDecimal("0")) > 0)
                success++;
        }
        System.out.println(success);
//        Assertions.assertEquals(new Bonus(new BigDecimal("5")), res);
    }

}