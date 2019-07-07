package model;

import model.enumerations.GameMode;
import server.Account;

public class SinglePlayerBattle extends Battle {

    public SinglePlayerBattle(int level, Account firstPlayerAccount) { // for story mode
        this.level = level;
        if (level == 1) {
            playGround = new PlayGround(GameMode.HERO_KILL,
                    0,true,true);
            gameMode = GameMode.HERO_KILL;
            battlePrize = 500;
        } else if (level == 2) {
            playGround = new PlayGround(GameMode.ONE_FLAG,
                    1,true,true);
            gameMode = GameMode.ONE_FLAG;
            battlePrize = 1000;
        } else if (level == 3){
            playGround = new PlayGround(GameMode.FLAGS,
                    7,true,true);
            gameMode = GameMode.FLAGS;
            numberOfFlags = 7;
            battlePrize = 1500;
        } else{
            try {
                throw new Exception();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        firstPlayer = new Player(firstPlayerAccount, this);
        secondPlayer = new Player(level, this); // AI computer
    }

    public SinglePlayerBattle(int mode, Deck deck, Account firstPlayerAccount, int numberOfFlags) { // for custom game
        switch (mode) {
            case 1:
                playGround = new PlayGround(GameMode.HERO_KILL,
                        0,true,true);
                gameMode = GameMode.HERO_KILL;
                break;
            case 2:
                playGround = new PlayGround(GameMode.ONE_FLAG,
                        1,true,true);
                gameMode = GameMode.ONE_FLAG;
                break;
            case 3:
                playGround = new PlayGround(GameMode.FLAGS, numberOfFlags,
                        true,true);
                gameMode = GameMode.FLAGS;
                this.numberOfFlags = numberOfFlags;
                break;
        }
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        firstPlayer = new Player(firstPlayerAccount, this);
        secondPlayer = new Player(deck, this);
        battlePrize = 1000;
        level = 0;//
    }

}
