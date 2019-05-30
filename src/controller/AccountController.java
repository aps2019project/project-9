package controller;

import javafx.stage.Stage;
import model.Account;
import model.enumerations.AccountErrorType;
import model.enumerations.AccountRequestType;
import view.AccountMenu2;
import view.AccountRequest;
import view.AccountView;

public class AccountController {
    private AccountView accountView = AccountView.getInstance();
    private AccountMenu2 accountMenu = new AccountMenu2();

    public void main() {
        accountMenu.start(new Stage());
        AccountRequest request = new AccountRequest();

        switch (request.getType()) {
            case CREATE_ACCOUNT:
                createAccount(request);
                break;
            case LOGIN:
                login(request);
                break;
            case SHOW_LEADERBOARDS:
                showLeaderBoards();
                break;
            case HELP:
                break;
        }
    }

    private void login(AccountRequest request) {
        if (Account.isUserNameToken(request.getUserName())) {
            accountView.printError(AccountErrorType.ENTER_PASSWORD);
            request.getNewCommand();
            if (Account.isPassWordValid(request.getUserName(), request.getCommand())) {
                goNextMenu(Account.findAccount(request.getUserName()));
            } else
                accountView.printError(AccountErrorType.INVALID_PASSWORD);
        } else
            accountView.printError(AccountErrorType.INVALID_USERNAME);
    }

    private void createAccount(AccountRequest request) {
        if (isUserNameValid(request.getUserName(), request)) {
            accountView.printError(AccountErrorType.ENTER_PASSWORD);
            //request.getNewCommand();
            Account newAccount = new Account(request.getUserName(), request.getCommand());
            goNextMenu(newAccount);
        }
    }

    private boolean isUserNameValid(String userName, AccountRequest request) {
        if (Account.isUserNameToken(userName)) {
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountView.printError(request.getErrorType());
            return false;
        }
        return true;
    }

    private void showLeaderBoards() {
        accountView.showLeaderBoards();
    }

    private void goNextMenu(Account loggedInAccount) {
        MainMenuController mainMenuController = new MainMenuController(loggedInAccount);
        mainMenuController.main();
    }

}
