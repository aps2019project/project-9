package controller;

import model.Account;
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
            }
            if (request.getTypeInSingleMultiPlayerMenu() == BattleMenuRequestType.EXIT)
                isFinished = true;
            switch (request.getTypeInSingleMultiPlayerMenu()) {
                case MUTLI_PLAYER:
                    singlePlayerMenu();
                    break;
                case SINGLE_PLAYER:
                    multiPlayerMenu();
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
            switch (request.getTypeStoryMode()) {
                case FIRST_LEVEL:
                    // start story mode game
                case SECOND_LEVEL:
                    // ...
                case THIRD_LEVEL:
                    // ...
            }
        }
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
            else {
                startCustomGame(request.getDeckName(), request.getMode(), request.getNumberOfFlags());
            }
        }
    }

    private void startCustomGame(String deckName, String mode, String numberOfFlags) { // still remaining
        if (loggedInAccount.findDeckByName(deckName) == null)
            view.printError(BattleMenuErrorType.DECK_NAME_NOT_VALID);
        else if (!loggedInAccount.findDeckByName(deckName).isValid())
            view.printError(BattleMenuErrorType.DECK_NOT_VALID);
        else {
            if (mode.equals("3")) {

            } else if (mode.equals("2")) {

            } else if (mode.equals("1")) {

            }
        }
    }

    private void multiPlayerMenu() {
        while (true) {
            view.showUserNames();
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
            } else if (request.getTypeMultiPlayer() == BattleMenuRequestType.SELECT_USER) {
                if (isDeckNameValid(request.getUserName())) {
                    view.printError(BattleMenuErrorType.OPPONENT_SUCCESSFULLY);
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
