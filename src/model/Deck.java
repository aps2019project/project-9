package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.CardType;
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
        String string = "Heroes : \n";
        if (hero != null)
            string += "\t\t\t1 : " + hero.toString() + "\n";

        string += "Items : ";
        if (item != null)
            string += "\t\t\t1 : " + item.toString() + "\n";

        string += "Cards : ";
        for (Card key : cards) {
            if(key.getCardType() == CardType.MINION) {
                Minion minion = (Minion)key;
                string += "\t\t\t" + counter + " : " + minion.toString() + "\n";
            }
            else{
                Spell spell = (Spell) key;
                string += "\t\t\t" + counter + " : " + spell.toString() + "\n";
            }
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

    public ArrayList<Card> getCards() {
        return cards;
    }
}
