package model;

import model.cards.Card;
import model.enumerations.GameMode;
import model.enumerations.MinionAttackType;
import model.items.Flag;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class PlayGround {
    private Cell[][] cells = new Cell[5][9];
    private ArrayList<Flag> flags; // for mode three
    private Flag flag; // for mode two ( one flag )

    public PlayGround(GameMode mode, int numberOfFlags) {
        if (mode == GameMode.HERO_KILL) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    cells[i][j] = new Cell(i, j, this, null);
                }
            }
        } else if (mode == GameMode.ONE_FLAG) {
            placeFlags();
        } else {
            for (int z = 0; z < numberOfFlags; z++) {
                placeFlags();
            }
        }
    }

    private int randomIntGenarator() {
        Random random = new Random();
        int a = random.nextInt();
        if (a < 0)
            a = -a;
        return a;
    }

    private void placeFlags() {
        int randomColoumn = randomIntGenarator() % 5;
        int randomSatr = randomIntGenarator() % 9;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (i == randomColoumn && j == randomSatr)
                    cells[i][j] = new Cell(i, j, this, new Flag());
                else
                    cells[i][j] = new Cell(i, j, this, null);
            }
        }
    }

    public boolean isForEnemyMinion(Cell cell, Player player) {
        if (!cell.hasCardOnIt())
            return false;
        return player.getOpponent().getMinionsInPlayGround().contains(cell.getMinionOnIt()) ||
                player.getOpponent().getHero().getCell().equals(cell);
    }

    public boolean isForFriendlyMinion(Cell cell, Player player) {
        return (cell.hasCardOnIt() &&
                player.getMinionsInPlayGround().contains(cell.getMinionOnIt()))
                || cell.equals(player.getHero().getCell());
    }

    public Cell getCell(int x, int y) {
        if ((x) >= 0 && (y) >= 0 && (x) <= 4 && (y) <= 8)
            return cells[x][y];
        else
            return null;
    }

    public ArrayList<Cell> getAroundCells(Cell centerCell) {
        ArrayList<Cell> result = new ArrayList<>();
        if (getCell(centerCell.getX() - 1, centerCell.getY()) != null)
            result.add(getCell(centerCell.getX() - 1, centerCell.getY()));
        if (getCell(centerCell.getX() + 1, centerCell.getY()) != null)
            result.add(getCell(centerCell.getX() + 1, centerCell.getY()));
        if (getCell(centerCell.getX(), centerCell.getY() - 1) != null)
            result.add(getCell(centerCell.getX(), centerCell.getY() - 1));
        if (getCell(centerCell.getX(), centerCell.getY() + 1) != null)
            result.add(getCell(centerCell.getX(), centerCell.getY() + 1));
        if (getCell(centerCell.getX() - 1, centerCell.getY() - 1) != null)
            result.add(getCell(centerCell.getX() - 1, centerCell.getY() - 1));
        if (getCell(centerCell.getX() + 1, centerCell.getY() - 1) != null)
            result.add(getCell(centerCell.getX() + 1, centerCell.getY() - 1));
        if (getCell(centerCell.getX() - 1, centerCell.getY() + 1) != null)
            result.add(getCell(centerCell.getX() - 1, centerCell.getY() + 1));
        if (getCell(centerCell.getX() + 1, centerCell.getY() + 1) != null)
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

    public boolean isValid(Cell minionCell, Cell targetCell, MinionAttackType attackType) { // for minion attack
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

    public Flag getFlag() {
        return flag;
    }

    public boolean canMoveThroughPath(Cell firstCell, Cell secondCell) {
    /*two cell with manhatan distance 2 ,
     if there are no power in the middle
     of their path , return true;*/
        if (getManhatanDistance(firstCell, secondCell) > 2)
            return false;
        if (java.lang.Math.abs(firstCell.getY() - secondCell.getY()) == 2
                || java.lang.Math.abs(firstCell.getX() - secondCell.getX()) == 2) {
            return !getCell((firstCell.getX() + secondCell.getX()) / 2,
                    (firstCell.getY() + secondCell.getY()) / 2).hasCardOnIt();
        } else
            return true;
    }

    public ArrayList<Cell> enemyInColumn(Cell cell, Player player) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell[] cell1 : cells) {
            for (Cell cell2 : cell1) {
                if (cell2.getY() == cell.getY()) {
                    if (cell2.hasCardOnIt() &&
                            !isForFriendlyMinion(cell2.getMinionOnIt().getCell(), player))
                        result.add(cell2);
                }
            }
        }
        return result;
    }

    public ArrayList<Cell> enemyInRow(Cell cell, Player player) {
        ArrayList<Cell> result = new ArrayList<>();
        for (Cell[] cell1 : cells) {
            for (Cell cell2 : cell1) {
                if (cell2.getX() == cell.getX())
                    if (cell2.hasCardOnIt() &&
                            !isForFriendlyMinion(cell2.getMinionOnIt().getCell(), player))
                        result.add(cell2);
            }
        }
        return result;
    }

    public ArrayList<Cell> getSquareCells(Cell cell, int squareSize) {
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = cell.getX(); i < squareSize; i++) {
            for (int j = cell.getY(); j < squareSize; j++) {
                if (i < 5 && j < 9) {
                    result.add(getCell(i, j));
                }
            }
        }
        return result;
    }

    public Cell getRandomCell() {
        Random first = new Random();
        Random second = new Random();
        int row = first.nextInt(5);
        int col = second.nextInt(9);
        return getCell(row, col);
    }

    public Cell getRandomPowerCell(Player player) {
        Random r = new Random();
        return player.getMinionsInPlayGround().get(r.nextInt(player.getMinionsInPlayGround().size())).getCell();
    }
}
