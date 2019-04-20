package model.buffs;


import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;
import model.enumerations.PowerType;

public class PowerBuff extends Buff {
    private int power;
    private boolean isForHP;
    private PowerType powerType = PowerType.INTEGER;

    public PowerBuff(int turnsActive) {
        this.buffName = BuffName.POWER;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public PowerBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.POWER;
        this.isPositive = false;
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
        minion.deleteActiveBuff(this);
    }
}
