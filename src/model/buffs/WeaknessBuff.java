package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class WeaknessBuff extends Buff {
    private int power;
    private boolean isForHP;

    public WeaknessBuff(int turnsActive) {
        this.buffName = BuffName.WEAKNESS;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public WeaknessBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.WEAKNESS;
        this.isPositive = false;
    }

    @Override
    public void startBuff(Cell cell) {
        if (isForHP)
            cell.getMinionOnIt().reduceHP(power);
        else
            cell.getMinionOnIt().reduceAP(power);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        if (isForHP)
            minion.addHP(power);
        else
            minion.addAP(power);
        minion.deleteActiveBuff(this);
    }
}
