package model;

import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;

public class Cell {
    private Card cardOnIt;
    private ArrayList<CellAffect> cellAffects;
    private PlayGround playGround;
    private Item collectableItem;
    public Card getCardOnIt(){
        return cardOnIt;
    }

    public PlayGround getPlayGround() {
        return playGround;
    }
    public boolean hasCollectableItem(){return false;}
    public void addCard(Card card){}
    public void deleteCard(){}
}
