package model;

import model.enumerations.GameMode;
import server.Account;

public class MultiPlayerBattle extends Battle {

    public MultiPlayerBattle(Account firstPlayer, Account secondAccount,GameMode mode, int flags) {
        if (mode == GameMode.HERO_KILL)
            gameMode = GameMode.HERO_KILL;
        else if (mode == GameMode.ONE_FLAG)
            gameMode = GameMode.ONE_FLAG;
        else {
            gameMode = GameMode.FLAGS;
            numberOfFlags = flags;
        }
        turn = 0;
        playGround = new PlayGround(gameMode, flags, true, true);
        whoseTurn = 1;
        this.firstPlayer = new Player(firstPlayer, this);
        this.secondPlayer = new Player(secondAccount, this);
        turn = 0;
        whoseTurn = 1;
        turnsToWon = 6;
        battlePrize = 1000;
        level = 0;
    }

    /*public MultiPlayerBattle(SinglePlayerBattle singlePlayerBattle, BattleResult battleResult) {
        this.gameMode = singlePlayerBattle.gameMode;
        turn = 0;

        playGround = new PlayGround(gameMode, singlePlayerBattle.numberOfFlags
                , false, false);
        whoseTurn = 1;
        turnsToWon = 6;
        battlePrize = singlePlayerBattle.battlePrize;
        level = singlePlayerBattle.level;
        firstPlayer = singlePlayerBattle.firstPlayer;
        secondPlayer = singlePlayerBattle.secondPlayer;
        firstPlayer = new Player(battleResult.getFirstPlayerDeck().getCopy(), this, battleResult.getFirstPlayer());
        secondPlayer = new Player(battleResult.getSecondPlayerDeck().getCopy(), this, battleResult.getSecondPlayer());
    }*/

}
