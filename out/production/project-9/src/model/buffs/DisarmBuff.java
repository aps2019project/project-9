package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class DisarmBuff extends Buff {

    public DisarmBuff(int turnsActive, boolean isForAllTurns, boolean isContinous) {
        super(BuffName.DISARM, turnsActive,
                isForAllTurns, false, isContinous);
    }

    @Override
    public void startBuff(Cell cell) {
        if(cell.hasCardOnIt()) {
            cell.getMinionOnIt().assignCanCounterAttack(false);
            cell.getMinionOnIt().addActiveBuff(this);
            if (this.isContinous && !cell.getMinionOnIt().getContinuousBuffs().contains(this))
                cell.getMinionOnIt().addContinuous(this);
        }
    }

    @Override
    public void endBuff(Minion minion) {
        minion.assignCanCounterAttack(true);
    }
}
