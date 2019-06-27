package model;

import model.enumerations.GameMode;

public class MultiPlayerBattle extends Battle {

    public MultiPlayerBattle(Account firstPlayer, String secondPlayerUserName, int mode, int flags) {
        Account secondAccount = Account.findAccount(secondPlayerUserName);
        if (mode == 1)
            gameMode = GameMode.HERO_KILL;
        else if (mode == 2)
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

    public MultiPlayerBattle(SinglePlayerBattle singlePlayerBattle, BattleResult battleResult) {
        this.gameMode = singlePlayerBattle.gameMode;
        turn = 0;
        //TODO for show replay
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
    }

}
