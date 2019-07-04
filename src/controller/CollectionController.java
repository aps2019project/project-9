package controller;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import server.Account;
import model.Deck;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.enumerations.CardType;
import model.enumerations.CollectionErrorType;
import model.items.Item;
import view.CollectionMenu;

public class CollectionController {
    private String loggedInAccount;
    private CollectionMenu collectionMenu;

    CollectionController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount.getUserName();
        collectionMenu = new CollectionMenu(this);
    }

    public void main() {
        collectionMenu.start(new Stage());
    }

    public void validateDeck(String deckName) {
        Deck currentDeck = Client.getAccount(loggedInAccount).findDeckByName(deckName);
        if (currentDeck.isValid()) {
            collectionMenu.printError(CollectionErrorType.DECK_IS_VALID);
        } else {
            collectionMenu.printError(CollectionErrorType.DECK_NOT_VALID);
        }
    }

    public void selectDeck(String deckName) {
        Account account = Client.getAccount(loggedInAccount);
        Deck currentDeck = account.findDeckByName(deckName);
        if (currentDeck.isValid())
            selectMainDeck(deckName);
        else
            collectionMenu.printError(CollectionErrorType.DECK_NOT_VALID);
    }

    private void selectMainDeck(String deckName) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.SELECT_MAIN_DECK);
        clientRequest.setDeckName(deckName);
        Client.sendRequest(clientRequest);
    }

    public void deleteDeck(String deckName) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.DELETE_DECK);
        clientRequest.setDeckName(deckName);
        Client.sendRequest(clientRequest);
    }

    public void createDeck(String newDeckName) {
        Account account = Client.getAccount(loggedInAccount);
        if (account.findDeckByName(newDeckName) != null) {
            collectionMenu.printError(CollectionErrorType.DECK_NAME_EXISTS);
        } else {
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.CREATE_ACCOUNT);
            clientRequest.setDeckName(newDeckName);
            Client.sendRequest(clientRequest);
            //loggedInAccount.createNewDeck(newDeckName);
            collectionMenu.printError(CollectionErrorType.DECK_CREATED);
        }
    }

    public void search(String cardOrItemName, TableView cardtable, TableView itemtable) { // card or item name
        Account account = Client.getAccount(loggedInAccount);
        if (account.getCollection().searchCardByName(cardOrItemName) != null) {
            cardtable.getItems().addAll(FXCollections.observableArrayList(
                    account.getCollection().searchCardByName(cardOrItemName)));
        } else if (account.getCollection().searchItemByName(cardOrItemName) != null) {
            itemtable.getItems().addAll(FXCollections.observableArrayList(
                    account.getCollection().searchItemByName(cardOrItemName)));
        } else
            collectionMenu.printError(CollectionErrorType.CARD_NOT_IN_COLLECTION);
    }

    public void remove(String deckName, int cardOrItemName) { // card or item id
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.REMOVE_FROM_DECK);
        clientRequest.setDeckName(deckName);
        clientRequest.setCardOrItemID(cardOrItemName);
        Client.sendRequest(clientRequest);
        collectionMenu.printError(CollectionErrorType.REMOVED_SUCCESSFULLY);
    }

    public void add(String deckName, int cardOrItemName) { // card or item id
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.ADD_CARD_TO_DECK);
        clientRequest.setCardOrItemID(cardOrItemName);
        clientRequest.setDeckName(deckName);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        if (response.equals("added")) {
            new Alert(Alert.AlertType.INFORMATION, "Added").show();
        } else if (response.equals("deck full")) {
            collectionMenu.printError(CollectionErrorType.DECK_FULL);
        } else if (response.equals("deck has a hero")) {
            collectionMenu.printError(CollectionErrorType.DECK_HAS_A_HERO);
        } else if (response.equals("deck already has an item"))
            collectionMenu.printError(CollectionErrorType.DECK_ALREADY_HAS_AN_ITEM);
    }

    public String getLoggedInAccount() {
        return loggedInAccount;
    }
}
