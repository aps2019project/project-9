package model.buffs;

import model.Cell;
import model.cards.Minion;
import model.enumerations.BuffName;

public class DelayBuff extends Buff {

    private Buff buff;

    public DelayBuff(int delay, Buff buff) {
        super(BuffName.DELAY, delay, false, buff.isPositive, false);
        this.buff = buff;
    }

    @Override
    public void startBuff(Cell cell) {
        cell.getMinionOnIt().addActiveBuff(this);
    }

    @Override
    public void endBuff(Minion minion) {
        buff.startBuff(minion.getCell());
    }
}
