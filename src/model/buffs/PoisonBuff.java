package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class PoisonBuff extends Buff{
    public PoisonBuff(int turnsActive,
                      boolean isForAllTurns,boolean isContinous) {
        super(BuffName.POISON, turnsActive, isForAllTurns, false, isContinous);
    }


    @Override
    public void startBuff(Cell cell) {
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
        cell.getMinionOnIt().reduceHP(1);
        cell.getMinionOnIt().addActiveBuff(this);
    }

    @Override
    public void endBuff(Minion minion) {
    }
}
