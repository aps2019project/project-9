package model;

import model.cards.Card;
import model.cards.Hero;
import model.items.Item;

import java.util.ArrayList;

public class Deck {
    ArrayList<Card> cards;
    Hero hero;
    Item item;
    String name;

    public void sort() {
    }

    public void addCard(Card card) {
        for (Card key : cards) {
            if (key == null) {
                key = card;
                break;
            }
        }
    }

    public void addItem(Item item) {
        this.item = item;
    }

    public void removeCard(Card card) {
        if (cards.contains(card))
            cards.remove(card);
    }

    public void removeItem(Item item) {        //not compelete
        item = null;
    }
    public boolean isValid(){
        return false;
    }              //I have no idea
    public String toString(){
        return null;
    }
    public Card getCard(String cardID){      //need card to complete
        return  null;
    }
}
