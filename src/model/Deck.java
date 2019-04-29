package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.CardType;
import model.items.Item;

import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();
    private Hero hero;
    private Item item;
    private String name; // used in accounts

    public Deck(String name) {
        this.name = name;
        if (name.equals("first_level")) {

        } else if (name.equals("second_level")) {

        } else if (name.equals("third_level")) {

        }
    }

    public Deck(ArrayList<Card> cards, Hero hero, Item item, String name) {
        this.cards = cards;
        this.hero = hero;
        this.item = item;
        this.name = name;
    }

    public Deck getCopy() {
        ArrayList<Card> secondCards = new ArrayList<>();
        for (Card card : cards) {
            if(card instanceof Minion) {
                Minion copy = DefaultCards.getMinion(((Minion)card).getMinionName());
                secondCards.add(copy);
            }else if(card instanceof Spell){
                Spell copy = DefaultCards.getSpell(((Spell)card).getSpellName());
                secondCards.add(copy);
            }
        }
        Hero secondHero = DefaultCards.getHero(hero.getHeroName());
        Item secondItem = DefaultCards.getItem(item.getItemType());
        return new Deck(secondCards,secondHero,secondItem,name);
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
        if (this.item.getName().equals(item.getName()))
            this.item = null;
    }

    public boolean isValid() {
        return (cards.size() == 20 && hero != null);
    }

    public String toString(boolean forCollection) {
        int counter = 1;
        String string = "";
        if (forCollection)
            string += "\t\t";
        string += "Heroes : \n";
        if (hero != null) {
            if (forCollection)
                string += "\t\t";
            string += "\t\t\t1 : " + hero.toString() + "\n";
        }
        if (forCollection)
            string += "\t\t";
        string += "Items : ";
        if (item != null) {
            if (forCollection)
                string += "\t\t";
            string += "\t\t\t1 : " + item.toString() + "\n";
        }
        string += "Cards : ";
        for (Card key : cards) {
            if (forCollection)
                string += "\t\t";
            if (key.getCardType() == CardType.MINION) {
                Minion minion = (Minion) key;
                string += "\t\t\t" + counter + " : " + minion.toString() + "\n";
            } else {
                Spell spell = (Spell) key;
                string += "\t\t\t" + counter + " : " + spell.toString() + "\n";
            }
            counter++;
        }
        return string;
    }

    public Card getCardByID(String cardID) { // also hero
        int mainCardId;
        try {
            mainCardId = Integer.parseInt(cardID);
        } catch (NumberFormatException e) {
            return null;
        }
        for (Card key : cards) {
            if (key.getCardID() == mainCardId)
                return key;
        }
        if (hero.getCardID() == mainCardId)
            return hero;
        return null;
    }

    public Item getItemByID(String itemID) {
        int mainItemId;
        try {
            mainItemId = Integer.parseInt(itemID);
        } catch (NumberFormatException e) {
            return null;
        }
        if (item.getItemID() == mainItemId)
            return item;
        else
            return null;
    }

    public String getName() {
        return name;
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

    public Item getItem() {
        return item;
    }

    public Hero getHero() {
        return hero;
    }
}
