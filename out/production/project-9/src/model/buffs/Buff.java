package model.buffs;

import model.Cell;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.BuffName;

import java.util.ArrayList;

public abstract class Buff {
    protected String name;
    protected BuffName buffName;
    protected int turnsActive;
    protected int turnsRemained;
    protected boolean isForAllTurns; // means : DAEMI
    protected boolean isPositive;
    protected boolean isContinous; // means : continous

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Buff(BuffName buffName, int turnsActive,
                boolean isForAllTurns, boolean isPositive,
                boolean isContinous) {
        this.buffName = buffName;
        this.turnsActive = (isContinous) ? turnsActive : 2 * turnsActive; // not counting the opponent's turns
        this.turnsRemained = (isContinous) ? turnsActive : 2 * turnsActive;// not counting
        this.isForAllTurns = isForAllTurns;
        this.isPositive = isPositive;
        this.isContinous = isContinous;
        if (isContinous)
            this.isForAllTurns = false;
        else if (isForAllTurns)
            this.isContinous = false;
    }

    public BuffName getBuffName() {
        return buffName;
    }

    public abstract void startBuff(Cell cell);

    public abstract void endBuff(Minion minion);

    public boolean isPositiveBuff() {
        return isPositive;
    }

    public boolean getIsContinuous() {
        return isContinous;
    }

    public void reduceTurnsRemained() {
        if (turnsRemained != 0)
            turnsRemained--;
    }

    public int getTurnsRemained() {
        return turnsRemained;
    }

    public boolean isForAllTurns() {
        return isForAllTurns;
    }

    public void setTurnsActive(int turnsActive) {
        this.turnsActive = turnsActive;
    }

    public void setTurnsRemained(int turnsRemained) {
        this.turnsRemained = turnsRemained;
    }

    public Buff getCopy() {
        if (this instanceof DisarmBuff)
            return new DisarmBuff(turnsActive / 2, isForAllTurns, isContinous);
        else if (this instanceof HollyBuff)
            return new HollyBuff(turnsActive / 2, isForAllTurns, isContinous, ((HollyBuff) this).getIsNegative());
        else if (this instanceof PoisonBuff)
            return new PoisonBuff(turnsActive / 2, isForAllTurns, isContinous);
        else if (this instanceof PowerBuff)
            return new PowerBuff(turnsActive / 2, isForAllTurns, isContinous,
                    ((PowerBuff) this).getPower(), ((PowerBuff) this).getIsForHP());
        else if (this instanceof StunBuff)
            return new StunBuff(turnsActive / 2, isForAllTurns, isContinous);
        else
            return new WeaknessBuff(turnsActive / 2, isForAllTurns, isContinous, ((WeaknessBuff) this).getPower()
                    , ((WeaknessBuff) this).getIsForHP(), ((WeaknessBuff) this).getIsDelayBuff(),
                    ((WeaknessBuff) this).getPowers());
    }

    @Override
    public String toString() {
        return name;
    }
}
