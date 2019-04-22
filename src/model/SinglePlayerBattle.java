package model;

import model.enumerations.GameMode;

public class SinglePlayerBattle extends Battle {


    public void doSecondPlayerAction() {
        secondPlayer.doAiAction();
    }

    public SinglePlayerBattle(int level, Account firstPlayerAccount) { // for story mode
        if (level == 1) {
            playGround = new PlayGround(GameMode.HERO_KILL);
            gameMode = GameMode.HERO_KILL;
        }else if(level == 2){
            playGround = new PlayGround(GameMode.ONE_FLAG);
            gameMode = GameMode.ONE_FLAG;
        }else{
            playGround = new PlayGround(GameMode.FLAGS);
            gameMode = GameMode.FLAGS;
        }
        turn = 0;
        whoseTurn = 1;
        firstPlayer = new Player(firstPlayerAccount);
        secondPlayer = new Player(level); // computer
    }
    public SinglePlayerBattle(int mode , Deck deck , Account firstPlayerAccount , int numberOfFlags){ // for custom game
        //
    }

}
