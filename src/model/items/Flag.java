package model.items;

import model.Cell;
import model.Player;
import model.cards.Minion;

public class Flag extends Item{
    Cell currentCell;
    Player owningPlayer;
    Minion owningMinion;

    public Minion getOwningMinion() {
        return owningMinion;
    }

    public Player getOwningPlayer() {
        return owningPlayer;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }
}
