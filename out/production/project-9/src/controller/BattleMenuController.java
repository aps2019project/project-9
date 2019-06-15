package controller;

import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.SinglePlayerBattle;
import view.BattleMenu;

public class BattleMenuController {
    private Account loggedInAccount;

    public BattleMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void main(Stage stage) {
        BattleMenu battleMenu = new BattleMenu(loggedInAccount, this);
        battleMenu.start(stage);
    }

    public void startStoryModeGame(int level) {
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(level, loggedInAccount);
        singlePlayerBattle.startBattle();
        InGameController inGameController = new InGameController(singlePlayerBattle);
        inGameController.main();
    }

    public void startCustomGame(String deckName, int mode, int numberOfFlags) {
        Deck customOpponentDeck = loggedInAccount.findDeckByName(deckName);
        startSinglePlayer(mode, customOpponentDeck, numberOfFlags);
    }

    private void startSinglePlayer(int mode, Deck customOpponentDeck, int numberOfFlags) {
        if (mode != 3)
            numberOfFlags = 0;
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(mode, customOpponentDeck, loggedInAccount
                , numberOfFlags);
        singlePlayerBattle.startBattle();
        InGameController inGameController = new InGameController(singlePlayerBattle);
        inGameController.main();
    }
}
