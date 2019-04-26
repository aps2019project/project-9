package model;

import model.cards.Card;

import java.util.ArrayList;

public class Hand {
    public Hand(Deck deck){ // deck should be sorted in start game methods
        numberOfCardFromDeck = 0;
        this.deck = deck;
        this.cards = (ArrayList<Card>)deck.getCards().subList(0,5);
    }
    private ArrayList<Card> cards;
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

    public String toString() {
        return null;
        // also show next turn card ( that will be added to hand )
    }

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
