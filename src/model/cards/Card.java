package model.cards;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import model.enumerations.CardType;

public class Card {

    @Expose
    protected int cost;
    @Expose
    protected int MP;
    @Expose
    protected CardType cardType;
    @Expose
    protected int cardID;
    protected String BattleID;
    @Expose
    protected String name;
    @Expose
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

    public int getMP() {
        return MP;
    }
}
