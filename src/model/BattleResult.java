package model;


import model.enumerations.GameMode;
import model.items.Collectible;
import model.items.Flag;
import view.InGameRequest;

import java.util.ArrayList;
import java.util.HashMap;


public class BattleResult {

    private String winner;
    private String looser;
    private int prize;
    private String time;
    private GameMode gameMode;
    private int numberOfFlags;
    private int level;
    private boolean isSinglePlayer;
    private String firstPlayer;
    private String secondPlayer;

    public BattleResult(Player winner, int prize, String time, String looser
            , Battle battle) {
        this.winner = winner.getName();
        this.prize = prize;
        this.time = time;
        this.looser = looser;
        gameMode = battle.gameMode;
        numberOfFlags = battle.numberOfFlags;
        level = battle.level;
        isSinglePlayer = battle instanceof SinglePlayerBattle;
        this.firstPlayer = battle.getFirstPlayer().getName();
        this.secondPlayer = battle.getSecondPlayer().getName();
    }



    public String getSecondPlayer() {
        return secondPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }


    public GameMode getGameMode() {
        return gameMode;
    }


    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public int getLevel() {
        return level;
    }



    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public String getWinner() {
        return winner;
    }

    public int getPrize() {
        return prize;
    }

    @Override
    public String toString() {
        String result = "";
        result += winner + " won a game from " + looser;
        result += " and won " + prize + " prize at " + time;
        return result;
    }
}
