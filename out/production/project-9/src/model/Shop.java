package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.*;
import model.items.Collectible;
import model.items.Item;

import java.util.ArrayList;
import java.util.Date;

public class Shop {
    private static final Shop SHOP = new Shop();

    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    private static int uniqueID = 1; // for shop

    public static Shop getInstance() {
        return SHOP;
    }

    private Shop() {
        // initialize shop
        for (MinionName minionName : MinionName.values()) {
            Minion minion = DefaultCards.getMinion(minionName);
            minion.setCardID(uniqueID++);
            allCards.add(minion);
        }
        for (SpellName spellName : SpellName.values()) {
            Spell spell = DefaultCards.getSpell(spellName);
            spell.setCardID(uniqueID++);
            allCards.add(spell);
        }
        for (ItemName itemName : ItemName.values()) {
            Item item = DefaultCards.getItem(itemName);
            if (!(item instanceof Collectible)) {
                if (item != null) {
                    item.setItemID(uniqueID++);
                    allItems.add(item);
                }
            }
        }
        for (HeroName value : HeroName.values()) {
            allCards.add(DefaultCards.getHero(value));
        }
    }

    public void addCard(Card card){
        card.setCardID(uniqueID++);
        allCards.add(card);
    }

    // total_disarm , all_disam , all_poison , ghool_snake
    public Card searchCardByName(String cardName) {
        for (Card card : allCards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }

    public Item searchItemByName(String itemName) {
        for (Item item : allItems) {
            if (item.getName().equals(itemName))
                return item;
        }
        return null;
    }

    public void buy(Card card, Account account) {
        account.getCollection().addCard(card);
        account.reduceMoney(card.getCost());
    }

    public void buy(Item item, Account account) {
        account.getCollection().addItem(item);
        account.reduceMoney(item.getCost());
    }

    public void sell(Card currentCard, Account loggedInAccount) {
        currentCard.setCardID(uniqueID++);
        loggedInAccount.getCollection().removeCard(currentCard);
        loggedInAccount.addMoney(currentCard.getCost());
        loggedInAccount.deleteCardFromDecks(currentCard);
    }

    public void sell(Item currentItem, Account loggedInAccount) {
        currentItem.setItemID(uniqueID++);
        loggedInAccount.getCollection().removeItem(currentItem);
        loggedInAccount.addMoney(currentItem.getCost());
        loggedInAccount.deleteCardFromDecks(currentItem);
    }

    public ArrayList<Card> getCards() {
        return allCards;
    }

    public ArrayList<Item> getItems() {
        return allItems;
    }

    public String toString() {
        return Collection.showArraylistOfCardsAndItems(allCards, allItems);
    }
}