package model.cards;

import model.enumerations.CardType;

public class Card {
    protected int cost;
    protected int MP;
    protected CardType cardType;
    protected int cardID;
    protected String BattleID;
    protected String name;
    protected String desc;

    public static void createID(Card card) {
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public int getCardID() {
        return cardID;
    }

    public int getCost() {
        return cost;
    }

    public String getDesc() {
        return desc;
    }

    public String getBattleID() {
        return BattleID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setBattleID(String battleID) {
        BattleID = battleID;
    }
}
