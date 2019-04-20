package model;

import java.util.ArrayList;

public class PlayGround {
    private Cell[][] cells = new Cell[5][9];

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
        result.add(getCell(centerCell.getX()-1,centerCell.getY()));
        result.add(getCell(centerCell.getX(),centerCell.getY()-1));
        result.add(getCell(centerCell.getX()-1,centerCell.getY()-1));
        result.add(getCell())
    }

    public ArrayList<Cell> getTwoDistanceCells(Cell currentCell) {
        return null;
    }

    public int getManhatanDistance(Cell firstCell, Cell secondCell) {
        return (java.lang.Math.abs((firstCell.getX() - secondCell.getX())) +
                java.lang.Math.abs((firstCell.getY() - secondCell.getY())));
    }

    public boolean isValidForMelee(Cell minionCell, Cell targetCell) {
        return getAroundCells(minionCell).contains(targetCell);
    }
}
