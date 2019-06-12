package model;

import model.buffs.Buff;
import model.buffs.StunBuff;
import model.buffs.WeaknessBuff;
import model.cards.Card;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.enumerations.GameMode;
import model.enumerations.SpecialPowerActivationTime;
import view.InGameView;
import view.NewInGameView;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

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
        firstPlayer.setMaxMana(2);
        secondPlayer.setMaxMana(2);
        firstPlayer.castUsableItem();
        secondPlayer.castUsableItem();
    }

    private void initializeHeroAttributes() {
        firstPlayer.getHero().setCell(playGround.getCell(2, 0));
        secondPlayer.getHero().setCell(playGround.getCell(2, 8));
        playGround.getCell(2, 0).setMinionOnIt(firstPlayer.getHero());
        playGround.getCell(2, 8).setMinionOnIt(secondPlayer.getHero());
        /*firstPlayer.getHero().setCell(playGround.getCell(2, 4));
        secondPlayer.getHero().setCell(playGround.getCell(2, 5));
        playGround.getCell(2, 4).setMinionOnIt(firstPlayer.getHero());
        playGround.getCell(2, 5).setMinionOnIt(secondPlayer.getHero());*/
        firstPlayer.getHero().setBattleID(firstPlayer.getName() + "_" + firstPlayer.getHero().getName()
                + "_" + "1");
        secondPlayer.getHero().setBattleID(secondPlayer.getName() + "_" + secondPlayer.getHero().getName()
                + "_" + "1");
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
        ArrayList<Minion> minions = new ArrayList<>();
        for (Minion minion : player.getMinionsInPlayGround()) {
            minions.add(minion);
        }

        for (Minion minion : minions) {
            ArrayList<Buff> buffsToDelete = new ArrayList<>();


            for (Buff buff : minion.getActiveBuffs()) {
                /*System.out.println("buff : " + buff.getBuffName() + " from minion : " + minion.getName()
                        + " has : " + buff.getTurnsRemained() + " turns remained ");*/
                if (buff instanceof WeaknessBuff && ((WeaknessBuff) buff).getIsDelayBuff()) {
                    buff.startBuff(minion.getCell());
                }
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
                buff.getCopy().startBuff(minion.getCell());
            }
        }
    }


    public void nextTurn() {
        if (gameMode == GameMode.ONE_FLAG) {
            if (playGround.getFlag().getOwningMinion() != null)
                playGround.getFlag().nextTurn();
            checkWinner();
        }
        firstPlayer.getHero().setTurnsRemainedForNextTurn();
        secondPlayer.getHero().setTurnsRemainedForNextTurn();
        handleCanMoveCanAttack(firstPlayer);
        handleCanMoveCanAttack(secondPlayer);
        checkBuffs(firstPlayer);
        checkBuffs(secondPlayer);
        checkCellAffects(playGround);
        handlePassiveSpecialPowers(firstPlayer);
        handlePassiveSpecialPowers(secondPlayer);
        assignMana();
        firstPlayer.castUsableItem();
        secondPlayer.castUsableItem();
        whoseTurn = (whoseTurn == 1) ? (2) : (1);
        turn++;
        if (this instanceof SinglePlayerBattle && whoseTurn == 2) {
            secondPlayer.doAiAction();
        }
    }

    private void handleCanMoveCanAttack(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            boolean check = false;
            for (Buff activeBuff : minion.getActiveBuffs()) {
                if (activeBuff instanceof StunBuff) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                minion.setCanMove(true);
                minion.setCanAttack(true);
            }
        }
    }

    private void assignMana() {
        if (whoseTurn == 1) {
            secondPlayerMana = secondPlayer.getMaxMana() + 1;
            if (secondPlayerMana > 9) {
                secondPlayer.setMaxMana(9);
                secondPlayer.assignMana(9);
            } else {
                secondPlayer.setMaxMana(secondPlayerMana);
                secondPlayer.assignMana(secondPlayerMana);
            }
            secondPlayer.assignMana(secondPlayer.getMana() + secondPlayer.getManaForNextTurnIncrease());
            firstPlayer.assignMana(firstPlayer.getMaxMana());
        } else {
            firstPlayerMana = firstPlayer.getMaxMana() + 1;
            if (firstPlayerMana > 9) {
                firstPlayer.setMaxMana(9);
                firstPlayer.assignMana(9);
            } else {
                firstPlayer.setMaxMana(secondPlayerMana);
                firstPlayer.assignMana(secondPlayerMana);
            }
            firstPlayer.assignMana(firstPlayer.getMana() + firstPlayer.getManaForNextTurnIncrease());
            secondPlayer.assignMana(secondPlayer.getMaxMana());
        }
    }

    private void handlePassiveSpecialPowers(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            try {
                if (minion.getSpecialPower() != null &&
                        minion.getSpecialPower().getSpecialPowerActivationTime() == SpecialPowerActivationTime.PASSIVE) {
                    minion.getSpecialPower().castSpecialPower(minion.getCell());
                }
            } catch (ConcurrentModificationException e) {

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
        BattleResult battleResult = new BattleResult(winner, battlePrize, this);
        if (Account.findAccount(firstPlayer.getName()) != null) {
            Account.findAccount(firstPlayer.getName()).addBattleResult(battleResult);
        }
        battleResult = new BattleResult(winner, battlePrize, this);
        if (Account.findAccount(secondPlayer.getName()) != null) {
            Account.findAccount(secondPlayer.getName()).addBattleResult(battleResult);
        }
        InGameView view = InGameView.getInstance();
        view.endGameOutput(battleResult);
        if (firstPlayer.equals(winner)) {
            Account.findAccount(firstPlayer.getName()).wins();
        } else if (!(this instanceof SinglePlayerBattle)) {
            Account.findAccount(secondPlayer.getName()).wins();
        }
        NewInGameView.finished(battleResult);
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
                int checkingInt = (numberOfFlags % 2 == 0) ? (numberOfFlags / 2) : ((numberOfFlags + 1) / 2);
                if (firstPlayer.getFlagsAcheived().size() >= checkingInt) {
                    endBattle(firstPlayer);
                } else if (secondPlayer.getFlagsAcheived().size() >= checkingInt) {
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
