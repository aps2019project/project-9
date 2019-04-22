package model;

import model.enumerations.GameMode;

public class MultiPlayerBattle extends Battle {

    public MultiPlayerBattle(Account firstPlayer , String secondPlayerUserName, int mode , int flags){
        Account secondAccount = Account.findAccount(secondPlayerUserName);
        if(mode == 1)
            gameMode = GameMode.HERO_KILL;
        else if(mode == 2)
            gameMode = GameMode.ONE_FLAG;
        else
            gameMode = GameMode.FLAGS;
        turn  = 0;
        playGround = new PlayGround(gameMode);
        whoseTurn = 1;
        if(mode == 3)
            numberOfFlags = flags;
        this.firstPlayer = new Player(firstPlayer);
        this.secondPlayer = new Player(secondAccount);
    }

}
