package server;

import data.JsonProcess;
import model.Collection;
import model.DefaultCards;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.*;
import model.items.Collectible;
import model.items.Item;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private static final Shop SHOP = new Shop();
    private static final int FIRST_CAPACITY = 3; // for cards and items
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Item> allItems = new ArrayList<>();
    HashMap<Card, Integer> cardNumbers = new HashMap<>();
    HashMap<Item, Integer> itemNumbers = new HashMap<>();
    private static int uniqueID = 1; // for shop

    public static Shop getInstance() {
        return SHOP;
    }

    private Shop() {
        // initialize shop
        for (MinionName minionName : MinionName.values()) {
            if (minionName != MinionName.CUSTOM) {
                Minion minion = DefaultCards.getMinion(minionName);
                setCardID(minion);
                allCards.add(minion);
                cardNumbers.put(minion, FIRST_CAPACITY);
            }
        }
        for (SpellName spellName : SpellName.values()) {
            if (spellName != SpellName.CUSTOM) {
                Spell spell = DefaultCards.getSpell(spellName);
                setCardID(spell);
                allCards.add(spell);
                cardNumbers.put(spell, FIRST_CAPACITY);
            }
        }
        for (ItemName itemName : ItemName.values()) {
            Item item = DefaultCards.getItem(itemName);
            if (!(item instanceof Collectible)) {
                if (item != null) {
                    item.setItemID(uniqueID++);
                    allItems.add(item);
                    itemNumbers.put(item, FIRST_CAPACITY);
                }
            }
        }
        for (HeroName value : HeroName.values()) {
            if (value != HeroName.CUSTOM) {
                Hero hero = DefaultCards.getHero(value);
                allCards.add(hero);
                cardNumbers.put(hero, FIRST_CAPACITY);
            }
        }

        ArrayList<Card> savedCards = JsonProcess.getSavedCustomCards();
        for (Card savedCard : savedCards) {
            allCards.add(savedCard);
            cardNumbers.put(savedCard, FIRST_CAPACITY);
        }
    }

    public void addCard(Card card) {
        if (searchCardByName(card.getName()) != null) {
            Card card1 = searchCardByName(card.getName());
            cardNumbers.put(card1, cardNumbers.get(card1) + 1);
        } else {
            setCardID(card);
            allCards.add(card);
        }
    }


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
        Card card1 = searchCardByName(card.getName());
        if (cardNumbers.get(card1) != 0)
            cardNumbers.put(card1, cardNumbers.get(card1) - 1);
        else
            return;
        if (card.isCustom()) {
            account.getCollection().addCard(card.getCustomCopy());
        } else {
            account.getCollection().addCard(card.getNormalCopy());
        }
        account.reduceMoney(card.getCost());
    }

    public void buy(Item item, Account account) {
        Item item1 = searchItemByName(item.getName());
        if (itemNumbers.get(item1) != 0)
            itemNumbers.put(item1, itemNumbers.get(item1) - 1);
        else
            return;
        account.getCollection().addItem(item);
        account.reduceMoney(item.getCost());
    }

    public void sell(Card currentCard, Account loggedInAccount) {
        setCardID(currentCard);
        if (searchCardByName(currentCard.getName()) != null) {
            Card card = searchCardByName(currentCard.getName());
            cardNumbers.put(card, cardNumbers.get(card) + 1);
        } else {
            cardNumbers.put(currentCard, 1);
        }
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

    private void setCardID(Card card) {
        int id = uniqueID + 1;
        while (searchCardByID(id) != null) {
            id++;
        }
        card.setCardID(id);
        uniqueID = id + 1;
    }

    private Card searchCardByID(int id) {
        for (Card card : allCards) {
            if (card.getCardID() == id)
                return card;
        }
        return null;
    }
}