package client;

import server.Account;

public class ShortAccount {
    String userName;
    int numberOfWins;
    String isOnline;

    public ShortAccount(Account account,String isOnline) {
        this.userName = account.getUserName();
        this.numberOfWins = account.getNumberOfWins();
        this.isOnline = isOnline;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public String getIsOnline() {
        return isOnline;
    }
}
