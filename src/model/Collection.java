package model;

import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private Account ownerAccount;

    public Card getCard(String cardID) {
        for (Card key : cards) {
            if (key != null) {     //&& key.getcardID
                return key;
            }
        }
        return null;
    }   //returns null if not in collection

    public Item getItem(String itemID) {
        for (Item key : items) {
            if (key != null) {     //&& key.getItemID
                return key;
            }
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void addItem(Item item) {
        items.add(item);
    }


    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

}
