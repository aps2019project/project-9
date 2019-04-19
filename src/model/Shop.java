package model;

import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;

public class Shop {
    private static final Shop SHOP = new Shop();

    private ArrayList<Card> allCards;
    private ArrayList<Item> allItems;

    public static Shop getInstance(){
        return SHOP;
    }
    private Shop(){}

    public Card searchCardByName(String cardName) {
        for (Card card : allCards) {
            if(card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public Item searchItemByName(String itemName) {
        for (Item item : allItems) {
            if(item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void buy(Card card, Account account) {          //need card to be complete
        account.getCollection().addCard(card);
        account.reduceMoney(card.getCost());
    }

    public void buy(Item item, Account account) {           //need item to be complete
        account.getCollection().addItem(item);
        account.reduceMoney(item.getCost());
    }

    public void sell(Card currentCard, Account loggedInAccount) {      //need card to be complete
        loggedInAccount.getCollection().removeCard(currentCard);
        loggedInAccount.addMoney(currentCard.getCost());
    }

    public void sell(Item currentItem, Account loggedInAccount) {            //need item to be complete
        loggedInAccount.getCollection().removeItem(currentItem);
        loggedInAccount.addMoney(currentItem.getCost());
    }

    public ArrayList<Card> getCards() {
        return allCards;
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }

}