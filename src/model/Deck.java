package model;

import model.cards.Card;
import model.cards.Hero;
import model.items.Item;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private Hero hero;
    private Item item;
    private String name; // used in accounts

    public void sort() {           //not complete
    }

    public boolean canAddCard() {
        return cards.size() < 20;
    }

    public void addCard(Card card) {
        cards.add(card);
    } // not hero

    public void addItem(Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return item != null;
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
        if (hero == null)
            string = string + "\n";
        else {
            string = string + "1 : " + hero.toString() + "\n";
        }
        if (item == null)
            string = string + "\n";
        else {
            string = string + "1 : " + item.toString() + "\n";
        }
        for (Card key : cards) {
            string += counter + " : " + key.toString() + "\n";
        }
        return string;
    }

    public Card getCardByID(String cardID) { // also hero
        for (Card key : cards) {
            if (key.getCardID().equals(cardID))
                return key;
        }
        if (hero.getCardID().equals(cardID))
            return hero;
        return null;
    }

    public Item getItemByID(String itemID) {
        if (item.getItemID().equals(itemID))
            return item;
        else
            return null;
    }

    public String getName() {
        return name;
    }

    Deck(String name) {
        this.name = name;
    }

    public boolean equals(Deck deck) {
        return this.name.equals(deck.getName());
    }

    public boolean hasHero() {
        return hero != null;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
