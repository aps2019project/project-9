package controller;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import data.JsonProcess;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import server.Account;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.items.Item;
import view.Shop;

public class ShopController {
    private String loggedInAccount;
    //private Shop shop = Shop.getInstance();
    private Shop shop = Shop.getInstance();

    public String getLoggedInAccount() {
        return loggedInAccount;
    }

    ShopController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount.getUserName();
    }


    public void start() {
        try {
            Stage stage = new Stage();
            shop.start(stage, loggedInAccount, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sell(int id) {
        Card currentCard = Client.getAccount(loggedInAccount).getCollection().searchCardByID(id);
        if (currentCard != null) {// it is card not item
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.SELL_CARD);
            clientRequest.setCardOrItemID(id);
            clientRequest.setCardOrItemName(currentCard.getName());
            Client.sendRequest(clientRequest);
            //Shop.getInstance().sell(currentCard, loggedInAccount);
            shop.printError(ShopErrorType.SOLD_SUCCESSFUL);
        } else {// it is item not card
            Item currentItem = Client.getAccount(loggedInAccount).getCollection().searchItemByID(id);
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.SELL_ITEM);
            clientRequest.setCardOrItemID(id);
            clientRequest.setCardOrItemName(currentItem.getName());
            Client.sendRequest(clientRequest);
            //Shop.getInstance().sell(currentItem, loggedInAccount);
            shop.printError(ShopErrorType.SOLD_SUCCESSFUL);
        }
    }

    private int getCardMoney(String cardName) { // item or card
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_CARD_OR_ITEM_MONEY);
        clientRequest.setCardOrItemName(cardName);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return Integer.parseInt(response);
    }

    private int getAccountMoney() {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_ACCOUNT_MONEY);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return Integer.parseInt(response);
    }

    private int getRemaining(String name) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_REMAINING_IN_SHOP);
        clientRequest.setCardOrItemName(name);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return Integer.parseInt(response);
    }

    public void buy(String itemOrCardName) {
        int cardMoney = getCardMoney(itemOrCardName);
        if (getRemaining(itemOrCardName) == 0) {
            shop.printError(ShopErrorType.NOT_REMAINING);
            return;
        }
        if (isCard(itemOrCardName)) { // card not item
            if (getAccountMoney() < cardMoney)
                shop.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else {
                ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.BUY_CARD);
                clientRequest.setCardOrItemName(itemOrCardName);
                Client.sendRequest(clientRequest);
                //shop.buy(currentCard, loggedInAccount);
                shop.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        } else {// item not card
            if (getAccountMoney() < cardMoney)
                shop.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else if (Client.getAccount(loggedInAccount).getCollection().getNumberOfItems() == 3) {
                shop.printError(ShopErrorType.YOUR_COLLECTION_HAS_THREE_ITEMS);
            } else {
                ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.BUY_ITEM);
                clientRequest.setCardOrItemName(itemOrCardName);
                Client.sendRequest(clientRequest);
                //shop.buy(currentItem, loggedInAccount);
                shop.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        }
    }

    private boolean isCard(String name) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.IS_CARD_IN_SHOP);
        clientRequest.setCardOrItemName(name);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return response.equals("true");
    }

    public void searchShop(String itemOrCardName, TableView cardTable, TableView itemTable) {
        if (notCardNotItem(itemOrCardName))
            shop.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else {
            if (isCard(itemOrCardName)) { // card
                shop.showCardBuy(cardTable, itemTable, getCard(itemOrCardName));
            } else { // item
                shop.showItemBuy(cardTable, itemTable, getItem(itemOrCardName));
            }
        }
    }

    private Card getCard(String name) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_CARD_FROM_SHOP);
        clientRequest.setCardOrItemName(name);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return JsonProcess.getGson().fromJson(response, Card.class);
    }

    private Item getItem(String name) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_ITEM_FROM_SHOP);
        clientRequest.setCardOrItemName(name);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return JsonProcess.getGson().fromJson(response, Item.class);
    }

    private boolean notCardNotItem(String name) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.NOT_CARD_NOT_ITEM);
        clientRequest.setCardOrItemName(name);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return response.equals("true");
    }

    public void searchCollection(String itemOrCardName, TableView cardTable, TableView itemTable) {
        if (notCardNotItem(itemOrCardName))
            shop.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else {
            if (isCard(itemOrCardName)) { // card
                shop.showCardSell(cardTable, itemTable, getCard(itemOrCardName));
            } else { // item
                shop.showItemSell(cardTable, itemTable, getItem(itemOrCardName));
            }
        }
    }
}
