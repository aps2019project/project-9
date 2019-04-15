package model.buffs;

import model.Cell;
import model.cards.Minion;

public class StunBuff extends Buff{

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().setCanAttack(false);
        cell.getMinionOnIt().setCanMove(false);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.setCanMove(true);
        minion.setCanAttack(true);
        minion.deleteActiveBuff(this);
    }
}
