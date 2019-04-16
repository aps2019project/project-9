package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class DisarmBuff extends Buff {

    public DisarmBuff(int turnsActive) {
        this.buffName =BuffName.DISARM;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public DisarmBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.DISARM;
        this.isPositive = false;
    }



    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().assignCanCounterAttack(false);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.assignCanCounterAttack(true);
        minion.deleteActiveBuff(this);
    }
}
