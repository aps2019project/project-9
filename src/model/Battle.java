package model;

import model.enumerations.GameMode;

public class Battle {
    protected int turn;
    protected PlayGround playGround;
    protected GameMode gameMode;
    protected int whoseTurn;
    protected Player firstPlayer;
    protected Player secondPlayer;
    protected int numberOfFlags;
    public void nextTurn(){

    }
    public void endBattle(){

    }
    public PlayGround getPlayGround(){return playGround;}
    public void checkWinner(){}
    public void assignMana(Player player, int number){
        player.addMana(number);
    }
    public boolean hasValidDeck(Player player){
        return player.getDeck().isValid();
    }
    public void setGameMode(GameMode mode){
        this.gameMode = mode;
    }
    public void setNumberOfFlags(int number){
        numberOfFlags = number;
    }
    public int getTurn(){return turn;}

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
