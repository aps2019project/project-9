package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class PoisonBuff extends Buff{
    public PoisonBuff(int turnsActive) {
        this.buffName = BuffName.POISON;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public PoisonBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.POISON;
        this.isPositive = false;
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
        minion.deleteActiveBuff(this);
    }
}
