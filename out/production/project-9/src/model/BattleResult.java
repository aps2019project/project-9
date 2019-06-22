package model;



import model.enumerations.GameMode;
import view.InGameRequest;
import java.util.ArrayList;


public class BattleResult {

    private String winner;
    private String looser;
    private int prize;
    private String time;
    private ArrayList<InGameRequest> inGameRequests;
    private GameMode gameMode;
    private int numberOfFlags;
    private int level;

    private transient Deck deck;
    private boolean isSinglePlayer;


    public BattleResult(Player winner, int prize, String time, String looser
            , Battle battle) {
        this.winner = winner.getName();
        this.prize = prize;
        this.time = time;
        this.looser = looser;
        this.inGameRequests = battle.inGameRequests;
        gameMode = battle.gameMode;
        numberOfFlags = battle.numberOfFlags;
        level = battle.level;
        deck = battle.secondPlayer.getDeck();
        isSinglePlayer = battle instanceof SinglePlayerBattle;
    }

    /*public ArrayList<InGameRequest> getInGameRequests() {
        return inGameRequests;
    }*/

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
