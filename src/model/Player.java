package model;

import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.items.*;

import java.util.ArrayList;

public class Player {
    private ArrayList<Buff> activeBuffs;
    private ArrayList<Card> cardsInPlayGround;
    private int mana;
    private Deck deck;
    private Hand hand;
    private Usable usableItem;
    private ArrayList<Collectable> collectedItems;
    private Hero hero;
    private Battle battle;
    private ArrayList<Flag> flagsAcheived;
    private String name;
    private Card selectedCard;
    private Item selectedCollectableItem;
    private GraveYard graveYard;

    public void attack(Cell cell, Card card) {
    }

    public void comboAttack(Cell cell, ArrayList<Card> cards) {
    }

    public void castUsableItem() {
    }

    public Battle getBattle() {
        return battle;
    }

    public void insertCard(Card card, Cell cell) {
    }

    public void collectItem(Card card, Item item) {
    }

    public String getName() {
        return name;
    }

    public void endTurn() {
    }

    public void reduceMana(int number) {
        mana -= number;
    }

    public void useCollectableItem(Item item, Cell cell) {
    }

    public void move(Card card, Cell cell) {

    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Card> getCardsInPlayGround() {
        return cardsInPlayGround;
    }

    public ArrayList<Collectable> getCollectedItems() {
        return collectedItems;
    }

    public void doAiAction() {
    }
    public void addMana(int number){
        mana+= number;
    }
    public void deleteUsableItem(){
        usableItem = null;
    }
    public Player getOpponent(){return null;}
    public Hero getHero(){
        return hero;
    }
    public Minion getRandomPower(){
        return null;
    }
}
