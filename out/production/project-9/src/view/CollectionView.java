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

}
