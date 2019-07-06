package server;

import client.Client;
import client.ClientRequest;
import client.GameRequest;
import client.ShortAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.DeckAddException;
import data.JsonProcess;
import javafx.scene.control.Alert;
import model.*;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.enumerations.CardType;
import model.enumerations.CollectionErrorType;
import model.enumerations.GameMode;
import model.enumerations.ItemName;
import model.items.Collectible;
import model.items.Flag;
import model.items.Item;
import view.InGameRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ClientHandler extends Thread {
    private String authToken;
    DataOutputStream outputStream;
    DataInputStream inputStream;
    private String userName;

    public ClientHandler(String key, Socket socket) {
        this.authToken = key;
        try {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF(authToken);
            accountRequest();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("client disconnected or interrupted , this exception is OK");
            GraphicalServer.onlineClients.remove(this);
            if (userName != null) {
                GraphicalServer.userNamesLoggedIn.remove(userName);
            }
        }
    }

    @Override
    public String toString() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    private Gson gson = new Gson();

    private void accountRequest() throws IOException {
        while (true) {
            String received = inputStream.readUTF();
            ClientRequest request = gson.fromJson(received, ClientRequest.class);
            if (request.getAuthKey().equals(authToken)) {
                switch (request.getType()) {
                    case CREATE_ACCOUNT:
                        Account account = new Account(request.getAccountRequest().getUserName(),
                                request.getAccountRequest().getPassWord());
                        Gson gson = JsonProcess.getGson();
                        outputStream.writeUTF(gson.toJson(account, Account.class));
                        break;
                    case IS_USER_VALID:
                        if (Account.isUserNameToken(request.getAccountRequest().getUserName()))
                            outputStream.writeUTF("false");
                        else
                            outputStream.writeUTF("true");
                        break;
                    case IS_PASS_VALID:
                        if (Account.isPassWordValid(request.getAccountRequest().getUserName(),
                                request.getAccountRequest().getPassWord()))
                            outputStream.writeUTF("true");
                        else
                            outputStream.writeUTF("false");
                        break;
                    case FIND_ACCOUNT:
                        String userName = request.getAccountRequest().getUserName();
                        outputStream.writeUTF(JsonProcess.getGson().
                                toJson(Account.findAccount(userName), Account.class));
                        break;
                    case ACCOUNT_LIST:
                        ArrayList<ShortAccount> userNames = new ArrayList<>();
                        for (Account account1 : Account.getAccounts()) {
                            if (GraphicalServer.userNamesLoggedIn.contains(account1.getUserName()))
                                userNames.add(new ShortAccount(account1, "online"));
                            else
                                userNames.add(new ShortAccount(account1, "offline"));
                        }
                        String toSend = JsonProcess.getGson().toJson(userNames
                                , new TypeToken<ArrayList<ShortAccount>>() {
                                }.getType());
                        outputStream.writeUTF(toSend);
                        break;
                    case BATTLE_RESULT_LIST:
                        userName = request.getAccountRequest().getUserName();
                        Account account1 = Account.findAccount(userName);
                        toSend = JsonProcess.getGson().toJson(account1.getBattleResults(),
                                new TypeToken<ArrayList<BattleResult>>() {
                                }.getType());
                        outputStream.writeUTF(toSend);
                        break;
                    case LOGGED_IN:
                        GraphicalServer.userNamesLoggedIn.add(request.getLoggedInUserName());
                        this.userName = request.getLoggedInUserName();
                        break;
                    case GET_CHAT:
                        outputStream.writeUTF(new Gson().toJson(GraphicalServer.globalChat,
                                new TypeToken<ArrayList<String>>() {
                                }.getType()));
                        break;
                    case SEND_MESSAGE:
                        String message = request.getMessage();
                        if (this.userName != null)
                            GraphicalServer.globalChat.add(this.userName + " : " + message);
                        break;
                    case ONLINE_PLAYERS:
                        outputStream.writeUTF(new Gson().toJson(GraphicalServer.userNamesLoggedIn,
                                new TypeToken<ArrayList<String>>() {
                                }.getType()));
                        break;
                    case SHOP_CARDS:
                        ArrayList<Card> cards = Shop.getInstance().getCards();
                        outputStream.writeUTF(JsonProcess.getGson().toJson(cards,
                                new TypeToken<ArrayList<Card>>() {
                                }.getType()));
                        break;
                    case SHOP_ITEMS:
                        ArrayList<Item> items = Shop.getInstance().getItems();
                        outputStream.writeUTF(JsonProcess.getGson().toJson(items,
                                new TypeToken<ArrayList<Item>>() {
                                }.getType()));
                        break;
                    case SELL_CARD:
                        String name = request.getCardOrItemName();
                        Shop.getInstance().sell
                                (Account.findAccount(this.userName).getCollection().searchCardByName(name).get(0)
                                        , Account.findAccount(this.userName));
                        break;
                    case SELL_ITEM:
                        name = request.getCardOrItemName();
                        Shop.getInstance().sell
                                (Account.findAccount(this.userName).getCollection().searchItemByName(name).get(0)
                                        , Account.findAccount(this.userName));
                        break;
                    case IS_CARD_IN_SHOP:
                        name = request.getCardOrItemName();
                        String res = (Shop.getInstance().searchCardByName(name) != null) ? "true" : "false";
                        outputStream.writeUTF(res);
                        break;
                    case BUY_CARD:
                        name = request.getCardOrItemName();
                        Shop.getInstance().buy(Shop.getInstance().searchCardByName(name),
                                Account.findAccount(this.userName));
                        break;
                    case GET_CARD_OR_ITEM_MONEY:
                        name = request.getCardOrItemName();
                        Shop shop = Shop.getInstance();
                        Card card = shop.searchCardByName(name);
                        if (card != null) {
                            outputStream.writeUTF(String.valueOf(card.getCost()));
                        } else {
                            outputStream.writeUTF(String.valueOf(shop.searchItemByName(name).getCost()));
                        }
                        break;
                    case GET_ACCOUNT_MONEY:
                        outputStream.writeUTF(String.valueOf(Account.findAccount(this.userName).getMoney()));
                        break;
                    case BUY_ITEM:
                        name = request.getCardOrItemName();
                        shop = Shop.getInstance();
                        shop.buy(shop.searchItemByName(name), Account.findAccount(this.userName));
                        break;
                    case NOT_CARD_NOT_ITEM:
                        name = request.getCardOrItemName();
                        shop = Shop.getInstance();
                        if (shop.searchCardByName(name) == null && shop.searchItemByName(name) == null)
                            outputStream.writeUTF("true");
                        else
                            outputStream.writeUTF("false");
                        break;
                    case GET_CARD_FROM_SHOP:
                        name = request.getCardOrItemName();
                        Card card1 = Shop.getInstance().searchCardByName(name);
                        outputStream.writeUTF(JsonProcess.getGson().toJson(card1, Card.class));
                        break;
                    case GET_ITEM_FROM_SHOP:
                        name = request.getCardOrItemName();
                        Item item = Shop.getInstance().searchItemByName(name);
                        outputStream.writeUTF(JsonProcess.getGson().toJson(item, Item.class));
                        break;
                    case SELECT_MAIN_DECK:
                        String deckName = request.getDeckName();
                        Account account2 = Account.findAccount(this.userName);
                        account2.selectMainDeck(account2.findDeckByName(deckName));
                        break;
                    case DELETE_DECK:
                        deckName = request.getDeckName();
                        account = Account.findAccount(this.userName);
                        account.deleteDeck(deckName);
                        break;
                    case CREATE_NEW_DECK:
                        deckName = request.getDeckName();
                        account = Account.findAccount(this.userName);
                        account.createNewDeck(deckName);
                        break;
                    case REMOVE_FROM_DECK:
                        removeFromDeck(request.getDeckName(), request.getCardOrItemID());
                        break;
                    case ADD_CARD_TO_DECK:
                        addToDeck(request.getDeckName(), request.getCardOrItemID());
                        break;
                    case IMPORT_DECK:
                        deckName = request.getDeckName();
                        JsonProcess.addSavedDeckToAccount(Account.findAccount(this.userName), deckName);
                        break;
                    case GET_REMAINING_IN_SHOP:
                        name = request.getCardOrItemName();
                        shop = Shop.getInstance();
                        Item item1 = shop.searchItemByName(name);
                        if (item1 != null)
                            outputStream.writeUTF(String.valueOf(shop.itemNumbers.get(item1)));
                        else
                            outputStream.writeUTF(String.
                                    valueOf(shop.cardNumbers.get(shop.searchCardByName(name))));
                        break;
                    case SAVE_ACCOUNT:
                        JsonProcess.saveAccount(Account.findAccount(this.userName));
                        break;
                    case EXPORT_DECK:
                        deckName = request.getDeckName();
                        try {
                            JsonProcess.exportDeckFromAccount(deckName, Account.findAccount(this.userName));
                            outputStream.writeUTF("ok");
                        } catch (DeckAddException e) {
                            outputStream.writeUTF("error");
                        }
                        break;
                    case ADD_BATTLE_RESULT:
                        account = Account.findAccount(request.getLoggedInUserName());
                        BattleResult battleResult = new Gson().
                                fromJson(request.getBattleResultJson(), BattleResult.class);
                        account.addBattleResult(battleResult);
                        break;
                    case ACCOUNT_WINS:
                        account = Account.findAccount(request.getLoggedInUserName());
                        int prize = request.getPrize();
                        account.wins(prize);
                        break;
                    case GAME_REQUEST:
                        gameRequest(request);
                        break;
                    case CANCELL_GAME_REQUEST:
                        cancellRequest();
                        break;
                    case BATTLE:
                        outputStream.writeUTF(JsonProcess.getGson()
                                .toJson(GraphicalServer.multiPlayerBattle, MultiPlayerBattle.class));
                        break;
                    case COLLECTIBLES:
                        sendCollectibles();
                        break;
                    case FLAGS:
                        sendFlags();
                        break;
                    case IN_GAME_REQUEST:
                        InGameRequest inGameRequest = request.getInGameRequest();
                        doGameRequest(inGameRequest);
                        break;
                    case MY_USERNAME:
                        outputStream.writeUTF(this.userName);
                        break;
                }


            }
        }
    }

    private void doGameRequest(InGameRequest request) throws IOException {
        ClientHandler opponent = getOpponent();

        opponent.outputStream.writeUTF(new Gson().toJson(request, InGameRequest.class));
    }

    private ClientHandler getOpponent() {
        if (GraphicalServer.multiPlayerBattle.getFirstPlayer().getName().equals(this.userName)) {
            String name = GraphicalServer.multiPlayerBattle.getSecondPlayer().getName();
            for (ClientHandler onlineClient : GraphicalServer.onlineClients) {
                if (onlineClient.userName.equals(name))
                    return onlineClient;
            }
        } else {
            String name = GraphicalServer.multiPlayerBattle.getFirstPlayer().getName();
            for (ClientHandler onlineClient : GraphicalServer.onlineClients) {
                if (onlineClient.userName.equals(name))
                    return onlineClient;
            }
        }
        return null;
    }

    private void sendFlags() throws IOException {
        PlayGround playGround = GraphicalServer.multiPlayerBattle.getPlayGround();
        ArrayList<Cell> result = new ArrayList<>();
        for (Flag flag : playGround.getFlags()) {
            result.add(flag.getCurrentCell());
        }
        outputStream.writeUTF(new Gson().toJson(result, new TypeToken<ArrayList<Cell>>() {
        }.getType()));
    }

    private void sendCollectibles() throws IOException {
        PlayGround playGround = GraphicalServer.multiPlayerBattle.getPlayGround();
        HashMap<ItemName, Cell> result = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (playGround.getCell(i, j).hasCollectableItem()) {
                    result.put(playGround.getCell(i, j).getCollectableItem().getItemType()
                            , playGround.getCell(i, j));
                }
            }
        }
        outputStream.writeUTF(new Gson().toJson(result, new TypeToken<HashMap<ItemName, Cell>>() {
        }.getType()));
    }

    private void gameRequest(ClientRequest request) {
        GameRequest gameRequest = request.getGameRequest();
        GraphicalServer.gameRequests.add(gameRequest);
        if (GraphicalServer.gameRequests.size() == 2) {
            if (GraphicalServer.gameRequests.get(0).getGameMode()
                    == GraphicalServer.gameRequests.get(1).getGameMode()
                    && GraphicalServer.gameRequests.get(0).getNumberOfFlags()
                    == GraphicalServer.gameRequests.get(1).getNumberOfFlags()) {
                GraphicalServer.startGame();
            }
        }
    }

    private void cancellRequest() {
        for (GameRequest gameRequest : GraphicalServer.gameRequests) {
            if (gameRequest.getUserRequested().equals(this.userName)) {
                GraphicalServer.gameRequests.remove(gameRequest);
                break;
            }
        }
    }

    private void addToDeck(String deckName, int cardOrItemName) throws IOException {
        Account loggedInAccount = Account.findAccount(this.userName);
        Deck currentDeck = loggedInAccount.findDeckByName(deckName);
        Card currentCard = loggedInAccount.getCollection().searchCardByID(cardOrItemName);
        if (currentCard != null) { // it is card not item
            if (currentCard.getCardType() == CardType.MINION) { // card is a hero or minion
                Minion currentMinion = (Minion) currentCard;
                if (currentMinion instanceof Hero && currentDeck.hasHero()) {
                    outputStream.writeUTF("deck has a hero");
                } else if (currentMinion instanceof Hero) { // minion is hero
                    currentDeck.setHero((Hero) currentMinion);
                } else { // it is not hero just minion
                    if (currentDeck.canAddCard()) {
                        currentDeck.addCard(currentCard);
                        outputStream.writeUTF("added");
                    } else
                        outputStream.writeUTF("deck full");
                }
            } else { // card is spell
                if (currentDeck.canAddCard()) {
                    currentDeck.addCard(currentCard);
                    outputStream.writeUTF("added");
                } else
                    outputStream.writeUTF("deck full");
            }
        } else { // it is item not card
            Item currentItem = loggedInAccount.getCollection().getItem(cardOrItemName);
            if (currentDeck.hasItem())
                outputStream.writeUTF("deck already has an item");
            else {
                currentDeck.addItem(currentItem);
                outputStream.writeUTF("added");
            }
        }
    }

    private void removeFromDeck(String deck, int id) {
        Account loggedInAccount = Account.findAccount(this.userName);
        Deck currentDeck = loggedInAccount.findDeckByName(deck);
        if (currentDeck.getCardByID(String.valueOf(id)) != null) {
            // card found should be deleted
            currentDeck.removeCard(currentDeck.getCardByID(String.valueOf(id)));
        } else {
            // item found , should be deleted
            currentDeck.removeItem(currentDeck.getItemByID(String.valueOf(id)));
        }
    }


    public String getAuthToken() {
        return authToken;
    }
}
