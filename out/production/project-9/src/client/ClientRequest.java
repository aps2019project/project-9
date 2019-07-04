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

    public ClientRequest(String authKey, RequestType type) {
        this.authKey = authKey;
        this.type = type;
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
