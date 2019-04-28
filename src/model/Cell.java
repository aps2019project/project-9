package model;

import model.cards.Card;
import model.cards.Minion;
import model.cellaffects.CellAffect;
import model.items.Flag;
import model.items.Item;

import java.util.ArrayList;

public class Cell {
    public int getX() {
        return x;
    }

    private int x;

    public int getY() {
        return y;
    }

    private int y;
    private Minion minionOnIt;
    private ArrayList<CellAffect> cellAffects = new ArrayList<>();

    private PlayGround playGround;
    private Item collectableItem;

    private Flag flag = null; // if it had flag this wouldn't be null

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
        minionOnIt = (Minion)card;
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
    public boolean hasCellAffect(){
        return cellAffects.size() > 0;
    }

    public Flag getFlag() {
        return flag;
    }
    public void setFlag(Flag flag){
        this.flag = flag;
    }

    public Item getCollectableItem() {
        return collectableItem;
    }
    public void deleteFlag(){
        flag = null;
    }

    public void removeCellAffect(CellAffect affect){
        cellAffects.remove(affect);
    }
}
