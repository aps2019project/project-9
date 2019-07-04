package client;

import view.AccountRequest;

public class ClientRequest {
    private String authKey;
    private RequestType type;
    private AccountRequest accountRequest;
    private String loggedInUserName;
    private String message;

    public ClientRequest(String authKey, RequestType type) {
        this.authKey = authKey;
        this.type = type;
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
