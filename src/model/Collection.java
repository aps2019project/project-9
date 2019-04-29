package model;

import model.cards.Card;
import model.cards.Minion;
import model.enumerations.HeroName;
import model.enumerations.ItemName;
import model.enumerations.MinionName;
import model.enumerations.SpellName;
import model.items.Item;

import java.util.ArrayList;

public class Collection {
    private ArrayList<Card> cards;
    private ArrayList<Item> items;
    private Account ownerAccount;

    public Collection(){
        cards = new ArrayList<>();
        items = new ArrayList<>();
        initializeCollections();
    }



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

    public void setOwnerAccount(Account ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    private void initializeCollections(){
        // initializing by level one in index doc
        // minions : 1,9,11,13,17,,18,21,22,26,38,36,40
        // spells : 1,7,10,11,12,18,20
        // hero : 1
        // item : 1
        // --------------Minions---------------
        Minion minion = DefaultCards.getMinion(MinionName.FARS_KAMANDAR);
        /*cards.add(DefaultCards.getMinion(MinionName.FARS_KAMANDAR));
        cards.add(DefaultCards.getMinion(MinionName.TOORANEE_NEYZEDAR));
        cards.add(DefaultCards.getMinion(MinionName.TOORANEE_GORZDAR));
        cards.add(DefaultCards.getMinion(MinionName.BLACK_DEEV));
        cards.add(DefaultCards.getMinion(MinionName.ONE_EYE_GHOOL));
        cards.add(DefaultCards.getMinion(MinionName.POISON_SNAKE));
        cards.add(DefaultCards.getMinion(MinionName.GHOOL_SNAKE));
        cards.add(DefaultCards.getMinion(MinionName.WHITE_WOLF));
        cards.add(DefaultCards.getMinion(MinionName.JADOOGAR_AZAM));
        cards.add(DefaultCards.getMinion(MinionName.SIAVOSH));
        cards.add(DefaultCards.getMinion(MinionName.NANE_SARMA));
        cards.add(DefaultCards.getMinion(MinionName.ARJANG_DEEV));
        //------------Spells-------------------
        cards.add(DefaultCards.getSpell(SpellName.TOTAL_DISARM));
        cards.add(DefaultCards.getSpell(SpellName.LIGHTING_BOLT));
        cards.add(DefaultCards.getSpell(SpellName.ALL_DISARM));
        cards.add(DefaultCards.getSpell(SpellName.ALL_POISON));
        cards.add(DefaultCards.getSpell(SpellName.SACRIFICE));
        cards.add(DefaultCards.getSpell(SpellName.SHOCK));
        //--------------Hero---------------------
        cards.add(DefaultCards.getHero(HeroName.WHITE_DEEV));
        //------------------item----------------
        items.add(DefaultCards.getItem(ItemName.TAJ_DANAYEE));*/
    }
}
