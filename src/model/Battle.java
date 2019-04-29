package model;

import model.buffs.Buff;
import model.buffs.DisarmBuff;
import model.cards.Card;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.enumerations.GameMode;
import model.enumerations.SpecialPowerActivationTime;

import java.util.ArrayList;

public class Battle {
    protected static final Deck FIRST_LEVEL_DECK = new Deck("first level");
    protected static final Deck SECOND_LEVEL_DECK = new Deck("second level");
    protected static final Deck THIRD_LEVEL_DECK = new Deck("third level");

    protected int turn;
    protected PlayGround playGround;
    protected GameMode gameMode;
    protected int whoseTurn;
    protected Player firstPlayer;
    protected Player secondPlayer;
    protected int numberOfFlags;
    protected int turnsToWon; // for one flag mode , turns that a flag is in position of winner
    protected int battlePrize; // should be initialized at Constructor()

    public void startBattle() {
        // buffs and cellAffects turns remained should be equal to turnsActive and specialPowers
        // just minions and heros should be copied
        initializeOwningPlayerOfCards(firstPlayer);
        initializeOwningPlayerOfCards(secondPlayer);
    }

    private void initializeOwningPlayerOfCards(Player player){
        for (Card card : player.getDeck().getCards()) {
            if(card instanceof Spell){
                ((Spell)card).setOwningPlayer(player);
            }else if(card instanceof Minion){
                ((Minion)card).setPlayer(player);
            }
        }
    }

    private void checkBuffs(Player player){
        for (Minion minion : player.getMinionsInPlayGround()) {
            ArrayList<Buff> buffsToDelete = new ArrayList<>();
            for (Buff buff : minion.getActiveBuffs()) {
                if(!buff.isForAllTurns()) {
                    buff.reduceTurnsRemained();
                    if (buff.getTurnsRemained() == 0) {
                        buff.endBuff(minion);
                        buffsToDelete.add(buff);
                    }
                }
            }
            for (Buff buff : buffsToDelete) {
                minion.buffDeactivated(buff);
            }
            for (Buff buff : minion.getContinuousBuffs()) {
                buff.startBuff(minion.getCell());
            }
        }
    }



    public void nextTurn() {
        // fill hands
        // change whose turn
        // one flag mode turnsOwned ( flag field ) ++
        firstPlayer.getHero().setTurnsRemainedForNextTurn();
        secondPlayer.getHero().setTurnsRemainedForNextTurn();
        checkBuffs(firstPlayer);
        checkBuffs(secondPlayer);
        checkCellAffects(playGround);
        handlePassiveSpecialPowers(firstPlayer);
        handlePassiveSpecialPowers(secondPlayer);
        handleUsableItems(firstPlayer); // cast them
        handleUsableItems(secondPlayer);// cast them
        handleManaCollectibleItem(firstPlayer);
        handleManaCollectibleItem(secondPlayer);
    }

    private void handleManaCollectibleItem(Player player){
        if(player.getUsedManaItem()){
            player.addMana(3);
            player.setUsedAddManaItem(false);
        }
    } // for item num 8

    private void handleUsableItems(Player player){
        player.castUsableItem();
    }

    private void handlePassiveSpecialPowers(Player player){
        for (Minion minion : player.getMinionsInPlayGround()) {
            if(minion.getSpecialPower().getSpecialPowerActivationTime() == SpecialPowerActivationTime.PASSIVE){
                minion.getSpecialPower().castSpecialPower(minion.getCell());
            }
        }
    }
    private void checkCellAffects(PlayGround playGround){
        ArrayList<CellAffect> cellAffectsToDelete = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = playGround.getCell(j,i);
                for (CellAffect cellAffect : cell.getCellAffects()) {
                    cellAffect.nextTurn();
                    if(cellAffect.getTurnsRemained() == 0)
                        cellAffectsToDelete.add(cellAffect);
                }
            }
        }
        for (CellAffect cellAffect : cellAffectsToDelete) {
            cellAffect.getAffectedCell().removeCellAffect(cellAffect);
        }
    }
    public void endBattle(Player winner) {

    }

    public PlayGround getPlayGround() {
        return playGround;
    }

    public void checkWinner() {
        switch (gameMode) {
            case HERO_KILL:
                if (firstPlayer.getHero().getHP() == 0) {
                    endBattle(secondPlayer);
                } else if (secondPlayer.getHero().getHP() == 0) {
                    endBattle(firstPlayer);
                }
                break;
            case ONE_FLAG:
                // get the turns that the flag is in the position of a player
                if (firstPlayer.getModeTwoFlag() != null &&
                        firstPlayer.getModeTwoFlag().getTurnsOwned() >= turnsToWon)
                    endBattle(firstPlayer);
                else if (secondPlayer.getModeTwoFlag() != null
                        && secondPlayer.getModeTwoFlag().getTurnsOwned() >= turnsToWon)
                    endBattle(secondPlayer);
                break;
            case FLAGS:
                if (firstPlayer.getFlagsAcheived().size() >= numberOfFlags / 2) {
                    endBattle(firstPlayer);
                } else if (secondPlayer.getFlagsAcheived().size() >= numberOfFlags / 2) {
                    endBattle(secondPlayer);
                }
                break;
        }
    }

    public void assignMana(Player player, int number) {
        player.addMana(number);
    }

    public boolean hasValidDeck(Player player) {
        return player.getDeck().isValid();
    }

    public void setGameMode(GameMode mode) {
        this.gameMode = mode;
    }

    public void setNumberOfFlags(int number) {
        numberOfFlags = number;
    }

    public int getTurn() {
        return turn;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public Player getCurrenPlayer() {
        if (whoseTurn == 1)
            return firstPlayer;
        else
            return secondPlayer;
    }
}
