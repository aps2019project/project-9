package model.cards;

import model.enumerations.CardType;

public class Card {
    protected int cost;
    protected int MP;
    protected CardType cardType;
    protected String cardID;
    protected String name;
    protected String desc;
    public static void createID(Card card){}
    public CardType getCardType(){return cardType;  }
    public String getName(){return name; }

    public String getCardID() {
        return cardID;
    }

    public int getCost() {
        return cost;
    }
}
