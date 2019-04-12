package model;

import model.enumerations.GameMode;

public class Battle {
    private int turn;
    private PlayGround playGround;
    private GameMode gameMode;
    private int whoseTurn;
    public void nextTurn(){}
    public void endBattle(){}
    public PlayGround getPlayGround(){return playGround;}
    public void checkWinner(){}
    public void assignMana(Player player){}
    public boolean hasValidDeck(Player player){return false;}
    public void setGameMode(GameMode mode){}
    public void setNumberOfFlags(int number){}
    public int getTurn(){return turn;}
}
