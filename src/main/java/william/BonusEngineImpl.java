package william;

import william.model.Bonus;
import william.model.Deposit;

import java.math.BigDecimal;
import java.util.Random;

public class BonusEngineImpl implements BonusEngine {

    boolean chance;

    @Override
    public Bonus calculate(Deposit deposit) {
        if (deposit.getAmount().compareTo(new BigDecimal("5")) < 0) {
            return new Bonus(new BigDecimal("0"));
        }

        if (deposit.getAmount().compareTo(new BigDecimal("5")) >= 0 && deposit.getAmount().compareTo(new BigDecimal("10")) <= 0) {
            return new Bonus(new BigDecimal("5"));
        }


        Random rnd = new Random();
        boolean chance = rnd.nextDouble() <= 0.25;

        if (chance) {
            return new Bonus(deposit.getAmount().divide(new BigDecimal("2")));
        }

        return new Bonus(new BigDecimal("0"));
    }
}
