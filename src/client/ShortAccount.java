package client;

import model.Account;

public class ShortAccount {
    String userName;
    int numberOfWins;

    public ShortAccount(Account account) {
        this.userName = account.getUserName();
        this.numberOfWins = account.getNumberOfWins();
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }
}
