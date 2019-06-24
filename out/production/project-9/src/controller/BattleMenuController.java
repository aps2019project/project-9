package controller;

import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.SinglePlayerBattle;
import view.BattleMenu;
import view.GraphicalInGameView;

import java.io.IOException;

public class BattleMenuController {
    private Account loggedInAccount;

    public BattleMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void main(Stage stage) {
        BattleMenu battleMenu = new BattleMenu(loggedInAccount, this);
        battleMenu.start(stage);
    }

    public void startStoryModeGame(int level,Stage stage) {
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(level, loggedInAccount);
        singlePlayerBattle.startBattle();
        try {
            new GraphicalInGameView().showGame(stage,singlePlayerBattle,loggedInAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startCustomGame(String deckName, int mode, int numberOfFlags,Stage stage) {
        Deck customOpponentDeck = loggedInAccount.findDeckByName(deckName);
        startSinglePlayer(mode, customOpponentDeck, numberOfFlags,stage);
    }

    private void startSinglePlayer(int mode, Deck customOpponentDeck, int numberOfFlags,Stage stage) {
        if (mode != 3)
            numberOfFlags = 0;
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(mode, customOpponentDeck, loggedInAccount
                , numberOfFlags);
        singlePlayerBattle.startBattle();
        try {
            new GraphicalInGameView().showGame(stage,singlePlayerBattle,loggedInAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
