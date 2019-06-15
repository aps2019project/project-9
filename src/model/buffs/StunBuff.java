package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class StunBuff extends Buff{


    public StunBuff(int turnsActive, boolean isForAllTurns, boolean isContinous) {
        super(BuffName.STUN, turnsActive, isForAllTurns, false, isContinous);
    }

    @Override
    public void startBuff(Cell cell) {
        if(cell.hasCardOnIt()) {
            cell.getMinionOnIt().setCanAttack(false);
            cell.getMinionOnIt().setCanMove(false);
            cell.getMinionOnIt().addActiveBuff(this);
            if (this.isContinous && !cell.getMinionOnIt().getContinuousBuffs().contains(this))
                cell.getMinionOnIt().addContinuous(this);
        }
    }

    @Override
    public void endBuff(Minion minion) {

        minion.setCanMove(true);
        minion.setCanAttack(true);
    }
}
