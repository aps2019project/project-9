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
}
