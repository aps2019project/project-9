package client;

import view.AccountRequest;

public class ClientRequest {
    private String authKey;
    private RequestType type;
    private AccountRequest accountRequest;
    private String accountGson;

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

    public void setAccountGson(String accountGson) {
        this.accountGson = accountGson;
    }

    public String getAccountGson() {
        return accountGson;
    }
}
