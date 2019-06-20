package model;


import com.google.gson.annotations.Expose;

import java.text.Format;
import java.util.Date;

public class BattleResult {

    private String winner;
    private String looser;
    private int prize;
    private String time;


    public BattleResult(Player winner, int prize, String time, String looser) {
        this.winner = winner.getName();
        this.prize = prize;
        this.time = time;
        this.looser = looser;
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

    @Override
    public String toString() {
        String result = "";
        result += winner + " won a game from " + looser;
        result += " and won " + prize + " prize at " + time;
        return result;
    }
}
