package client;

import view.AccountRequest;

public class ClientRequest {
    private String authKey;
    private RequestType type;
    private AccountRequest accountRequest;
    private String loggedInUserName;
    private String message;
    private int cardOrItemID;
    private String cardOrItemName;
    private String deckName;
    private String accountJson;
    private String battleResultJson;
    private int prize;
    private GameRequest gameRequest;

    public ClientRequest(String authKey, RequestType type) {
        this.authKey = authKey;
        this.type = type;
    }

    public void setGameRequest(GameRequest gameRequest) {
        this.gameRequest = gameRequest;
    }

    public GameRequest getGameRequest() {
        return gameRequest;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public int getPrize() {
        return prize;
    }

    public String getBattleResultJson() {
        return battleResultJson;
    }

    public void setBattleResultJson(String battleResultJson) {
        this.battleResultJson = battleResultJson;
    }

    public String getAccountJson() {
        return accountJson;
    }

    public void setAccountJson(String accountJson) {
        this.accountJson = accountJson;
    }

    public String getDeckName() {
        return deckName;
    }

    public void setDeckName(String deckName) {
        this.deckName = deckName;
    }

    public void setCardOrItemName(String cardOrItemName) {
        this.cardOrItemName = cardOrItemName;
    }

    public String getCardOrItemName() {
        return cardOrItemName;
    }

    public void setCardOrItemID(int cardOrItemID) {
        this.cardOrItemID = cardOrItemID;
    }

    public int getCardOrItemID() {
        return cardOrItemID;
    }

    public void setAccountRequest(AccountRequest accountRequest) {
        this.accountRequest = accountRequest;
    }

    public RequestType getType() {
        return type;
    }

    public AccountRequest getAccountRequest() {
        return accountRequest;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public String getLoggedInUserName() {
        return loggedInUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
