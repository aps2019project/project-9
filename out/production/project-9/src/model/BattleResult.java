package model;

import java.util.HashMap;

public class BattleResult {
    private Player winner;
    private int prize;
    private Battle battle;


    public BattleResult(Player winner, int prize, Battle battle) {
        this.battle = battle;
        this.winner = winner;
        this.prize = prize;
    }

    public String getWinner() {
        return winner.getName();
    }

    public int getPrize() {
        return prize;
    }
}
