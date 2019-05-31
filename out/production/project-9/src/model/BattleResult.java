package model;

public class BattleResult {
    private Player currentPlayer;
    private Player opponent;
    private Player winner;
    private int level;
    private int prize;

    public BattleResult(Player currentPlayer, Player opponent, Player winner, int level, int prize) {
        this.currentPlayer = currentPlayer;
        this.opponent = opponent;
        this.winner = winner;
        this.level = level;
        this.prize = prize;
    }

    public String getWinner() {
        return winner.getName();
    }

    public int getPrize() {
        return prize;
    }
}
