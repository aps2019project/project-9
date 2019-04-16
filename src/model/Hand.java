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

    public String toString() {
        return null;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }
}
