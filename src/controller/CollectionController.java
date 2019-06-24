package controller;

import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.enumerations.CardType;
import model.enumerations.CollectionErrorType;
import model.items.Item;
import view.CollectionMenu;

public class CollectionController {
    private Account loggedInAccount;
    private CollectionMenu collectionMenu;

    public CollectionController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
        collectionMenu = new CollectionMenu(this);
    }

    public void main() {
        collectionMenu.start(new Stage());
    }

    public void validateDeck(String deckName) {
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck.isValid()) {
            collectionMenu.printError(CollectionErrorType.DECK_IS_VALID);
        } else {
            collectionMenu.printError(CollectionErrorType.DECK_NOT_VALID);
        }
    }

    public void selectDeck(String deckName) {
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck.isValid())
            loggedInAccount.selectMainDeck(loggedInAccount.findDeckByName(deckName));
        else
            collectionMenu.printError(CollectionErrorType.DECK_NOT_VALID);
    }

    public void deleteDeck(String deckName) {
        loggedInAccount.deleteDeck(deckName);
    }

    public void createDeck(String newDeckName) {
        if (loggedInAccount.findDeckByName(newDeckName) != null) {
            collectionMenu.printError(CollectionErrorType.DECK_NAME_EXISTS);
        } else {
            loggedInAccount.createNewDeck(newDeckName);
            collectionMenu.printError(CollectionErrorType.DECK_CREATED);
        }
    }

    public void search(String cardOrItemName, TableView cardtable, TableView itemtable) { // card or item name
        if (loggedInAccount.getCollection().searchCardByName(cardOrItemName) != null) {
            cardtable.getItems().addAll(FXCollections.observableArrayList(
                    loggedInAccount.getCollection().searchCardByName(cardOrItemName)));
        } else if (loggedInAccount.getCollection().searchItemByName(cardOrItemName) != null) {
            itemtable.getItems().addAll(FXCollections.observableArrayList(
                    loggedInAccount.getCollection().searchItemByName(cardOrItemName)));
        } else
            collectionMenu.printError(CollectionErrorType.CARD_NOT_IN_COLLECTION);
    }

    public void remove(String deckName, int cardOrItemName) { // card or item id
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck.getCardByID(String.valueOf(cardOrItemName)) != null) {
            // card found should be deleted
            currentDeck.removeCard(currentDeck.getCardByID(String.valueOf(cardOrItemName)));
        } else {
            // item found , should be deleted
            currentDeck.removeItem(currentDeck.getItemByID(String.valueOf(cardOrItemName)));
        }
        collectionMenu.printError(CollectionErrorType.REMOVED_SUCCESSFULLY);
    }

    public void add(String deckName, int cardOrItemName) { // card or item id
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        Card currentCard = loggedInAccount.getCollection().searchCardByID(cardOrItemName);
        if (currentCard != null) { // it is card not item
            if (currentCard.getCardType() == CardType.MINION) { // card is a hero or minion
                Minion currentMinion = (Minion) currentCard;
                if (currentMinion instanceof Hero && currentDeck.hasHero()) {
                    collectionMenu.printError(CollectionErrorType.DECK_HAS_A_HERO);
                } else if (currentMinion instanceof Hero) { // minion is hero
                    currentDeck.setHero((Hero) currentMinion);
                } else { // it is not hero just minion
                    if (currentDeck.canAddCard()) {
                        currentDeck.addCard(currentCard);
                        new Alert(Alert.AlertType.INFORMATION, "Added").show();
                    } else
                        collectionMenu.printError(CollectionErrorType.DECK_FULL);
                }
            } else { // card is spell
                if (currentDeck.canAddCard()) {
                    currentDeck.addCard(currentCard);
                    new Alert(Alert.AlertType.INFORMATION, "Added").show();
                } else
                    collectionMenu.printError(CollectionErrorType.DECK_FULL);
            }
        } else { // it is item not card
            Item currentItem = loggedInAccount.getCollection().getItem(cardOrItemName);
            if (currentDeck.hasItem())
                collectionMenu.printError(CollectionErrorType.DECK_ALREADY_HAS_AN_ITEM);
            else {
                currentDeck.addItem(currentItem);
                new Alert(Alert.AlertType.INFORMATION, "Added").show();
            }
        }
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }
}
