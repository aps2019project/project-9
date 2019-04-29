package model.buffs;


import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;
import model.enumerations.PowerType;

public class PowerBuff extends Buff {
    private int power;
    private boolean isForHP; // for HP or AP

    public PowerBuff(int turnsActive, int turnsRemained, boolean isForAllTurns,
                      boolean isContinous, int power, boolean isForHP) {
        super(BuffName.POWER, turnsActive,isForAllTurns, true, isContinous);
        this.power = power;
        this.isForHP = isForHP;
    }

    @Override
    public void startBuff(Cell cell) {
        if (isForHP) {
            cell.getMinionOnIt().addHP(power);
        } else {
            cell.getMinionOnIt().addAP(power);
        }
        cell.getMinionOnIt().addActiveBuff(this);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        if (isForHP)
            minion.reduceHP(power);
        else
            minion.reduceAP(power);
    }
}
