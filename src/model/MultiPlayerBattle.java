package model;

import model.enumerations.GameMode;

public class MultiPlayerBattle extends Battle {

    public MultiPlayerBattle(Account firstPlayer , String secondPlayerUserName, int mode , int flags){
        Account secondAccount = Account.findAccount(secondPlayerUserName);
        if(mode == 1)
            gameMode = GameMode.HERO_KILL;
        else if(mode == 2)
            gameMode = GameMode.ONE_FLAG;
        else {
            gameMode = GameMode.FLAGS;
            numberOfFlags = flags;
        }
        turn  = 0;
        playGround = new PlayGround(gameMode,flags);
        whoseTurn = 1;
        this.firstPlayer = new Player(firstPlayer , this);
        this.secondPlayer = new Player(secondAccount , this);
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        battlePrize = 1000;
        level = 0;
    }

}
