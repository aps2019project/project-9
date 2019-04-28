package model;

import model.items.Collectible;

import java.util.ArrayList;
import java.util.Collections;

public class Account implements Comparable<Account> {
    private Collection myCollection;
    private int money;
    private ArrayList<BattleResult> battleResults = new ArrayList<>();
    private ArrayList<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private static ArrayList<Account> accounts = new ArrayList<>();
    private String userName;
    private String passWord;
    private int numberOfWins = 0;

    public Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        accounts.add(this);
        myCollection = new Collection();
        myCollection.setOwnerAccount(this);
    }

    public static Account findAccount(String userName) {           //if not valid return null
        Account temp = null;
        for (Account key : accounts) {
            if (key != null && key.userName.equals(userName))
                temp = key;
        }
        return temp;
    }

    public static boolean isUserNameToken(String userName) {
        return (findAccount(userName) != null);
    }

    public static boolean isPassWordValid(String userName, String passWord) {
        return findAccount(userName).passWord.equals(passWord);
    }


    public static void sortAccounts() {
        Collections.sort(accounts);
    }

    public void deleteDeck(String deckName) {
        decks.remove(findDeckByName(deckName));
    }

    public void selectMainDeck(Deck deck) {
        mainDeck = deck;
    }

    public void reduceMoney(int money) {
        this.money -= money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public void createNewDeck(String name) {

        decks.add(new Deck(name));
    }

    public ArrayList<Deck> getDecks() {
        return this.decks;
    }
    public Deck getMainDeck(){return mainDeck;}
    public String getUserName() {
        return userName;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public static ArrayList<Account> getAccounts() {
        sortAccounts();
        return accounts;
    }

    public Collection getCollection() {
        return myCollection;
    }

    public int compareTo(Account compare) {
        int compareQuantity = compare.numberOfWins;
        return compareQuantity - this.numberOfWins;
    }
    public Deck findDeckByName(String name){
        for (Deck deck : decks) {
            if(deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public int getMoney() {
        return money;
    }
}