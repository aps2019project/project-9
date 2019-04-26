package model;

import model.buffs.Buff;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.enumerations.*;
import model.items.*;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private ArrayList<Buff> activeBuffs;
    private ArrayList<Minion> minionsInPlayGround;
    private int mana;
    private Deck deck;
    private Hand hand;
    private Usable usableItem;
    private ArrayList<Collectible> collectedItems;
    private Hero hero;
    private Battle battle;
    private ArrayList<Flag> flagsAcheived;
    private Flag modeTwoFlag;
    private String name;
    private Card selectedCard;
    private Item selectedCollectableItem;
    private GraveYard graveYard;

    public Player(Account account){
        activeBuffs = new ArrayList<>();
        minionsInPlayGround = new ArrayList<>();
        // mana
        // copy the deck
        // copy usable item from deck
        flagsAcheived = new ArrayList<>();

    }
    public Player(int level){ // for computer AI

    }
    public void attack(Cell cell, Card card) {

    }

    public void comboAttack(Cell cell, ArrayList<Card> cards) {

    }

    public void castUsableItem() {
        ItemName usableName = usableItem.getItemType();
        if (usableName == ItemName.ASSASINATION_DAGGER
                || usableName == ItemName.TERROR_HOOD
                || usableName == ItemName.POISONOUS_DAGGER)
            return; // except for these items
        usableItem.castItem(this);
    }

    public Battle getBattle() {
        return battle;
    }

    public void insertCard(Card card, Cell cell) {
        // check the target cell having flags
        //
        //
        if (card.getCardType() == CardType.MINION) {
            Minion currentMinion = (Minion) card;
            currentMinion.putInMap(cell);
        } else {
            Spell currentSpell = (Spell) card;
            currentSpell.castSpell(cell);
            hand.deleteCard(card);
        }
    }

    public Hand getHand() {
        return hand;
    }

    public void collectItem(Item item) {
        collectedItems.add((Collectible) item);
    }

    public String getName() {
        return name;
    }

    public void endTurn() {
        hand.addCardFromDeck();
        battle.nextTurn();
    }

    public void reduceMana(int number) {
        mana -= number;
    }

    public void useCollectableItem(Item item, Cell cell) {
        Collectible collectibleItem = (Collectible) item;
        collectibleItem.useItem();
        collectedItems.remove(collectibleItem);
    }

    public void move(Minion minion, Cell cell) {
        if (!cell.hasCardOnIt()) {
            for (CellAffect cellAffect : minion.getCell().getCellAffects()) {
                cellAffect.expireCellAffect();
            }
            minion.getCell().deleteCard();

            if (cell.hasCellAffect()) {
                for (CellAffect cellAffect : cell.getCellAffects()) {
                    cellAffect.castCellAffect(minion);
                }
            }
            cell.addCard(minion);


            if (battle.getGameMode() == GameMode.ONE_FLAG) {
                if (cell.getFlag() != null) {
                    modeTwoFlag = cell.getFlag();
                    // assign the flag to this player and to this minion
                    //
                    //
                }
            }
            if (battle.getGameMode() == GameMode.FLAGS) {
                // check having flag
                //
                //
            }
        }
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Collectible> getCollectedItems() {
        return collectedItems;
    }

    public void doAiAction() {
        // ...
    }

    public void addMana(int number) {
        mana += number;
    }

    public void deleteUsableItem() {
        usableItem = null;
    }

    public Player getOpponent() {
        if (battle.getFirstPlayer().equals(this))
            return battle.getSecondPlayer();
        else
            return battle.getFirstPlayer();
    }

    public Hero getHero() {
        return hero;
    }

    public Minion getRandomPower(boolean isRangedOrHybrid) {
        if (isRangedOrHybrid) {
            ArrayList<Minion> rangedHybridMinionsInPlayGround = new ArrayList<>();
            for (Minion minion : minionsInPlayGround) {
                if (minion.getAttackType() == MinionAttackType.HYBRID
                        || minion.getAttackType() == MinionAttackType.RANGED)
                    rangedHybridMinionsInPlayGround.add(minion);
            }
            Random rand = new Random();
            return rangedHybridMinionsInPlayGround.get(rand.nextInt(rangedHybridMinionsInPlayGround.size()));
        } else {
            Random rand = new Random();
            return minionsInPlayGround.get(rand.nextInt(minionsInPlayGround.size()));
        }
    }

    public void giveSpellToRandomPower(Spell spell, boolean isForEnemy) { // spells target shouldn't conflict
        if (isForEnemy) {
            Player opponent = getOpponent();
            opponent.giveSpellToRandomPower(spell, false);
        } else {
            Random rand = new Random();
            Minion randomMinion = minionsInPlayGround.get(rand.nextInt(minionsInPlayGround.size()));
            spell.castSpell(randomMinion.getCell());
        }
    }

    public ArrayList<Minion> getMinionsInPlayGround() {
        return minionsInPlayGround;
    }

    public Deck getDeck() {
        return deck;
    }

    public ArrayList<Flag> getFlagsAcheived() {
        return flagsAcheived;
    }

    public Item getSelectedCollectableItem() {
        return selectedCollectableItem;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public GraveYard getGraveYard() {
        return graveYard;
    }

    public Minion findMinionByIdInPlayGround(String battleId){
        for (Minion minion : getMinionsInPlayGround()) {
            if(minion.getBattleID().equals(battleId))
                return minion;
        }
        return null;
    }
}
