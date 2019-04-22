package model;

import model.enumerations.MinionAttackType;
import model.items.Flag;

import java.util.ArrayList;

public class PlayGround {
    private Cell[][] cells = new Cell[5][9];
    private ArrayList<Flag> flags ; // for mode three
    private Flag flag; // for mode two
    public boolean isForEnemyMinion(Cell cell, Player player) {
        return false;
    }

    public boolean isForFriendlyMinion(Cell cell, Player player) {
        return false;
    }

    public Cell getCell(int x, int y) {
        if ((x - 1) >= 0 && (y - 1) >= 0 && (x - 1) <= 4 && (y - 1) <= 8)
            return cells[x - 1][y - 1];
        else
            return null;
    }

    public ArrayList<Cell> getAroundCells(Cell centerCell) {
        ArrayList<Cell> result = new ArrayList<>();
        result.add(getCell(centerCell.getX() - 1, centerCell.getY()));
        result.add(getCell(centerCell.getX() + 1, centerCell.getY()));
        result.add(getCell(centerCell.getX(), centerCell.getY() - 1));
        result.add(getCell(centerCell.getX(), centerCell.getY() + 1));
        result.add(getCell(centerCell.getX() - 1, centerCell.getY() - 1));
        result.add(getCell(centerCell.getX() + 1, centerCell.getY() - 1));
        result.add(getCell(centerCell.getX() - 1, centerCell.getY() + 1));
        result.add(getCell(centerCell.getX() + 1, centerCell.getY() + 1));
        return result;
    }

    public ArrayList<Cell> getTwoDistanceCells(Cell currentCell) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell[] rowCells : cells) {
            for (Cell cell : rowCells) {
                if (getManhatanDistance(cell, currentCell) == 2)
                    result.add(cell);
            }
        }
        return result;
    }

    public int getManhatanDistance(Cell firstCell, Cell secondCell) {
        return (java.lang.Math.abs((firstCell.getX() - secondCell.getX())) +
                java.lang.Math.abs((firstCell.getY() - secondCell.getY())));
    }

    public boolean isValid(Cell minionCell, Cell targetCell, MinionAttackType attackType) {
        switch (attackType) {
            case MELEE:
                return getAroundCells(minionCell).contains(targetCell);
            case HYBRID:
                return true;
            case RANGED:
                if (getAroundCells(minionCell).contains(targetCell))
                    return false;
                else if (getManhatanDistance(minionCell, targetCell) <= minionCell.getMinionOnIt().getAttackRange()) {
                    return true;
                } else
                    return false;
        }
        return false;
    }

    public ArrayList<Flag> getFlags() {
        return flags;
    }

    public void setFlags(ArrayList<Flag> flags) {
        this.flags = flags;
    }

    public Flag getFlag(){
        return flag;
    }

    public void setFlag(Flag flag){
        this.flag = flag;
    }
}
