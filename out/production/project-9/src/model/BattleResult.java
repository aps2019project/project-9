package model;


import com.google.gson.annotations.Expose;

public class BattleResult {
    @Expose
    private String winner;
    @Expose
    private int prize;
    private Battle battle;


    public BattleResult(Player winner, int prize, Battle battle) {
        this.battle = battle;
        this.winner = winner.getName();
        this.prize = prize;
    }

    public String getWinner() {
        return winner;
    }

    public int getPrize() {
        return prize;
    }
}
