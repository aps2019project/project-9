package model;

import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;

public class Shop {
    private ArrayList<Card> allCards;
    private ArrayList<Item> allItems;

    public void search(Card card) {
    }

    public void search(Item item) {
    }

    public void buy(Card card, Account account) {
    }

    public void buy(Item item, Account account) {
    }

    public void sell(Card card, Account account) {
    }

    public void sell(Item item, Account account) {
    }

    public ArrayList<Card> getCards() {
        return allCards;
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }
}