package model.buffs;

import model.Cell;
import model.cards.Minion;

public class WeaknessBuff extends Buff {
    private int power;
    private boolean isForHP;
    public WeaknessBuff(){
        // ...
    }
    @Override
    public void startBuff(Cell cell) {
        if (isForHP)
            cell.getMinionOnIt().reduceHP(power);
        else
            cell.getMinionOnIt().reduceAP(power);
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
