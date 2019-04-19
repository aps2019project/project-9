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

    public Card searchCardByName(String cardName){
        for (Card card : cards) {
            if(card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public Card searchCardByID(String cardID){
        for (Card card : cards) {
            if(card.getCardID().equals(cardID))
                return card;
        }
        return null;
    }
    public Item searchItemByID(String itemID){
        for (Item item : items) {
            if(item.getItemID().equals(itemID))
                return item;
        }
        return null;
    }
    public Item searchItemByName(String itemName){
        for (Item item : items) {
            if(item.getName().equals(itemName))
                return item;
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

    public String toString(){
        return null;
    }

}
