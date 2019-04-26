package model;

import model.enumerations.GameMode;

public class Battle {
    protected static final Deck FIRST_LEVEL_DECK = new Deck("first level");
    protected static final Deck SECOND_LEVEL_DECK = new Deck("second level");
    protected static final Deck THIRD_LEVEL_DECK = new Deck("third level");

    protected int turn;
    protected PlayGround playGround;
    protected GameMode gameMode;
    protected int whoseTurn;
    protected Player firstPlayer;
    protected Player secondPlayer;
    protected int numberOfFlags;
    protected int battlePrize; // should be initialized at Constructor()

    public void startBattle(){
        // buffs and cellAffects turns remained should be equal to turnsActive
        // just minions and heros should be copied
    }

    public void nextTurn(){
        // fill hands
        // change whose turn
    }
    public void endBattle(Player winner){

    }
    public PlayGround getPlayGround(){return playGround;}
    public void checkWinner(){
        switch (gameMode){
            case HERO_KILL:
                if(firstPlayer.getHero().getHP() == 0){
                    endBattle(secondPlayer);
                }else if(secondPlayer.getHero().getHP() == 0){
                    endBattle(firstPlayer);
                }
                break;
            case ONE_FLAG:
                // get the turns that the flag is in the position of a player
                break;
            case FLAGS:
                if(firstPlayer.getFlagsAcheived().size() >= numberOfFlags/2){
                    endBattle(firstPlayer);
                }else if(secondPlayer.getFlagsAcheived().size() >= numberOfFlags/2){
                    endBattle(secondPlayer);
                }
                break;
        }
    }
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

    public GameMode getGameMode() {
        return gameMode;
    }
    public Player getCurrenPlayer(){
        if (whoseTurn == 1)
            return firstPlayer;
        else
            return secondPlayer;
    }
}
