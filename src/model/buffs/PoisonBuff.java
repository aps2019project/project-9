package model.buffs;

import model.Cell;
import model.cards.Minion;

public class PoisonBuff extends Buff{
    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().reduceHP(1);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.deleteActiveBuff(this);
    }
}
