package model;

import model.cards.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {
    public Hand(Deck deck) { // deck should be sorted in start game methods
        numberOfCardFromDeck = 0;
        this.deck = deck;
        Collections.reverse(deck.getCards());
       //TODO Collections.shuffle(deck.getCards());
        int index = (deck.getCards().size() >= 5) ? (5) : deck.getCards().size();
        for (int i = 0; i < index; i++) {
            this.cards.add(deck.getCards().get(i));
        }
        numberOfCardFromDeck = 5;
    }

    private ArrayList<Card> cards = new ArrayList<>(5);
    private Deck deck;
    private int numberOfCardFromDeck;

    public Card getNext() {
        if (numberOfCardFromDeck < deck.getCards().size())
            return deck.getCards().get(numberOfCardFromDeck);
        else
            return null;
    }


    public void addCardFromDeck() {
        if (cards.size() < 5 && numberOfCardFromDeck < deck.getCards().size()) {
            cards.add(deck.getCards().get(numberOfCardFromDeck++));
        }
    }

    public String toString() {
        String string = "";
        for (Card key : cards) {
            string = string.concat(key.getName() + key.getCardID() + ":" + key.getDesc() + "\n");
        }
        string = string.concat("Next Card :\n");
        if (getNext() != null)
            string = string.concat(getNext().getName() + getNext().getCardID() + ":" + getNext().getDesc() + "\n");
        return string;
    }

    public void deleteCard(Card card) {
        cards.remove(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getCardByName(String cardName) {
        for (Card card : cards) {
            if (card.getName().equals(cardName))
                return card;
        }
        return null;
    }
}
