package model.buffs;


import model.Cell;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.PowerType;

public class PowerBuff extends Buff {
    private int power;
    private boolean isForHP;
    private PowerType powerType = PowerType.INTEGER;
    public PowerBuff(){
        // ...
    }
    @Override
    public void startBuff(Cell cell) {
        if (isForHP) {
            cell.getMinionOnIt().addHP(power);
        } else {
            cell.getMinionOnIt().addAP(power);
        }
        if(this.isContinous)
            cell.getMinionOnIt().addContinous(this);
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
