package model;

import model.cards.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private Deck deck;
    private int numberOfCardFromDeck;
    public Card getNext() {
        return null;
    }

    public void addCardFromDeck() {
        if(cards.size() < 5){
            cards.add(deck.getCards().get(numberOfCardFromDeck++));
        }
    }

    public String toString() {
        return null;
        // also show next turn card ( that will be added to hand )
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }
}
