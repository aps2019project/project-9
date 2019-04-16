package model.buffs;

import model.Cell;
import model.cards.Minion;

public class HollyBuff extends Buff{

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().setReductionOfOthersAttack(1);
        if(this.isContinous)
            cell.getMinionOnIt().addContinous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.setReductionOfOthersAttack(0);
        minion.deleteActiveBuff(this);
    }
}
