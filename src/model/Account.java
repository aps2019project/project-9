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


    public static Account findAccount(String userName) {
        return null;
    }

    public static boolean isUserNameValid(String userName) {
        return false;
    }

    public static boolean isPassWordValid(String passWord) {
        return false;
    }

    public static ArrayList<Account> showAllAccounts() {
        return null;
    }

    public static void login(String userName, String passWord) {

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
        return null;
    }

    public void reduceMoney(int money) {
    }

    public void addMoney(int money) {
    }

    public void createNewDeck(String name) {
    }

    public static Deck[] getDecks() {
        return null;
    }
}
