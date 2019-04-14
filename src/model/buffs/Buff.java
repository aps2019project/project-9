package model.buffs;

import model.Cell;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.BuffName;

import java.util.ArrayList;

public abstract class Buff {
    protected BuffName buffName;
    protected int turnsActive;
    private static ArrayList<Buff> buffs = new ArrayList<>();
    protected boolean isPassive;
    protected Card affectingCard; // the card that this buff has affect on
    protected boolean isDisabledThisTurn = false;

    public abstract void startBuff(Cell cell);
    public abstract void endBuff(Minion minion);

}
