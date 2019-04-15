package model;

import model.cards.Card;
import model.cards.Minion;
import model.cellaffects.CellAffect;
import model.items.Item;

import java.util.ArrayList;

public class Cell {
    private Minion minionOnIt;
    private ArrayList<CellAffect> cellAffects;
    private PlayGround playGround;
    private Item collectableItem;
    public Minion getMinionOnIt(){
        return minionOnIt;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
    public boolean hasCollectableItem(){return false;}
    public void addCard(Card card){}
    public void deleteCard(){}
    public void addCellAffect(CellAffect cellAffect){
        cellAffects.add(cellAffect);
    }
}
