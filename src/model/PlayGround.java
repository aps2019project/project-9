package model;

import javafx.application.Platform;
import model.cards.Card;
import model.enumerations.GameMode;
import model.enumerations.ItemName;
import model.enumerations.MinionAttackType;
import model.items.Collectible;
import model.items.Flag;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class PlayGround {
    private Cell[][] cells = new Cell[5][9];
    private ArrayList<Flag> flags = new ArrayList<>(); // for mode three
    private Flag flag; // for mode two ( one flag )
    private ArrayList<Collectible> collectibles = new ArrayList<>();

    public PlayGround(GameMode mode, int numberOfFlags) {
        if (mode == GameMode.HERO_KILL) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    cells[i][j] = new Cell(i, j, this, null);
                }
            }
        } else if (mode == GameMode.ONE_FLAG) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (i == 2 && j == 4)
                        cells[i][j] = new Cell(i, j, this, new Flag(cells[i][j]));
                    else
                        cells[i][j] = new Cell(i, j, this, null);
                }
            }
            flag = cells[2][4].getFlag();
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    cells[i][j] = new Cell(i, j, this, null);
                }
            }
            for (int z = 0; z < numberOfFlags; z++) {
                placeFlags();
            }
        }
        assignCollectibleItem();
    }

    private void assignCollectibleItem() {
        int[] col = new int[]{3, 4, 5};
        int[] row = new int[]{0, 1, 2, 3, 4};
        for (int i = 0; i < 3; i++) {
            Cell cell = getCell(row[new Random().nextInt(5)], col[i]);
            Collectible collectible = getRandomCollectibleItem();
            collectibles.add(collectible);
            cell.setCollectableItem(collectible);
        }
    }

    public ArrayList<Collectible> getCollectibles() {
        return collectibles;
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
                if (i == randomColoumn && j == randomSatr) {
                    Flag flag = new Flag(cells[i][j]);
                    cells[i][j].setFlag(flag);
                    flags.add(flag);
                }
            }
        }
    }

    protected Collectible getRandomCollectibleItem() {
        Random r = new Random();
        ArrayList<Collectible> collectibles = new ArrayList<>();
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.NOOSH_DAROO));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.TIR_DOSHAKH));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.EKSIR));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.MAJOON_MANA));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.MAJOON_ROIEEN));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.NEFRIN_MARG));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.RANDOM_DAMAGE));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.BLADES_AGILITY));
        collectibles.add((Collectible) DefaultCards.getItem(ItemName.CHINESE_SHAMSHIR));
        int y = r.nextInt(collectibles.size());
        return collectibles.get(y);
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
                if (getManhatanDistance(cell, currentCell) <= 2)
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
                if (minionCell.equals(targetCell))
                    return false;
                return getManhatanDistance(minionCell, targetCell) <= minionCell.getMinionOnIt().getAttackRange();
            case RANGED:
                if (targetCell.equals(minionCell))
                    return false;
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
        for (int i = cell.getX(); i < squareSize + cell.getX(); i++) {
            for (int j = cell.getY(); j < squareSize + cell.getY(); j++) {
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

    public Cell getAnEnemyInEightAround(Player player, Cell cell) {
        ArrayList<Cell> arounds = getAroundCells(cell);
        ArrayList<Cell> allEnemyCells = new ArrayList<>();
        for (Cell around : arounds) {
            if (isForEnemyMinion(around, player)) {
                allEnemyCells.add(around);
            }
        }
        return allEnemyCells.get(new Random().nextInt(allEnemyCells.size()));
    }
}
