package controller;

import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.enumerations.CardType;
import model.enumerations.CollectionErrorType;
import model.enumerations.CollectionRequestType;
import model.items.Item;
import view.CollectionMenu;
import view.CollectionRequest;
import view.CollectionView;

public class CollectionController {
    private Account loggedInAccount;
    private CollectionView view = CollectionView.getInstance();
    private CollectionMenu collectionMenu;

    public CollectionController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
        collectionMenu = new CollectionMenu(this);
    }

    public void main(Stage stage) {
        collectionMenu.start(stage);
        CollectionRequest request = new CollectionRequest();
        switch (request.getType()) {
            case VALIDATE_DECK:
                validateDeck(request.getDeckName());
                break;
            case SHOW_DECK:
                showDeck(request.getDeckName());
                break;
            case SEARCH:
                search(request.getCardOrItamName());
                break;
            case REMOVE:
                remove(request.getDeckName(), request.getCardOrItamName());
                break;
            case SHOW:
                view.showCollection(loggedInAccount.getCollection());
                break;
            case SAVE:
                //TODO
                break;
            case ADD:
                add(request.getDeckName(), request.getCardOrItamName());
                break;
        }
    }

    private void validateDeck(String deckName) {
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck == null) {
            view.printError(CollectionErrorType.DECK_NAME_NOT_EXISTS);
        } else {
            boolean isThisDeckValid = currentDeck.isValid();
            if (isThisDeckValid) {
                view.printError(CollectionErrorType.DECK_IS_VALID);
            } else {
                view.printError(CollectionErrorType.DECK_NOT_VALID);
            }
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

    private void showDeck(String deckName) {
        if (loggedInAccount.findDeckByName(deckName) == null)
            view.printError(CollectionErrorType.DECK_NAME_NOT_EXISTS);
        else {
            System.out.println(loggedInAccount.findDeckByName(deckName).toString());
        }
    }

    public void search(String cardOrItemName) { // card or item name
        if (loggedInAccount.getCollection().searchCardByName(cardOrItemName) != null) {
            view.showCardID(loggedInAccount.getCollection().searchCardByName(cardOrItemName));
        } else if (loggedInAccount.getCollection().searchItemByName(cardOrItemName) != null) {
            view.showItemID(loggedInAccount.getCollection().searchItemByName(cardOrItemName));
        } else
            view.printError(CollectionErrorType.CARD_NOT_IN_COLLECTION);
    }

    private void remove(String deckName, String cardOrItemName) { // card or item id
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck == null)
            view.printError(CollectionErrorType.DECK_NAME_NOT_EXISTS);
        else {
            if (currentDeck.getCardByID(cardOrItemName) == null
                    && currentDeck.getItemByID(cardOrItemName) == null) {
                view.printError(CollectionErrorType.NOT_IN_DECK);
            } else {
                if (currentDeck.getCardByID(cardOrItemName) != null) {
                    // card found should be deleted
                    currentDeck.removeCard(currentDeck.getCardByID(cardOrItemName));
                } else {
                    // item found , should be deleted
                    currentDeck.removeItem(currentDeck.getItemByID(cardOrItemName));
                }
                view.printError(CollectionErrorType.REMOVED_SUCCESSFULLY);
            }
        }
    }

    private void add(String deckName, String cardOrItemName) { // card or item id
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        if (currentDeck == null) {
            view.printError(CollectionErrorType.DECK_NAME_NOT_EXISTS);
        } else {
            if (loggedInAccount.getCollection().searchItemByID(cardOrItemName) == null
                    && loggedInAccount.getCollection().searchCardByID(cardOrItemName) == null) {
                view.printError(CollectionErrorType.CARD_NOT_IN_COLLECTION);
            } else if (currentDeck.getCardByID(cardOrItemName) != null ||
                    currentDeck.getItemByID(cardOrItemName) != null) {
                view.printError(CollectionErrorType.ALREADY_IN_DECK);
            } else {
                // should be added here
                Card currentCard = loggedInAccount.getCollection().searchCardByID(cardOrItemName);
                if (currentCard != null) { // it is card not item
                    if (currentCard.getCardType() == CardType.MINION) { // card is a hero or minion
                        Minion currentMinion = (Minion) currentCard;
                        if (currentMinion instanceof Hero && currentDeck.hasHero()) {
                            view.printError(CollectionErrorType.DECK_HAS_A_HERO);
                        } else if (currentMinion instanceof Hero) { // minion is hero
                            currentDeck.setHero((Hero) currentMinion);
                        } else { // it is not hero just minion
                            if (currentDeck.canAddCard())
                                currentDeck.addCard(currentCard);
                            else
                                view.printError(CollectionErrorType.DECK_FULL);
                        }
                    } else { // card is spell
                        if (currentDeck.canAddCard())
                            currentDeck.addCard(currentCard);
                        else
                            view.printError(CollectionErrorType.DECK_FULL);
                    }
                } else { // it is item not card
                    Item currentItem = loggedInAccount.getCollection().getItem(cardOrItemName);
                    if (currentDeck.hasItem())
                        view.printError(CollectionErrorType.DECK_ALREADY_HAS_AN_ITEM);
                    else {
                        currentDeck.addItem(currentItem);
                    }
                }
            }
        }
    }

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }
}
