package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class StunBuff extends Buff{

    public StunBuff(int turnsActive) {
        this.buffName = BuffName.STUN;
        this.turnsActive = turnsActive;
        this.isPositive = false;
    }

    public StunBuff(boolean isForAllTurns , boolean isContinuous){
        if(isContinuous)
            turnsActive = 1;
        else
            this.isForAllTurns = isForAllTurns;
        this.buffName = BuffName.STUN;
        this.isPositive = false;
    }

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().setCanAttack(false);
        cell.getMinionOnIt().setCanMove(false);
        if(this.isContinous)
            cell.getMinionOnIt().addContinuous(this);
    }

    @Override
    public void endBuff(Minion minion) {
        minion.setCanMove(true);
        minion.setCanAttack(true);
        minion.deleteActiveBuff(this);
    }
}