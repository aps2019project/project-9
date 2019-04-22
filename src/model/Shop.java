package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.CardType;
import model.items.Item;

import java.util.ArrayList;

public class Shop {
    private static final Shop SHOP = new Shop();

    private ArrayList<Card> allCards;
    private ArrayList<Item> allItems;
    private int uniqeID = 0;

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

    public void buy(Card card, Account account) {
        card.setCardID(uniqeID++);
        account.getCollection().addCard(card);
        account.reduceMoney(card.getCost());
    }

    public void buy(Item item, Account account) {
        item.setItemID(uniqeID++);
        account.getCollection().addItem(item);
        account.reduceMoney(item.getCost());
    }

    public void sell(Card currentCard, Account loggedInAccount) {
        currentCard.setCardID(uniqeID++);
        loggedInAccount.getCollection().removeCard(currentCard);
        loggedInAccount.addMoney(currentCard.getCost());
    }

    public void sell(Item currentItem, Account loggedInAccount) {
        currentItem.setItemID(uniqeID++);
        loggedInAccount.getCollection().removeItem(currentItem);
        loggedInAccount.addMoney(currentItem.getCost());
    }

    public ArrayList<Card> getCards() {
        return allCards;
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }

    public String toString(){
        String string = "Heroes : \n";
        int counter = 1;
        for(Card card : allCards){
            if(card.getCardType() == CardType.MINION){
                Minion minion = (Minion) card;
                if(minion.getIsHero()){
                    Hero hero = (Hero) minion;
                    string += "\t\t\t" + counter + " : " + hero.toString() + " - Buy Cost : " + hero.getCost() ;
                    counter++;
                }
            }
        }
        for(Item item : allItems){
            string += item.toString();
            string += "\n";
        }
        for(Card card : allCards){
            if(card.getCardType() == CardType.MINION){
                Minion minion = (Minion) card;
                string += minion.toString() + " - Sell Cost : " + minion.getCost();
            }
            else {
                Spell spell = (Spell) card;
                string += spell.toString() + " - Sell Cost : " + spell.getCost();
            }
            string += "\n";
        }
        return string;
    }

}