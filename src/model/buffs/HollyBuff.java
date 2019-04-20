package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class HollyBuff extends Buff{

    public HollyBuff(int turnsActive) {
        this.buffName = BuffName.HOLLY;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public HollyBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.HOLLY;
        this.isPositive = false;
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
        minion.deleteActiveBuff(this);
    }
}
