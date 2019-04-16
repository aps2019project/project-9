package model;

import model.cards.Card;
import model.cards.Hero;
import model.items.Item;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private Hero hero;
    private Item item;
    private String name;

    public void sort() {           //not complete
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addItem(Item item) {
        this.item = item;
    }

    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void removeItem(Item item) {
        item = null;
    }

    public boolean isValid() {
        return (cards.size() == 20 && hero != null);
    }

    public String toString() {
        int counter = 1;
        String string = new String("Heroes : \n\t\t\t");
        if(hero == null)
            string = string +"\n";
        else{
            string = string + "1 : " + hero.toString() +"\n";
        }
        if(item == null)
            string = string +"\n";
        else{
            string = string + "1 : " + item.toString() +"\n";
        }
        for(Card key : cards){
            string += counter + " : " + key.toString() + "\n";
        }
        return string;
    }

    public Card getCard(String cardID) {      //need card to complete   returns null if not in deck
        for (Card key : cards){
            if(key != null){                  ////&& key.getCardID == cardID) {
                return key;
            }
        }
        return null;
    }
}
