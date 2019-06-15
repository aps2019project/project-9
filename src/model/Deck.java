package model;

import com.google.gson.annotations.Expose;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.*;
import model.items.Item;

import java.util.ArrayList;

public class Deck {
    // 1,9,11,25,13,17,18,21,22,26,38,36,40
    private static final MinionName[] FIRST_LEVEL_MINIONS =
            {MinionName.FARS_KAMANDAR, MinionName.TOORANEE_NEYZEDAR,
                    MinionName.TOORANEE_GORZDAR, MinionName.JADOOGAR, MinionName.BLACK_DEEV,
                    MinionName.ONE_EYE_GHOOL, MinionName.POISON_SNAKE, MinionName.GHOOL_SNAKE,
                    MinionName.WHITE_WOLF, MinionName.JADOOGAR_AZAM, MinionName.SIAVOSH,
                    MinionName.NANE_SARMA, MinionName.ARJANG_DEEV};
    // 1,7,10,11,12,18,20
    private static final SpellName[] FIRST_LEVEL_SPELLS =
            {SpellName.TOTAL_DISARM, SpellName.LIGHTING_BOLT, SpellName.ALL_DISARM,
                    SpellName.ALL_POISON, SpellName.DISPEL, SpellName.SACRIFICE, SpellName.SHOCK};
    // 1
    private static final HeroName FIRST_LEVEL_HERO = HeroName.WHITE_DEEV;
    // 1
    private static final ItemName FIRST_LEVEL_ITEM = ItemName.TAJ_DANAYEE;
    // 2,3,5,8,12,15,19,23,27,30,33,39,25
    private static final MinionName[] SECOND_LEVEL_MINIONS =
            {MinionName.FARS_SHAMSHIRZAN, MinionName.FARS_NEYZEDAR, MinionName.FARS_PAHLAVAN,
                    MinionName.TOORANEE_GHOLABSANG, MinionName.TOORANEE_SHAHZADE, MinionName.EAGLE,
                    MinionName.DRAGON_FIRE, MinionName.PALANG, MinionName.JEN, MinionName.GEEV,
                    MinionName.EERAG, MinionName.SHAH_GOOL, MinionName.JADOOGAR_AZAM};
    // 2,3,5,9,8,13,19
    private static final SpellName[] SECOND_LEVEL_SPELLS =
            {SpellName.AREA_DISPEL, SpellName.EMPOWER, SpellName.GOD_STRENGTH,
                    SpellName.POISON_LAKE, SpellName.MADNESS, SpellName.HEALTH_WITH_PROFIT,
                    SpellName.KINGS_GUARD};
    // 2
    private static final HeroName SECOND_LEVEL_HERO = HeroName.SIMORGH;
    // 18
    private static final ItemName SECOND_LEVEL_ITEM = ItemName.SOUL_EATER;
    // 6,7,10,14,16,18,20,24,25,28,29,31,34
    private static final MinionName[] THIRD_LEVEL_MINIONS =
            {MinionName.FARS_SEPAHSALAR, MinionName.TOORANEE_KAMANDAR,
                    MinionName.TOORANE_JASOS, MinionName.SANGANDAZ_GHOLL,
                    MinionName.GORAZ_DEEV, MinionName.POISON_SNAKE,
                    MinionName.DARANDE_SHIR, MinionName.WOLF, MinionName.JADOOGAR,
                    MinionName.WILD_GORAZ, MinionName.PIRAN, MinionName.BAHMAN,
                    MinionName.BIG_GHOOL};
    // 6,10,12,14,15,16,1
    private static final SpellName[] THIRD_LEVEL_SPELLS =
            {SpellName.HELLFIRE, SpellName.ALL_DISARM, SpellName.DISPEL,
                    SpellName.POWER_UP, SpellName.ALL_POWER, SpellName.ALL_ATTACK,
                    SpellName.TOTAL_DISARM};
    // 3
    private static final HeroName THIRD_LEVEL_HERO = HeroName.EJDEHA;
    // 12
    private static final ItemName THIRD_LEVEL_ITEM = ItemName.TERROR_HOOD;

    @Expose
    private ArrayList<Card> cards = new ArrayList<>();
    private Hero hero;

    private Item item;

    private String name; // used in accounts

    private int uniqueID = 150;

    public Deck(String name) {
        this.name = name;
        //TODO for debug
        if (name.equals("debugging")) {
            hero = DefaultCards.getHero(HeroName.SIMORGH);
            cards.add(DefaultCards.getMinion(MinionName.FARS_KAMANDAR));
            cards.add(DefaultCards.getMinion(MinionName.FARS_KAMANDAR));
            cards.add(DefaultCards.getMinion(MinionName.FARS_KAMANDAR));
            cards.add(DefaultCards.getMinion(MinionName.GHOOL_SNAKE));
            cards.add(DefaultCards.getMinion(MinionName.FARS_ASBSAVAR));
            cards.add(DefaultCards.getMinion(MinionName.WHITE_WOLF));
            cards.add(DefaultCards.getMinion(MinionName.FARS_PAHLAVAN));
            cards.add(DefaultCards.getMinion(MinionName.ASHKBOOS));
            cards.add(DefaultCards.getMinion(MinionName.FARS_KAMANDAR));
            cards.add(DefaultCards.getSpell(SpellName.WEAKENING));
            cards.add(DefaultCards.getSpell(SpellName.GOD_STRENGTH));
            cards.add(DefaultCards.getSpell(SpellName.HELLFIRE));
            cards.add(DefaultCards.getMinion(MinionName.SHAH_GOOL));
            cards.add(DefaultCards.getMinion(MinionName.ARJANG_DEEV));
            cards.add(DefaultCards.getMinion(MinionName.TOORANEE_SHAHZADE));
            cards.add(DefaultCards.getMinion(MinionName.FARS_SEPAHSALAR));
            cards.add(DefaultCards.getSpell(SpellName.TOTAL_DISARM));
        } else if (name.equals("first_level")) {
            for (MinionName minion : FIRST_LEVEL_MINIONS) {
                Minion minion1 = DefaultCards.getMinion(minion);
                minion1.setCardID(uniqueID++);
                cards.add(minion1);
            }
            for (SpellName spell : FIRST_LEVEL_SPELLS) {
                Spell spell1 = DefaultCards.getSpell(spell);
                spell1.setCardID(uniqueID++);
                cards.add(spell1);
            }
            hero = DefaultCards.getHero(FIRST_LEVEL_HERO);
            hero.setCardID(uniqueID++);
            //TODO debugging
            //item = DefaultCards.getItem(FIRST_LEVEL_ITEM);
            //item.setItemID(uniqueID++);
        } else if (name.equals("second_level")) {
            for (MinionName minion : SECOND_LEVEL_MINIONS) {
                Minion minion1 = DefaultCards.getMinion(minion);
                minion1.setCardID(uniqueID++);
                cards.add(minion1);
            }
            for (SpellName spell : SECOND_LEVEL_SPELLS) {
                Spell spell1 = DefaultCards.getSpell(spell);
                spell1.setCardID(uniqueID++);
                cards.add(spell1);
            }
            hero = DefaultCards.getHero(SECOND_LEVEL_HERO);
            hero.setCardID(uniqueID++);
            item = DefaultCards.getItem(SECOND_LEVEL_ITEM);
            item.setItemID(uniqueID++);
        } else if (name.equals("third_level")) {
            for (MinionName minion : THIRD_LEVEL_MINIONS) {
                Minion minion1 = DefaultCards.getMinion(minion);
                minion1.setCardID(uniqueID++);
                cards.add(minion1);
            }
            for (SpellName spell : THIRD_LEVEL_SPELLS) {
                Spell spell1 = DefaultCards.getSpell(spell);
                spell1.setCardID(uniqueID++);
                cards.add(spell1);
            }
            hero = DefaultCards.getHero(THIRD_LEVEL_HERO);
            hero.setCardID(uniqueID++);
            item = DefaultCards.getItem(THIRD_LEVEL_ITEM);
            item.setItemID(uniqueID++);
        }
    }

    public Deck(ArrayList<Card> cards, Hero hero, Item item, String name) {
        this.cards = cards;
        this.hero = hero;
        this.item = item;
        this.name = name;
    }

    public Deck getCopy() {
        ArrayList<Card> secondCards = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Minion) {
                Minion copy = DefaultCards.getMinion(((Minion) card).getMinionName());
                copy.setCardID(card.getCardID());
                secondCards.add(copy);
            } else if (card instanceof Spell) {
                Spell copy = DefaultCards.getSpell(((Spell) card).getSpellName());
                copy.setCardID(card.getCardID());
                secondCards.add(copy);
            }
        }
        Hero secondHero = DefaultCards.getHero(hero.getHeroName());
        secondHero.setCardID(hero.getCardID());
        Item secondItem = null;
        if (item != null) {
            secondItem = DefaultCards.getItem(item.getItemType());
            secondItem.setItemID(item.getItemID());
        }
        return new Deck(secondCards, secondHero, secondItem, name);
    }

    public boolean canAddCard() {
        return cards.size() < 20;
    }

    public void addCard(Card card) {
        if (card instanceof Hero)
            hero = (Hero) card;
        else
            cards.add(card);

    } // not hero

    public void addItem(Item item) {
        this.item = item;
    }

    public boolean hasItem() {
        return item != null;
    }

    public void removeCard(Card card) {
        if (card instanceof Hero && hero.getName().equals(card.getName())) {
            hero = null;
            cards.remove(card);//TODO
        } else
            cards.remove(card);
    }

    public void removeItem(Item item) {
        if (this.item.getName().equals(item.getName()))
            this.item = null;
    }

    public boolean isValid() {
        return (cards.size() == 20 && hero != null);
    }

    public String toString() {
        ArrayList<Card> theCards = new ArrayList<>();
        for (Card card : cards) {
            theCards.add(card);
        }
        theCards.add(hero);
        ArrayList<Item> items = new ArrayList<>();
        items.add(item);
        if (item != null)
            return Collection.showArraylistOfCardsAndItems(theCards, items);
        else
            return Collection.showArraylistOfCardsAndItems(theCards, null);
    }

    public Card getCardByBattleID(String battleID) {
        for (Card card : cards) {
            if (card.getBattleID().equals(battleID))
                return card;
        }
        return null;
    }

    public Card getCardByID(String cardID) { // also hero
        int mainCardId;
        try {
            mainCardId = Integer.parseInt(cardID);
        } catch (NumberFormatException e) {
            return null;
        }
        if (hero != null && hero.getCardID() == mainCardId)
            return hero;
        for (Card key : cards) {
            if (key.getCardID() == mainCardId)
                return key;
        }

        return null;
    }

    public Item getItemByID(String itemID) {
        int mainItemId;
        try {
            mainItemId = Integer.parseInt(itemID);
        } catch (NumberFormatException e) {
            return null;
        }
        if (item != null && item.getItemID() == mainItemId)
            return item;
        else
            return null;
    }

    public String getName() {
        return name;
    }


    public boolean equals(Deck deck) {
        return this.name.equals(deck.getName());
    }

    public boolean hasHero() {
        return hero != null;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Item getItem() {
        return item;
    }

    public Hero getHero() {
        return hero;
    }

    public int getDeckCost() {
        int cost = 0;
        for (Card key :
                cards) {
            if (key != null)
                cost += key.getCost();
        }
        if (item != null)
            cost += item.getCost();
        return cost;
    }

    public int getAvarageMana() {
        int mana = 0;
        for (Card key :
                cards) {
            if (key != null)
                mana += key.getMP();
        }
        if (cards.size() != 0)
            mana /= cards.size();
        else {
            mana = 0;
        }

        mana = Math.round(mana);
        return mana;
    }

    public Card getcardbyName(String cardName) {
        for (Card key :
                cards) {
            if (key != null && key.getName().equals(cardName)) {
                return key;
            }
        }
        return null;
    }
}
