package model;

import model.cards.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private Deck deck;

    public Card getNext() {
        return null;
    }

    public void addCardFromDeck() {

    }

    public String toString() {               //need Card to be complete
        String string = "";
        for (Card key : cards) {
            string += key.getCardID;
        }
        return string;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }
}
