package model;

import model.cards.Card;
import model.cards.Minion;
import model.cellaffects.CellAffect;
import model.items.Flag;
import model.items.Item;

import java.util.ArrayList;

public class Cell {
    public Cell(int x, int y,
                PlayGround playGround, Flag flag) {
        this.x = x;
        this.y = y;
        this.minionOnIt = null;
        this.cellAffects = new ArrayList<>();
        this.playGround = playGround;
        this.collectableItem = null;
        this.flag = flag;
        if (flag != null)
            flag.setCurrentCell(this);
    }

    public int getX() {
        return x;
    }

    private int x;

    public int getY() {
        return y;
    }

    private int y;
    private transient Minion minionOnIt;
    private transient ArrayList<CellAffect> cellAffects = new ArrayList<>();

    private transient PlayGround playGround;
    private transient Item collectableItem;

    private transient Flag flag = null; // if it had flag this wouldn't be null

    public Minion getMinionOnIt() {
        return minionOnIt;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }

    public boolean hasCollectableItem() {
        return collectableItem != null;
    }

    public void addCard(Card card) {
        minionOnIt = (Minion) card;
    }

    public void deleteCard() {
        minionOnIt = null;
    }

    public void addCellAffect(CellAffect cellAffect) {
        cellAffects.add(cellAffect);
    }

    public boolean hasCardOnIt() {
        if (minionOnIt != null)
            return true;
        return false;
    }

    public boolean equals(Cell cell) {
        return (this.x == cell.x) && (this.y == cell.y);
    }

    public ArrayList<CellAffect> getCellAffects() {
        return cellAffects;
    }

    public boolean hasCellAffect() {
        return cellAffects.size() > 0;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public Item getCollectableItem() {
        return collectableItem;
    }

    public void deleteFlag() {
        flag = null;
    }

    public void removeCellAffect(CellAffect affect) {
        cellAffects.remove(affect);
    }

    public void setMinionOnIt(Minion minionOnIt) {
        this.minionOnIt = minionOnIt;
    }

    public void setCollectableItem(Item collectableItem) {
        this.collectableItem = collectableItem;
    }
}
