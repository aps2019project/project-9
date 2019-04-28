package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class WeaknessBuff extends Buff {
    private int power;
    private boolean isForHP; // is for reducing HP or reducing AP

    public WeaknessBuff(int turnsActive, int turnsRemained,
                        boolean isForAllTurns, boolean isContinous, int power, boolean isForHP) {
        super(BuffName.WEAKNESS, turnsActive,isForAllTurns, false, isContinous);
        this.power = power;
        this.isForHP = isForHP;
    }


    @Override
    public void startBuff(Cell cell) {
        if (isForHP)
            cell.getMinionOnIt().reduceHP(power);
        else
            cell.getMinionOnIt().reduceAP(power);
        cell.getMinionOnIt().addActiveBuff(this);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        if (isForHP)
            minion.addHP(power);
        else
            minion.addAP(power);
    }
}
