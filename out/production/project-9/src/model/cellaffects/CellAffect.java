package model.cellaffects;

import model.Cell;
import model.cards.Minion;
import model.enumerations.CellAffectName;

import java.util.ArrayList;

public abstract class CellAffect {
    protected CellAffectName name;
    protected int turnsActive;
    protected int turnsRemained;
    protected Cell affectedCell;

    public CellAffect(CellAffectName name, int turnsActive) {
        this.name = name;
        this.turnsActive = turnsActive;
        this.turnsRemained = turnsActive;
    }

    public abstract void castCellAffect(Minion minion); // when minion comes on it

    public void putCellAffect(Cell cell) {
        cell.addCellAffect(this);
        affectedCell = cell;
    }

    public void nextTurn() {
        if (turnsRemained != 0)
            turnsRemained--;
    }

    public int getTurnsRemained() {
        return turnsRemained;
    }

    public Cell getAffectedCell() {
        return affectedCell;
    }

    public CellAffect getCopy() {
        if (this instanceof FireCellAffect) {
            return new FireCellAffect(turnsActive);
        } else if (this instanceof HollyCellAffect) {
            return new HollyCellAffect(turnsActive);
        } else {
            return new PoisonCellAffect(turnsActive);
        }
    }
}
