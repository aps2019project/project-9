package model;

import model.buffs.Buff;
import model.cards.Card;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.enumerations.GameMode;
import model.enumerations.SpecialPowerActivationTime;
import view.InGameView;

import java.util.ArrayList;

public class Battle {
    protected int firstPlayerMana;
    protected int secondPlayerMana;
    protected int turn;
    protected PlayGround playGround;
    protected GameMode gameMode;
    protected int whoseTurn;
    protected Player firstPlayer;
    protected Player secondPlayer;
    protected int numberOfFlags;
    protected int turnsToWon; // for one flag mode , turns that a flag is in position of winner
    protected int battlePrize; // should be initialized at Constructor()
    protected int level; // for single player games ( in battle result )


    public void startBattle() {
        // ..........
        firstPlayerMana = 2;
        secondPlayerMana = 2;
        initializeOwningPlayerOfCards(firstPlayer);
        initializeOwningPlayerOfCards(secondPlayer);
        initializeHeroAttributes();
        firstPlayer.assignMana(2);
        secondPlayer.assignMana(2);
        /*initializeSpecialPowersMinions(firstPlayer);
        initializeSpecialPowersMinions(secondPlayer);*/
    }

    /*private void initializeSpecialPowersMinions(Player player){
        for (Card card : player.getDeck().getCards()) {
            if(card instanceof Minion){
                Minion minion = (Minion)card;
                minion.getSpecialPower().setMinion(minion);
            }
        }
    }*/
    private void initializeHeroAttributes(){
        firstPlayer.getHero().setCell(playGround.getCell(2,0));
        secondPlayer.getHero().setCell(playGround.getCell(2,8));
        firstPlayer.getHero().setBattleID(firstPlayer.getName() + "_" + firstPlayer.getHero().getName()
        + "_" +"1");
        secondPlayer.getHero().setBattleID(secondPlayer.getName() + "_" + secondPlayer.getHero().getName()
                + "_" +"1");
    }

    private void initializeOwningPlayerOfCards(Player player) {
        for (Card card : player.getDeck().getCards()) {
            if (card instanceof Spell) {
                ((Spell) card).setOwningPlayer(player);
            } else if (card instanceof Minion) {
                ((Minion) card).setPlayer(player);
            }
        }
        player.getDeck().getHero().setPlayer(player);
    }

    private void checkBuffs(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            ArrayList<Buff> buffsToDelete = new ArrayList<>();
            for (Buff buff : minion.getActiveBuffs()) {
                if (!buff.isForAllTurns()) {
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
            ArrayList<Buff> buffsToAdd = new ArrayList<>(); // for exception handling
            for (Buff buff : minion.getContinuousBuffs()) {
                buffsToAdd.add(buff);
            }
            for (Buff buff : buffsToAdd) {
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
        assignMana();
        whoseTurn = (whoseTurn == 1) ? (2) : (1);
        turn++;
        if(this instanceof SinglePlayerBattle){
            secondPlayer.doAiAction();
        }
        handleCanMoveCanAttack(firstPlayer);
        handleCanMoveCanAttack(secondPlayer);
    }

    private void handleCanMoveCanAttack(Player player){
        for (Minion minion : player.getMinionsInPlayGround()) {
            minion.setCanMove(true);
            minion.setCanAttack(true);
        }
    }
    private void assignMana() {
        if (whoseTurn == 1) {
            secondPlayerMana++;
            secondPlayer.assignMana(secondPlayerMana);
        } else {
            firstPlayerMana++;
            firstPlayer.assignMana(firstPlayerMana);
        }
    }

    private void handleManaCollectibleItem(Player player) {
        if (player.getUsedManaItem()) {
            player.addMana(3);
            player.setUsedAddManaItem(false);
        }
    } // for item num 8

    private void handleUsableItems(Player player) {
        player.castUsableItem();
    }

    private void handlePassiveSpecialPowers(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            if (minion.getSpecialPower()!=null &&
                    minion.getSpecialPower().getSpecialPowerActivationTime() == SpecialPowerActivationTime.PASSIVE) {
                minion.getSpecialPower().castSpecialPower(minion.getCell());
            }
        }
    }

    private void checkCellAffects(PlayGround playGround) {
        ArrayList<CellAffect> cellAffectsToDelete = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Cell cell = playGround.getCell(j, i);
                for (CellAffect cellAffect : cell.getCellAffects()) {
                    cellAffect.nextTurn();
                    if (cellAffect.getTurnsRemained() == 0)
                        cellAffectsToDelete.add(cellAffect);
                }
            }
        }
        for (CellAffect cellAffect : cellAffectsToDelete) {
            cellAffect.getAffectedCell().removeCellAffect(cellAffect);
        }
    }

    public void endBattle(Player winner) {
        BattleResult battleResult = new BattleResult(firstPlayer, secondPlayer, winner, level , battlePrize);
        if (Account.findAccount(firstPlayer.getName()) != null) {
            Account.findAccount(firstPlayer.getName()).addBattleResult(battleResult);
        }
        battleResult = new BattleResult(secondPlayer, firstPlayer, winner, level , battlePrize);
        if (Account.findAccount(secondPlayer.getName()) != null) {
            Account.findAccount(secondPlayer.getName()).addBattleResult(battleResult);
        }
        InGameView view = InGameView.getInstance();
        view.endGameOutput(battleResult);
        if(firstPlayer.equals(winner)){
            Account.findAccount(firstPlayer.getName()).wins();
        }else if(!(this instanceof SinglePlayerBattle)){
            Account.findAccount(secondPlayer.getName()).wins();
        }
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
