package model.buffs;

import model.Cell;
import model.cards.Minion;

public class DisarmBuff extends Buff{

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().assignCanCounterAttack(false);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.assignCanCounterAttack(true);
        minion.deleteActiveBuff(this);
    }
}
