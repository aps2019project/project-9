package model;

import java.util.ArrayList;
import java.util.Collections;

public class Account implements Comparable<Account> {
    private Collection myCollection;
    private int money;
    private BattleResult[] battleResults;
    private ArrayList<Deck> decks;
    private Deck MainDeck;
    private static ArrayList<Account> accounts;
    private String userName;
    private String passWord;
    private int numberOfWins = 0;

    public Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    private static Account findAccount(String userName) {           //if not valid return null
        Account temp = new Account("0", "0");
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
        return (findAccount(userName) != null);
    }

    public static boolean isPassWordValid(String userName, String passWord) {
        return findAccount(userName).passWord.equals(passWord);
    }

    public static void login(String userName, String passWord) {
        // i dont know the logic
    }

    public static void sortAccounts() {
        Collections.sort(accounts);
    }

    public void deleteDeck(Deck deck) {
        decks.remove(deck);
    }

    public void selectMainDeck(Deck deck) {
        MainDeck = deck;
    }

    public void reduceMoney(int money) {
        this.money -= money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void createNewDeck(String name) {
        //this need deck to be complete
    }

    public ArrayList<Deck> getDecks() {
        return this.decks;
    }

    public String getUserName() {
        return userName;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public Collection getCollection() {
        return myCollection;
    }

    public int compareTo(Account compare) {
        int compareQuantity = compare.numberOfWins;
        return compareQuantity - this.numberOfWins;
    }
}