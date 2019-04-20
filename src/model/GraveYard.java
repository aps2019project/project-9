package model;

import model.cards.Card;

import java.util.ArrayList;

public class GraveYard {
    private Player player;
    private ArrayList<Card> cards;

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCard(String cardID) {
        for (Card card : cards) {
            if (card.getCardID().equals(cardID))
                return card;
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);

    }
}
