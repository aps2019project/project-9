package model;

import controller.InGameController;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.enumerations.*;
import model.items.*;
import view.GraphicalInGameView;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private ArrayList<Minion> minionsInPlayGround = new ArrayList<>();
    private int mana;
    private int maxMana;
    private Deck deck;
    private Hand hand;
    private Usable usableItem;
    private ArrayList<Collectible> collectedItems = new ArrayList<>();
    private Hero hero;
    private Battle battle;
    private ArrayList<Flag> flagsAcheived = new ArrayList<>();
    private Flag modeTwoFlag;
    private String name;
    private Card selectedCard;
    private Item selectedCollectableItem;
    private GraveYard graveYard;
    private boolean usedAddManaItem = false; // for item num 8
    private int manaForNextTurnIncrease = 0;
    private static int uniqueItemID = 100;

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public Player(Account account, Battle battle) {
        this.deck = account.getMainDeck().getCopy();
        if (deck.getItem() != null)
            usableItem = (Usable) deck.getItem();
        else
            usableItem = null;
        hero = deck.getHero();
        this.battle = battle;
        this.name = account.getUserName();
        minionsInPlayGround.add(hero);
        graveYard = new GraveYard(this);
        hand = new Hand(deck);
    }

    public Player(int level, Battle battle) { // for computer AI
        name = "first_level";
        switch (level) {
            case 1:
                name = "first_level";
                break;
            case 2:
                name = "second_level";
                break;
            case 3:
                name = "third_level";
                break;
        }
        this.deck = new Deck(name);
        if (deck.getItem() != null)
            usableItem = (Usable) deck.getItem();
        else
            usableItem = null;
        hero = deck.getHero();
        this.battle = battle;
        minionsInPlayGround.add(hero);
        graveYard = new GraveYard(this);
        hand = new Hand(deck);
    }

    public Player(Deck deck, Battle battle) {
        this.deck = deck;
        if (deck.getItem() != null)
            usableItem = (Usable) deck.getItem();
        else
            usableItem = null;
        hero = deck.getHero();
        this.battle = battle;
        minionsInPlayGround.add(hero);
        graveYard = new GraveYard(this);
        name = "CustomOpponent";
    }

    public static void enterNewCell(Cell target, Minion minion, Player player) { //not attack to cell entering new cell
        if (target.getFlag() != null) {
            player.collectFlag(target, minion);
        }
        if (target.getCollectableItem() != null) {
            player.collectItem(target.getCollectableItem());
            target.setCollectableItem(null);
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
            if (usableItem instanceof OnSpawnUsableItem || usableItem instanceof OnDeathUsableItem)
                return;
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
            currentMinion.setCanMove(false);
            currentMinion.setCanAttack(false);
            if (currentMinion.getSpecialPower() != null
                    && currentMinion.getSpecialPower().getSpecialPowerActivationTime() == SpecialPowerActivationTime.ON_SPAWN) {
                currentMinion.getSpecialPower().castSpecialPower(cell);
            }
            if (usableItem != null && usableItem instanceof OnSpawnUsableItem) {
                ((OnSpawnUsableItem) usableItem).doOnSpawnAction(this, currentMinion);
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
        ((Collectible) item).collect(this);
        item.setItemID(uniqueItemID++);
        PlayGround playGround = battle.getPlayGround();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (playGround.getCell(i, j).getCollectableItem() != null
                        && playGround.getCell(i, j).getCollectableItem().getName().equals(item.getName())) {
                    playGround.getCell(i, j).setCollectableItem(null);
                }
            }
        }
    }

    public void collectFlag(Cell targetCell, Minion owningMinion) {
        Flag flag = targetCell.getFlag();
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
            flag.setCurrentCell(targetCell);
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (battle.getPlayGround().getCell(i, j).getCollectableItem() != null
                        && battle.getPlayGround().getCell(i, j).getCollectableItem()
                        .getName().equals(collectibleItem.getName())) {
                    battle.getPlayGround().getCell(i, j).setCollectableItem(null);
                    return;
                }
            }
        }
    }

    public void move(Minion minion, Cell cell) {
        // check collectable items
        Cell previous = minion.getCell();
        if (!cell.hasCardOnIt()) {
            if (cell.getCollectableItem() != null) {
                collectItem(cell.getCollectableItem());
            }
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
                    collectFlag(cell, minion);
                }
                if (previous.getFlag() != null) {
                    previous.getFlag().setCurrentCell(cell);
                    cell.setFlag(previous.getFlag());
                    previous.deleteFlag();
                }
            }
            if (battle.getGameMode() == GameMode.FLAGS) {
                if (previous.getFlag() != null) {
                    missFlag(previous.getFlag());
                }
                if (cell.getFlag() != null) {
                    collectFlag(cell, minion);
                }
            }
            minion.setCanMove(false);
            minion.setCell(cell);
        }
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Collectible> getCollectedItems() {
        return collectedItems;
    }

    private String action;

    public void doAiAction() {
        action = "";
        insertAiAction();
        moveAiAction();
        attackAiAction();
        GraphicalInGameView.alertAiAction(action);
        //GraphicalInGameView.doAiAnimations(action);
        endTurn();
    }

    private void moveAiAction() {
        for (Minion minion : getMinionsInPlayGround()) {
            try {
                if (minion.isCanMove()) {
                    Cell target = targetAiMove(minion);
                    //TODO
                    Cell first = minion.getCell();

                    move(minion, target);

                    //GraphicalInGameView.moveTo(first,target);
                    //GraphicalInGameView.alertAiAction(AiAction.MOVE,minion,target);
                    action += "\nMinion : " + minion.getName() + "\nmoved to : " + target.getX() + " " + target.getY()
                    + "\nfrom : " + first.getX() + " " + first.getY();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Cell targetAiMove(Minion minion) throws Exception {
        Cell target = getOpponent().getHero().getCell();
        ArrayList<Cell> possibleCellsForMove = GraphicalInGameView.getPossibleCellsForMove(minion);
        if (possibleCellsForMove.size() > 0) {
            PlayGround playGround = battle.getPlayGround();
            if (playGround.getManhatanDistance(target, minion.getCell()) <= 2)
                throw new Exception("no need to move");
            int min = playGround.getManhatanDistance(target, possibleCellsForMove.get(0));
            Cell result = possibleCellsForMove.get(0);
            for (Cell cell : possibleCellsForMove) {
                if (playGround.getManhatanDistance(cell, target) < min) {
                    min = playGround.getManhatanDistance(cell, target);
                    result = cell;
                }
            }
            return result;
        }
        throw new Exception("no possible cells");
    }


    private void insertAiAction() {
        ArrayList<Card> cards = hand.getCards();
        ArrayList<Card> toInsert = new ArrayList<>();
        for (Card card : cards) {
            ArrayList<Cell> possibleCells = GraphicalInGameView.getPossibleCells(card);
            if (possibleCells.size() > 0) {
                if (mana >= card.getMP()) {
                    InGameController.finalThingsInInsertingCard(card, this, possibleCells.get(0));
                    //GraphicalInGameView.alertAiAction(AiAction.INSERT, card, possibleCells.get(0));
                    Cell cell = possibleCells.get(0);
                    action += "\nYour Opponent : Inserted " + card.getName() + " in " + cell.getX() + " " + cell.getY();
                    break;
                }
            }
        }
    }

    private void attackAiAction() {
        for (Minion minion : getMinionsInPlayGround()) {
            if (minion.isCanAttack()) {
                ArrayList<Cell> possibleCellsForAttack = GraphicalInGameView.getPossibleCellsForAttack(minion);
                if (possibleCellsForAttack.size() > 0) {
                    Cell target;
                    if (possibleCellsForAttack.contains(getOpponent().getHero().getCell())) {
                        target = getOpponent().getHero().getCell();
                    } else {
                        target = possibleCellsForAttack.get(0);
                    }
                    minion.attack(target);
                    //GraphicalInGameView.alertAiAction(AiAction.ATTACK, minion, target);
                    action += "\n" + minion.getName() + " : attacked to the Cell:  "
                            + target.getX() + " " + target.getY();
                    break;
                }
            }
        }
    }

    public void addMana(int number) {
        mana += number;
    }

    public void assignMana(int mana) {
        this.mana = mana;
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

    public void setOnAttackItemForAllPlayers(OnAttackSpellUsable item) {
        for (Minion minion : minionsInPlayGround) {
            minion.setOnAttackItem(item);
        }
    }

    public void setUsedAddManaItem(boolean usedAddManaItem) {
        this.usedAddManaItem = usedAddManaItem;
    }

    public boolean getUsedManaItem() {
        return usedAddManaItem;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof Player)) {
            return this.name.equals(((Player) obj).getName());
        } else
            return false;
    }

    public void setManaForNextTurnIncrease(int manaForNextTurnIncrease) {
        this.manaForNextTurnIncrease = manaForNextTurnIncrease;
    }

    public int getManaForNextTurnIncrease() {
        int archive = manaForNextTurnIncrease;
        manaForNextTurnIncrease = 0;
        return archive;
    }
}
