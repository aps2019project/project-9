package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class WeaknessBuff extends Buff {
    private int power;
    private boolean isForHP; // is for reducing HP or reducing AP
    private boolean isDelayBuff; // for specialPowers of some Minions
    private int[] powers;
    private int turn; // for delay buff

    public WeaknessBuff(int turnsActive, boolean isForAllTurns, boolean isContinous, int power, boolean isForHP
            , boolean isDelayBuff, int[] powers) {
        super(BuffName.WEAKNESS, turnsActive, isForAllTurns, false, isContinous);
        this.power = power;
        this.isForHP = isForHP;
        this.turn = 0;
        this.powers = powers;
        this.isDelayBuff = isDelayBuff;
    }


    @Override
    public void startBuff(Cell cell) {
        if (isDelayBuff) {
            if (turn < powers.length) {
                cell.getMinionOnIt().reduceHP(powers[turn++]);
                cell.getMinionOnIt().addActiveBuff(this);
            }
        } else {
            if (isForHP)
                cell.getMinionOnIt().reduceHP(power);
            else
                cell.getMinionOnIt().reduceAP(power);
            cell.getMinionOnIt().addActiveBuff(this);
            if (this.isContinous && !cell.getMinionOnIt().getContinuousBuffs().contains(this))
                cell.getMinionOnIt().addContinuous(this);
        }
    }

    @Override
    public void endBuff(Minion minion) {
        if (isForHP)
            minion.addHP(power);
        else
            minion.addAP(power);
    }

    public boolean isDelayBuff() {
        return isDelayBuff;
    }
}
