package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class HollyBuff extends Buff {
    private boolean isNegative; // just for minion GHOOL PEYKAR SNAKE it should be true

    public HollyBuff(int turnsActive,
                     boolean isForAllTurns, boolean isContinous, boolean isNegative) {
        super(BuffName.HOLLY, turnsActive, isForAllTurns, true, isContinous);
        this.isNegative = isNegative;
    }

    @Override
    public void startBuff(Cell cell) {
        if (cell.hasCardOnIt()) {
            if (!isNegative) {
                cell.getMinionOnIt().setReductionOfOthersAttack(1);
                cell.getMinionOnIt().addActiveBuff(this);
                cell.getMinionOnIt().gotHollyBuff();
                if (this.isContinous && !cell.getMinionOnIt().getContinuousBuffs().contains(this))
                    cell.getMinionOnIt().addContinuous(this);
            } else
                cell.getMinionOnIt().setReductionOfOthersAttack(-1);
        }
    }

    @Override
    public void endBuff(Minion minion) {
        if(!isNegative){
            minion.setReductionOfOthersAttack(-1);
            minion.missedHollyBuff();
        }else {
            minion.setReductionOfOthersAttack(1);
            minion.missedHollyBuff();
        }
    }

    public boolean getIsNegative(){
        return isNegative;
    }
}
