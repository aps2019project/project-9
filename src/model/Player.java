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
    private ArrayList<Minion> minionsInPlayGround = new ArrayList<>();
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

    public Player(Account account) {
        activeBuffs = new ArrayList<>();
        minionsInPlayGround = new ArrayList<>();
        // mana
        // copy the deck
        // copy usable item from deck
        // initialize hero
        flagsAcheived = new ArrayList<>();
        minionsInPlayGround.add(hero);
    }

    public Player(int level) { // for computer AI
        // like above
    }

    public static void enterNewCell(Cell target, Minion minion, Player player) { // not attack to cell
        // entering new cell
        if (target.getFlag() != null) {
            player.collectFlag(target.getFlag(), minion);
        }
        if (target.getCollectableItem() != null) {
            player.collectItem(target.getCollectableItem());
        }
        if (target.getCellAffects().size() > 0) {
            for (CellAffect cellAffect : target.getCellAffects()) {
                cellAffect.castCellAffect(minion);
            }
        }
    }

    public static void leavingACell(Minion minion, Player player, Cell previousCell) {
        if (previousCell.getFlag() != null) {
            if (player.getBattle().getGameMode() == GameMode.FLAGS) {
                Flag flag = previousCell.getFlag();
                flag.setOwningPlayer(null);
                flag.setOwningMinion(null);
                player.getFlagsAcheived().remove(flag);
            }
        }
    }

    public void attack(Cell cell, Card card) {
        if (card instanceof Minion) {
            ((Minion) card).attack(cell);
        }
    }

    public void comboAttack(Cell cell, ArrayList<Minion> minions) {
        for (Minion minion : minions) {
            minion.attack(cell);
        }
    }

    public void castUsableItem() { // every turn should be called
        if (usableItem != null) {
            ItemName usableName = usableItem.getItemType();
            if (usableName == ItemName.ASSASINATION_DAGGER
                    || usableName == ItemName.TERROR_HOOD
                    || usableName == ItemName.POISONOUS_DAGGER)
                return; // except for these items
            usableItem.castItem(this);
        }
    }

    public Battle getBattle() {
        return battle;
    }

    public void insertCard(Card card, Cell cell) {
        if (card.getCardType() == CardType.MINION) {
            enterNewCell(cell, (Minion) card, this);
            Minion currentMinion = (Minion) card;
            currentMinion.putInMap(cell);
            ((Minion) card).setCell(cell);
            reduceMana(card.getMP());
            if (currentMinion.getSpecialPower().getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_SPAWN) {
                currentMinion.getSpecialPower().castSpecialPower(cell);
            }
        } else {
            Spell currentSpell = (Spell) card;
            currentSpell.castSpell(cell);
            hand.deleteCard(card);
            reduceMana(card.getMP());
        }
    }


    public Hand getHand() {
        return hand;
    }

    public void collectItem(Item item) {
        collectedItems.add((Collectible) item);
    }

    public void collectFlag(Flag flag, Minion owningMinion) {
        if (battle.getGameMode() == GameMode.FLAGS) {
            // flags mode
            flagsAcheived.add(flag);
            flag.setOwningPlayer(this);
            flag.setOwningMinion(owningMinion);
            battle.checkWinner();
        } else {
            // one flag mode
            modeTwoFlag = flag;
            flag.setOwningMinion(owningMinion);
            flag.setOwningPlayer(this);
            flag.setTurnsOwned(0);
        }
    }

    public void missFlag(Flag flag) { // opposite of above
        if (battle.getGameMode() == GameMode.ONE_FLAG) {
            modeTwoFlag = null;
            flag.setOwningPlayer(null);
            flag.setOwningMinion(null);
            flag.setTurnsOwned(0);
        } else {
            flag.setOwningMinion(null);
            flag.setOwningPlayer(null);
            flagsAcheived.remove(flag);
        }

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

    public void useCollectableItem() {
        Item item = selectedCollectableItem;
        Collectible collectibleItem = (Collectible) item;
        collectibleItem.useItem();
        collectedItems.remove(collectibleItem);
    }

    public void move(Minion minion, Cell cell) {
        // check collectable items
        Cell previous = minion.getCell();
        if (!cell.hasCardOnIt()) {
            if (cell.hasCellAffect()) {
                for (CellAffect cellAffect : cell.getCellAffects()) {
                    cellAffect.castCellAffect(minion);
                }
            }
            previous.deleteCard();
            cell.addCard(minion);
            if (battle.getGameMode() == GameMode.ONE_FLAG) {
                if (cell.getFlag() != null) {
                    modeTwoFlag = cell.getFlag();
                    collectFlag(cell.getFlag(), minion);
                }
                if (previous.getFlag() != null) {
                    previous.deleteFlag();
                }
            }
            if (battle.getGameMode() == GameMode.FLAGS) {
                if (previous.getFlag() != null) {
                    missFlag(previous.getFlag());
                }
                if (cell.getFlag() != null) {
                    collectFlag(cell.getFlag(), minion);
                }
            }

            minion.setCanMove(false);
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

    public Minion findMinionByIdInPlayGround(String battleId) {
        for (Minion minion : getMinionsInPlayGround()) {
            if (minion.getBattleID().equals(battleId))
                return minion;
        }
        return null;
    }

    public ArrayList<Cell> getCellsToInsertMinion() {
        // for inserting minions from deck , the target should be beside the hero or other minions in play ground
        PlayGround playGround = battle.getPlayGround();
        ArrayList<Cell> cells = new ArrayList<>();
        for (Minion minion : minionsInPlayGround) {
            for (Cell aroundCell : playGround.getAroundCells(minion.getCell())) {
                cells.add(aroundCell);
            }
        }
        return cells;
    }

    public void setSelectedCollectableItem(Item selectedCollectableItem) {
        this.selectedCollectableItem = selectedCollectableItem;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Flag getModeTwoFlag() {
        return modeTwoFlag;
    }

    public void addMinionInPlayGroundMinions(Minion minion) {
        minionsInPlayGround.add(minion);
    }

    public void minionDead(Minion minion) {
        minionsInPlayGround.remove(minion);
    }

    public Usable getUsableItem() {
        return usableItem;
    }
}
