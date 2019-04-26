package model;

import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private Account ownerAccount;

    public Card getCard(String cardID) {
        int mainCardId;
        try {
            mainCardId = Integer.parseInt(cardID);
        } catch (NumberFormatException e) {
            return null;
        }
        for (Card key : cards) {
            if (key != null && key.getCardID() == (mainCardId)) {
                return key;
            }
        }
        return null;
    }   //returns null if not in collection

    public Item getItem(String itemID) {
        int mainItemId;
        try {
            mainItemId = Integer.parseInt(itemID);
        } catch (NumberFormatException e) {
            return null;
        }
        for (Item key : items) {
            if (key != null && key.getItemID() == (mainItemId)) {
                return key;
            }
        }
        return null;
    }

    public ArrayList<Card> searchCardByName(String cardName) {
        ArrayList<Card> result = new ArrayList<>();
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                result.add(card);
        }
        if (result.size() == 0)
            return null;
        return result;
    }

    public Card searchCardByID(String cardID) {
        int mainCardId;
        try {
            mainCardId = Integer.parseInt(cardID);
        } catch (NumberFormatException e) {
            return null;
        }
        for (Card card : cards) {
            if (card.getCardID() == mainCardId)
                return card;
        }
        return null;
    }

    public Item searchItemByID(String itemID) {
        int mainItemId;
        try {
            mainItemId = Integer.parseInt(itemID);
        } catch (NumberFormatException e) {
            return null;
        }
        for (Item item : items) {
            if (item.getItemID() == mainItemId)
                return item;
        }
        return null;
    }

    public ArrayList<Item> searchItemByName(String itemName) {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(itemName))
                result.add(item);
        }
        if (result.size() == 0)
            return null;
        else
            return result;
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

    public String toString() {
        String string = "";
        int counter = 1;
        for (Deck deck : ownerAccount.getDecks()) {
            string += counter + " : " + deck.getName() + " :\n" + deck.toString(true);
            counter++;
        }
        return string;
    }

}
