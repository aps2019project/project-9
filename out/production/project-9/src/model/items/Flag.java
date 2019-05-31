package model.items;

import model.Cell;
import model.Player;
import model.cards.Minion;
import model.enumerations.ItemName;

public class Flag extends Item {


    private Cell currentCell;
    private Player owningPlayer;
    private Minion owningMinion;
    private int turnsOwned;

    public Flag(Cell currentCell) {
        super(0, "",null, "");
        this.currentCell = currentCell;
    }

    public Flag() {
        super(0, "flag", null, "flag");
    }

    public Minion getOwningMinion() {
        return owningMinion;
    }

    public Player getOwningPlayer() {
        return owningPlayer;
    }

    public void setOwningPlayer(Player owningPlayer) {
        this.owningPlayer = owningPlayer;
    }

    public void setOwningMinion(Minion owningMinion) {
        this.owningMinion = owningMinion;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setTurnsOwned(int turnsOwned) {
        this.turnsOwned = turnsOwned;
    }

    public int getTurnsOwned() {
        return turnsOwned;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public void nextTurn(){
        turnsOwned++;
    }
}
