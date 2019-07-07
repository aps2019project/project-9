package model;

import model.cards.Card;

import java.util.ArrayList;

public class GraveYard {

    private ArrayList<Card> cards = new ArrayList<>();

    public GraveYard(Player player){

    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(int cardID) {
        for (Card card : cards) {
            if (card.getCardID() == cardID)
                return card;
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public Card getCardByID(String battleID){
        for (Card card : cards) {
            if(card.getBattleID().equals(battleID))
                return card;
        }
        return null;
    }
}
