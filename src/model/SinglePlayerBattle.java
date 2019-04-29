package model;

import model.enumerations.GameMode;

public class SinglePlayerBattle extends Battle {


    public void doSecondPlayerAction() {
        secondPlayer.doAiAction();
    }

    public SinglePlayerBattle(int level, Account firstPlayerAccount) { // for story mode
        this.level = level;
        if (level == 1) {
            playGround = new PlayGround(GameMode.HERO_KILL);
            gameMode = GameMode.HERO_KILL;
            battlePrize = 500;
        }else if(level == 2){
            playGround = new PlayGround(GameMode.ONE_FLAG);
            gameMode = GameMode.ONE_FLAG;
            battlePrize = 1000;
        }else{
            playGround = new PlayGround(GameMode.FLAGS);
            gameMode = GameMode.FLAGS;
            numberOfFlags = 7;
            battlePrize = 1500;
        }
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        firstPlayer = new Player(firstPlayerAccount , this);
        secondPlayer = new Player(level , this); // computer
    }
    public SinglePlayerBattle(int mode , Deck deck , Account firstPlayerAccount , int numberOfFlags){ // for custom game
        switch (mode){
            case 1:
                playGround = new PlayGround(GameMode.HERO_KILL);
                gameMode = GameMode.HERO_KILL;
                break;
            case 2:
                playGround = new PlayGround(GameMode.ONE_FLAG);
                gameMode = GameMode.ONE_FLAG;
                break;
            case 3:
                playGround = new PlayGround(GameMode.FLAGS);
                gameMode = GameMode.FLAGS;
                this.numberOfFlags = numberOfFlags;
                break;
        }
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        firstPlayer = new Player(firstPlayerAccount,this);
        secondPlayer = new Player(deck,this);
        battlePrize = 1000;
        level = 0;
    }

}
