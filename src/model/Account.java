package model;

import java.util.ArrayList;

public class Account {
    private Collection myCollection;
    private int money;
    private BattleResult[] battleResults;
    private Deck[] decks;
    private Deck MainDeck;
    private static ArrayList<Account> accounts;
    private String userName;
    private String passWord;

    Account(String userName) {
        this.userName = userName;
    }

    public static Account findAccount(String userName) {           //if not valid return null
        Account temp = new Account("0");
        for (Account key : accounts) {
            if (key != null && key.userName.equals(userName))
                temp = key;
        }
        if (isUserNameValid(userName))
            return temp;
        else
            return null;
    }

    public static boolean isUserNameValid(String userName) {
        for (Account key : accounts) {
            if (key != null && key.userName.equals(userName))
                return true;
        }
        return false;
    }

    public static boolean isPassWordValid(String passWord) {
        for (Account key : accounts) {
            if (key != null && key.passWord.equals(passWord))
                return true;
        }
        return false;
    }

    public static ArrayList<Account> showAllAccounts() {         //not complete
        return null;
    }

    public static void login(String userName, String passWord) {    //not complete

    }

    public static void sortAccounts() {

    }

    public void deleteDeck(Deck deck) {

    }

    public void selectMainDeck(Deck deck) {
    }

    public Collection getCollection() {
        return null;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void reduceMoney(int money) {
        this.money -= money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void createNewDeck(String name) {
    }

    public Deck[] getDecks() {
        return this.decks;
    }
}
