package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class HollyBuff extends Buff{

    public HollyBuff(int turnsActive,
                     boolean isForAllTurns, boolean isContinous) {
        super(BuffName.HOLLY, turnsActive, isForAllTurns, true, isContinous);
    }

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().setReductionOfOthersAttack(1);
        cell.getMinionOnIt().addActiveBuff(this);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.setReductionOfOthersAttack(0);
    }
}
