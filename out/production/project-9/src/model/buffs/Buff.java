package model.buffs;

import model.Cell;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.BuffName;

import java.util.ArrayList;

public abstract class Buff {
    protected BuffName buffName;
    protected int turnsActive;

    public Buff(BuffName buffName, int turnsActive,
                boolean isForAllTurns, boolean isPositive,
                boolean isContinous) {
        this.buffName = buffName;
        this.turnsActive = turnsActive;
        this.turnsRemained = turnsActive;
        this.isForAllTurns = isForAllTurns;
        this.isPositive = isPositive;
        this.isContinous = isContinous;
        if(isContinous)
            this.isForAllTurns = false;
        else if(isForAllTurns)
            this.isContinous = false;
    }

    protected int turnsRemained;
    private static ArrayList<Buff> buffs = new ArrayList<>();
    protected boolean isForAllTurns; // means : DAEMI
    protected boolean isPositive;
    protected boolean isContinous; // means : continous
    public BuffName getBuffName(){
        return buffName;
    }
    public abstract void startBuff(Cell cell);
    public abstract void endBuff(Minion minion);
    public boolean isPositiveBuff(){
        return isPositive;
    }
    public boolean getIsContinuous(){
        return isContinous;
    }

    public void reduceTurnsRemained() {
        if(turnsRemained != 0)
            turnsRemained--;
    }

    public int getTurnsRemained() {
        return turnsRemained;
    }

    public boolean isForAllTurns() {
        return isForAllTurns;
    }
}
