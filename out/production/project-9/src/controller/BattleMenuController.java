package controller;

import model.Account;
import model.Deck;
import model.MultiPlayerBattle;
import model.SinglePlayerBattle;
import model.enumerations.BattleMenuErrorType;
import model.enumerations.BattleMenuRequestType;
import view.BattleMenuRequest;
import view.BattleMenuView;

public class BattleMenuController {
    private Account loggedInAccount;
    private BattleMenuView view = BattleMenuView.getInstance();

    public BattleMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void main() {
        boolean isFinished = false;
        do {
            view.showSingleMultiPlayerMenu();
            BattleMenuRequest request = new BattleMenuRequest();
            request.getNewCommand();
            if (request.getTypeInSingleMultiPlayerMenu() == null) {
                view.printError(BattleMenuErrorType.INVALID_COMMAND);
                continue;
            }
            if (request.getTypeInSingleMultiPlayerMenu() == BattleMenuRequestType.EXIT)
                isFinished = true;
            switch (request.getTypeInSingleMultiPlayerMenu()) {
                case MUTLI_PLAYER:
                    multiPlayerMenu();
                    break;
                case SINGLE_PLAYER:
                    singlePlayerMenu();
                    break;
            }

        } while (!isFinished);
    }

    private void singlePlayerMenu() {
        boolean isFinished = false;
        do {
            view.showSinglePlayerMenu();
            BattleMenuRequest request = new BattleMenuRequest();
            request.getNewCommand();
            if (request.getTypeSinglePlayerMenu() == BattleMenuRequestType.EXIT)
                isFinished = true;
            else if (request.getTypeSinglePlayerMenu() == null) {
                view.printError(BattleMenuErrorType.INVALID_COMMAND);
                continue;
            }
            switch (request.getTypeSinglePlayerMenu()) {
                case CUSTOM_GAME:
                    customGameMenu();
                    break;
                case STORY_GAME:
                    storyMenu();
                    break;
            }
        } while (!isFinished);
    }

    private void storyMenu() {
        while (true) {
            view.showStoryMenu();
            BattleMenuRequest request = new BattleMenuRequest();
            request.getNewCommand();
            if (request.getTypeStoryMode() == BattleMenuRequestType.EXIT)
                break;
            else if (request.getTypeStoryMode() == null)
                view.printError(BattleMenuErrorType.INVALID_COMMAND);
            else {
                switch (request.getTypeStoryMode()) {
                    case FIRST_LEVEL:
                        startStoryModeGame(1);
                        break;
                    case SECOND_LEVEL:
                        startStoryModeGame(2);
                        break;
                    case THIRD_LEVEL:
                        startStoryModeGame(3);
                        break;
                }
                return;
            }
        }
    }

    private void startStoryModeGame(int level) {
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(level, loggedInAccount);
        singlePlayerBattle.startBattle();
        InGameController inGameController = new InGameController(singlePlayerBattle);
        inGameController.main();
    }

    private void customGameMenu() {
        while (true) {
            view.showCustomGameMenu(loggedInAccount);
            BattleMenuRequest request = new BattleMenuRequest();
            request.getNewCommand();
            if (request.getTypeOfCustomGame() == null)
                view.printError(BattleMenuErrorType.INVALID_COMMAND);
            else if (request.getTypeOfCustomGame() == BattleMenuRequestType.EXIT)
                break;
            else { // START GAME
                startCustomGame(request.getDeckName(), request.getMode(), request.getNumberOfFlags());
            }
        }
    }

    private void startCustomGame(String deckName, String mode, String numberOfFlags) {
        if (loggedInAccount.findDeckByName(deckName) == null)
            view.printError(BattleMenuErrorType.DECK_NAME_NOT_VALID);
        else if (!loggedInAccount.getMainDeck().isValid())
            view.printError(BattleMenuErrorType.YOUR_MAIN_DECK_NOT_VALID);
        else if (!loggedInAccount.findDeckByName(deckName).isValid())
            view.printError(BattleMenuErrorType.DECK_NOT_VALID);
        else {
            Deck customOpponentDeck = loggedInAccount.findDeckByName(deckName);
            int modeInt = Integer.parseInt(mode);
            startSinglePlayer(modeInt, customOpponentDeck, numberOfFlags);
        }
    }

    private void startSinglePlayer(int mode, Deck customOpponentDeck, String numberOfFlags) {
        if (mode != 3)
            numberOfFlags = "0";
        SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(mode, customOpponentDeck, loggedInAccount
                , Integer.parseInt(numberOfFlags));
        singlePlayerBattle.startBattle();
        InGameController inGameController = new InGameController(singlePlayerBattle);
        inGameController.main();
    }

    private void multiPlayerMenu() {
        int counter = 0;
        while (true) {
            if (counter == 0)
                view.showUserNames(loggedInAccount);
            BattleMenuRequest request = new BattleMenuRequest();
            request.getNewCommand();
            if (request.getTypeMultiPlayer() == null)
                view.printError(BattleMenuErrorType.INVALID_COMMAND);
            else if (request.getTypeMultiPlayer() == BattleMenuRequestType.EXIT)
                break;
            else if (request.getTypeMultiPlayer() == BattleMenuRequestType.START_GAME) {
                // start multi player game
                MultiPlayerBattle multiPlayerBattle = new MultiPlayerBattle(loggedInAccount, request.getUserName()
                        , request.getModeInt(), request.getNumberOfFlagsInt());
                multiPlayerBattle.startBattle();
                InGameController inGameController = new InGameController(multiPlayerBattle);
                inGameController.main();
                break;
            } else if (request.getTypeMultiPlayer() == BattleMenuRequestType.SELECT_USER) {
                if (isDeckNameValid(request.getUserName())) {
                    view.printError(BattleMenuErrorType.OPPONENT_SUCCESSFULLY);
                    counter = 1;
                }
            }
        }
    }

    public boolean isDeckNameValid(String userName) {
        if (Account.findAccount(userName) == null) {
            view.printError(BattleMenuErrorType.INVALID_USERNAME);
            return false;
        } else if (!Account.findAccount(userName).getMainDeck().isValid()) {
            view.printError(BattleMenuErrorType.SELECTED_DECK_FOR_SECOND_PLAYER_INVALID);
            return false;
        }
        return true;
    }
}
