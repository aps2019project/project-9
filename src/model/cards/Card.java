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

    public Card(int cost, int MP, CardType cardType,
                int cardID, String name, String desc) {
        this.cost = cost;
        this.MP = MP;
        this.cardType = cardType;
        this.cardID = cardID;
        this.name = name;
        this.desc = desc;
    }

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
