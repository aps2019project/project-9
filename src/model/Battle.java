package model;

import model.enumerations.GameMode;

public class Battle {
    protected int turn;
    protected PlayGround playGround;
    protected GameMode gameMode;
    protected int whoseTurn;
    protected Player firstPlayer;
    protected Player secondPlayer;
    public void nextTurn(){}
    public void endBattle(){}
    public PlayGround getPlayGround(){return playGround;}
    public void checkWinner(){}
    public void assignMana(Player player){}
    public boolean hasValidDeck(Player player){return false;}
    public void setGameMode(GameMode mode){}
    public void setNumberOfFlags(int number){}
    public int getTurn(){return turn;}

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }
}
