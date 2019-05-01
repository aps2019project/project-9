package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    public Hand(Deck deck){ // deck should be sorted in start game methods
        numberOfCardFromDeck = 0;
        this.deck = deck;
        Collections.shuffle(deck.getCards());
        for (int i = 0; i < 5; i++) {
            this.cards.add(deck.getCards().get(i));
        }
        numberOfCardFromDeck = 5;
    }
    private ArrayList<Card> cards = new ArrayList<>(5);
    private Deck deck;
    private int numberOfCardFromDeck;
    public Card getNext() {
        return deck.getCards().get(numberOfCardFromDeck++);
    }


    public void addCardFromDeck() {
        if(cards.size() < 5){
            cards.add(deck.getCards().get(numberOfCardFromDeck++));
        }
    }

    /*public String toString() {
        return null;
        // also show next turn card ( that will be added to hand )
    }*/

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCardByName(String cardName){
        for (Card card : cards) {
            if(card.getName().equals(cardName))
                return card;
        }
        return null;
    }
}
