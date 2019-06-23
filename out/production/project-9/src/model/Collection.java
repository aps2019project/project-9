package model;

import com.google.gson.annotations.Expose;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.enumerations.HeroName;
import model.enumerations.ItemName;
import model.enumerations.MinionName;
import model.enumerations.SpellName;
import model.items.Item;

import java.util.ArrayList;

public class Collection {
    @Expose
    private ArrayList<Card> cards;
    @Expose
    private ArrayList<Item> items;

    static int uniqueID = 1;

    public Collection() {
        cards = new ArrayList<>();
        items = new ArrayList<>();
        initializeCollections();
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public static String showArraylistOfCardsAndItems(ArrayList<Card> cards, ArrayList<Item> items) {
        // used in shop and collection toString()
        String string = "";
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nHeroes :\n\n");
        int counter = 1;
        if (cards != null && cards.size() > 0) {
            for (Card card : cards) {
                if (card instanceof Hero) {
                    if (card != null) {
                        string = counter++ + "\t\t" + card.toString() + "\tHero ID : " + card.getCardID() + "\n";
                        stringBuilder.append(string);
                    }
                }
            }
        }
        counter = 1;
        stringBuilder.append("\nItems :\n\n");
        if (items != null) {
            for (Item item : items) {
                if (item != null) {
                    string = counter++ + "\t\t" + item.toString() + "\tItem ID : " + item.getItemID() + "\n";
                    stringBuilder.append(string);
                }
            }
        }
        counter = 1;
        stringBuilder.append("\nCards :\n\n");
        if (cards != null && cards.size() > 0) {
            for (Card card : cards) {
                if (!(card instanceof Hero)) {
                    if (card != null) {
                        string = counter++ + "\t\t" + card.toString() + "\tCard ID : " + card.getCardID() + "\n";
                        stringBuilder.append(string);
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public Item getItem(int itemID) {
        for (Item key : items) {
            if (key != null && key.getItemID() == (itemID)) {
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

    public Card searchCardByID(int cardID) {
        for (Card card : cards) {
            if (card.getCardID() == cardID)
                return card;
        }
        return null;
    }

    public Item searchItemByID(int itemID) {
        for (Item item : items) {
            if (item.getItemID() == itemID)
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
        int id = uniqueID;
        while (true) {
            if (searchCardByID(id) != null)
                id++;
            else
                break;
        }
        uniqueID = id + 1;
        card.setCardID(id);
        cards.add(card);
    }

    public void addItem(Item item) {
        int id = uniqueID;
        while (true) {
            if (searchItemByID(id) != null)
                id++;
            else
                break;
        }
        uniqueID = id + 1;
        item.setItemID(id);
        items.add(item);
    }


    public void removeCard(Card card) {
        cards.remove(card);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public String toString() {
        return showArraylistOfCardsAndItems(cards, items);
    }


    private void initializeCollections() {
        // initializing by level one in index doc
        // minions : 1,9,11,13,17,,18,21,22,26,38,36,40
        // spells : 1,7,10,11,12,18,20
        // hero : 1
        // item : 1
        // --------------Minions---------------
        addCard(DefaultCards.getMinion(MinionName.FARS_SHAMSHIRZAN));
        addCard(DefaultCards.getMinion(MinionName.GEEV));
        addCard(DefaultCards.getMinion(MinionName.TOORANEE_GORZDAR));
        addCard(DefaultCards.getMinion(MinionName.JEN));
        addCard(DefaultCards.getMinion(MinionName.ONE_EYE_GHOOL));
        addCard(DefaultCards.getMinion(MinionName.SIAVOSH));
        addCard(DefaultCards.getMinion(MinionName.GHOOL_SNAKE));
        addCard(DefaultCards.getMinion(MinionName.WHITE_WOLF));
        addCard(DefaultCards.getMinion(MinionName.JADOOGAR_AZAM));
        addCard(DefaultCards.getMinion(MinionName.SIAVOSH));
        addCard(DefaultCards.getMinion(MinionName.NANE_SARMA));
        addCard(DefaultCards.getMinion(MinionName.WOLF));
        addCard(DefaultCards.getMinion(MinionName.JADOOGAR));
        //------------Spells-------------------
        addCard(DefaultCards.getSpell(SpellName.TOTAL_DISARM));
        addCard(DefaultCards.getSpell(SpellName.LIGHTING_BOLT));
        addCard(DefaultCards.getSpell(SpellName.ALL_DISARM));
        addCard(DefaultCards.getSpell(SpellName.AREA_DISPEL));
        addCard(DefaultCards.getSpell(SpellName.ALL_POISON));
        addCard(DefaultCards.getSpell(SpellName.HELLFIRE));
        addCard(DefaultCards.getSpell(SpellName.SHOCK));
        //--------------Hero---------------------
        addCard(DefaultCards.getHero(HeroName.WHITE_DEEV));
        //------------------item----------------
        addItem(DefaultCards.getItem(ItemName.POISONOUS_DAGGER));
    }

    public int getNumberOfItems() {
        return items.size();
    }
}
