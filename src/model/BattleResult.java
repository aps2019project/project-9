package model;


import com.google.gson.annotations.Expose;

import java.text.Format;
import java.util.Date;

public class BattleResult {
    @Expose
    private String winner;
    @Expose
    private int prize;
    private String time;


    public BattleResult(Player winner, int prize , String time) {
        this.winner = winner.getName();
        this.prize = prize;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getWinner() {
        return winner;
    }

    public int getPrize() {
        return prize;
    }
}
