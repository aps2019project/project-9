package model.cards;


import com.google.gson.annotations.Expose;
import model.Player;
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
    protected String battleID;
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
        return battleID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public void setBattleID(Player player) {
        int id = 1;
        for (Minion minion : player.getMinionsInPlayGround()) {
            try {
                if (minion.getMinionName().equals(((Minion) this).getMinionName()))
                    id++;
            }catch (NullPointerException e){

            }
        }
        battleID = player.getName() + "_"
                + this.getName()
                + "_" + id;
    }

    public void setBattleID(String id) {
        this.battleID = id;
    }

    public int getMP() {
        return MP;
    }
}
