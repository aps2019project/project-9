package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.CardType;
import model.enumerations.ItemName;
import model.enumerations.MinionName;
import model.enumerations.SpellName;
import model.items.Item;

import java.util.ArrayList;

public class Shop {
    private static final Shop SHOP = new Shop();

    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    private static int uniqueID = 1;

    public static Shop getInstance(){
        return SHOP;
    }
    private Shop(){
        // initialize shop
        for (MinionName minionName : MinionName.values()) {
            allCards.add(DefaultCards.getMinion(minionName));
        }
        for (SpellName spellName : SpellName.values()) {
            allCards.add(DefaultCards.getSpell(spellName));
        }
        for(ItemName itemName : ItemName.values()){
            allItems.add(DefaultCards.getItem(itemName));
        }
    }

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
        card.setCardID(uniqueID++);
        account.getCollection().addCard(card);
        account.reduceMoney(card.getCost());
    }

    public void buy(Item item, Account account) {
        item.setItemID(uniqueID++);
        account.getCollection().addItem(item);
        account.reduceMoney(item.getCost());
    }

    public void sell(Card currentCard, Account loggedInAccount) {
        currentCard.setCardID(uniqueID++);
        loggedInAccount.getCollection().removeCard(currentCard);
        loggedInAccount.addMoney(currentCard.getCost());
    }

    public void sell(Item currentItem, Account loggedInAccount) {
        currentItem.setItemID(uniqueID++);
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
        return Collection.showArraylistOfCardsAndItems(allCards,allItems);
    }

}