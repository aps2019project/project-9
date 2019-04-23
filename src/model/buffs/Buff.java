package model.buffs;

import model.Cell;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.BuffName;

import java.util.ArrayList;

public abstract class Buff {
    protected BuffName buffName;
    protected int turnsActive;
    protected int turnsRemained;
    private static ArrayList<Buff> buffs = new ArrayList<>();
    protected boolean isForAllTurns;
    protected Card affectingCard; // the card that this buff has affect on
    protected boolean isDisabledThisTurn = false;
    protected boolean isPositive;
    protected boolean isContinous;
    public BuffName getBuffName(){
        return buffName;
    }
    public abstract void startBuff(Cell cell);
    public abstract void endBuff(Minion minion);
    public boolean isPositiveBuff(){
        return isPositive;
    }
    public void addAffectingCard(Card card){
        this.affectingCard = card;
    }
    public boolean getIsContinuous(){
        return isContinous;
    }
}
