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
    }

    public static boolean isUserNameValid(String userName) {

    }

    public static boolean isPassWordValid(String passWord) {

    }

    public static ArrayList<Account> showAllAccounts() {

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
    }

    public static ArrayList<Account> getAccounts() {
    }

    public void reduceMoney(int money) {
    }

    public void addMoney(int money) {
    }

    public void createNewDeck(String name) {
    }

    public static Deck[] getDecks() {
    }
}
