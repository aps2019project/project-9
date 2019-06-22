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
    private String firstPlayer;
    private String secondPlayer;

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
        this.firstPlayer = battle.getFirstPlayer().getName();
        this.secondPlayer = battle.getSecondPlayer().getName();
    }

    public String getSecondPlayer() {
        return secondPlayer;
    }

    public ArrayList<InGameRequest> getInGameRequests() {
        return inGameRequests;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getLooser() {
        return looser;
    }

    public void setLooser(String looser) {
        this.looser = looser;
    }

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setInGameRequests(ArrayList<InGameRequest> inGameRequests) {
        this.inGameRequests = inGameRequests;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getNumberOfFlags() {
        return numberOfFlags;
    }

    public void setNumberOfFlags(int numberOfFlags) {
        this.numberOfFlags = numberOfFlags;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isSinglePlayer() {
        return isSinglePlayer;
    }

    public void setSinglePlayer(boolean singlePlayer) {
        isSinglePlayer = singlePlayer;
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
