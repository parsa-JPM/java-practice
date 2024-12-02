package william;

import william.model.Bonus;
import william.model.Deposit;

public interface BonusEngine {
   Bonus calculate(Deposit deposit);
}
