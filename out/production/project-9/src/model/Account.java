package model;

import data.JsonProcess;
import model.cards.Card;
import model.items.Item;

import java.util.ArrayList;
import java.util.Collections;

public class Account implements Comparable<Account> {
    private static ArrayList<Account> accounts = new ArrayList<>();

    static {
        setAccounts();
    }

    private Collection myCollection;

    private int money = 15000;

    private ArrayList<BattleResult> battleResults = new ArrayList<>();

    private ArrayList<Deck> decks = new ArrayList<>();

    private String mainDeck;

    private String userName;

    private String passWord;

    private int numberOfWins = 0;

    public Account(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
        accounts.add(this);
        myCollection = new Collection();
        //mainDeck = new Deck("debugging");
        mainDeck = "first_level"; // initialized deck
        decks.add(new Deck("first_level"));
        JsonProcess.saveAccount(this);
    }

    public static Account findAccount(String userName) {//if not valid return null
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
        if (mainDeck.equals(deckName))
            mainDeck = null;
    }

    public void selectMainDeck(Deck deck) {
        mainDeck = deck.getName();
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

    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    public ArrayList<Deck> getDecks() {
        return this.decks;
    }

    public Deck getMainDeck() {
        return findDeckByName(mainDeck);
    }

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

    public Deck findDeckByName(String name) {
        for (Deck deck : decks) {
            if (deck.getName().equals(name))
                return deck;
        }
        return null;
    }

    public int getMoney() {
        return money;
    }

    public void addBattleResult(BattleResult result) {
        battleResults.add(result);
    }

    public void wins(int prize) {
        money += prize;
        numberOfWins++;
    }

    public void deleteCardFromDecks(Card card) {
        for (Deck deck : decks) {
            deck.removeCard(card);
        }
    }

    public void deleteCardFromDecks(Item item) {
        for (Deck deck : decks) {
            deck.removeItem(item);
        }
    }

    public ArrayList<BattleResult> getBattleResults() {
        return battleResults;
    }

    public static void setAccounts() {
        accounts = JsonProcess.getSavedAccounts();
    }

    @Override
    public String toString() {
        return userName;
    }
}