package view;

import model.Account;

import java.util.ArrayList;

import static model.Account.getAccounts;
import static model.Account.sortAccounts;

public class AccountMenu {
    public void createAccount(String userName, String passWord) {
        getAccounts().add(new Account(userName,passWord));
    }

    public void login() {         //not complete
    }

    public void showLeaderBoards() {
        sortAccounts();
    }

    public void getInput() {
    }

    public void showMenu() {
    }

    public void showHelp() {
    }

    public void logOut() {
    }
}
