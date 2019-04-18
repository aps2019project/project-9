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

    public void buy(Card card, Account account) {          //need card to be complete
        account.addMoney(card.getPrice);
        account.getCollection().addCard(card);
    }

    public void buy(Item item, Account account) {           //need item to be complete
        account.addMoney(item.getPrice);
        account.getCollection().addItem(item);
    }

    public void sell(Card card, Account account) {      //need card to be complete
        account.reduceMoney(card.getPrice);
        account.getCollection().removeCard(card);
    }

    public void sell(Item item, Account account) {            //need item to be complete
        account.reduceMoney(item.getPrice);
        account.getCollection().removeItem(item);
    }

    public ArrayList<Card> getCards() {
        return allCards;
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }
}