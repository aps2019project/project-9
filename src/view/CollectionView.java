package view;

import model.Collection;
import model.Deck;
import model.cards.Card;
import model.enumerations.CollectionErrorType;
import model.items.Item;

import java.util.ArrayList;
import java.util.Calendar;

public class CollectionView {
    private static final CollectionView VIEW = new CollectionView();

    public static CollectionView getInstance() {
        return VIEW;
    }

    public void printError(CollectionErrorType errorType) {
        System.out.println(errorType.getMessage());
    }

    public void help() {
        System.out.println("Commands :");
        System.out.println("exit");
        System.out.println("show ( show Collection Items or Cards )");
        System.out.println("Search [card name | item name]");
        System.out.println("save");
        System.out.println("create deck [ deck name ]");
        System.out.println("delete deck [ deck name ]");
        System.out.println("add [card id] to deck [deck name]");
        System.out.println("remove [card id] from deck [deck name]");
        System.out.println("validate deck [deck name]");
        System.out.println("show all decks");
        System.out.println("show deck [deck name]");
        System.out.println("help");
    }

    public void showAllDecks(ArrayList<Deck> decks, Deck mainDeck) {
        ArrayList<Deck> allDecks = decks;
        if (mainDeck != null) {
            allDecks.remove(mainDeck);
        }
        for (Deck deck : allDecks) {
            System.out.println(deck.toString());
        }
    }

    public void showCardID(Card card){
        System.out.println("Card ID :");
        System.out.println(card.getCardID());
    }

    public void showItemID(Item item){
        System.out.println("Item ID :");
        System.out.println(item.getItemID());
    }

    public void showCollection(Collection collection){
        System.out.println(collection.toString());
    }
}
