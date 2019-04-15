package model.cellaffects;

import model.Cell;
import model.cards.Minion;
import model.enumerations.CellAffectName;

import java.util.ArrayList;

public abstract class CellAffect {
    protected CellAffectName name;
    protected int turnsActive;
    private static ArrayList<CellAffect> cellAffects = new ArrayList<>();
    protected Cell affectedCell;

    public abstract void castCellAffect(Minion minion);
    public abstract void expireCellAffect();
    public void putCellAffect(Cell cell){

    }
}
