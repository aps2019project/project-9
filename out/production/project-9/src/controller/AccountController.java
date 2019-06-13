package controller;

import javafx.stage.Stage;
import model.Account;
import model.enumerations.AccountErrorType;
import view.AccountMenu;
import view.AccountRequest;

import java.io.FileNotFoundException;

public class AccountController {
    private static AccountMenu accountMenu = AccountMenu.getInstance();


    public void start(Stage stage) throws FileNotFoundException {
        accountMenu.start(stage, this);
    }

    public void login(AccountRequest request) {
        Stage stage = new Stage();
        if (Account.isUserNameToken(request.getUserName())) {
            if (Account.isPassWordValid(request.getUserName(), request.getPassWord())) {
                goNextMenu(Account.findAccount(request.getUserName()),stage);
            } else
                accountMenu.printError(AccountErrorType.INVALID_PASSWORD);
        } else
            accountMenu.printError(AccountErrorType.INVALID_USERNAME);
    }

    public void createAccount(AccountRequest request) {
        if (isUserNameValid(request)) {
            Stage stage = new Stage();
            Account newAccount = new Account(request.getUserName(), request.getPassWord());
            goNextMenu(newAccount,stage);
        }
    }

    private boolean isUserNameValid(AccountRequest request) {
        if (Account.isUserNameToken(request.getUserName())) {
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountMenu.printError(request.getErrorType());
            return false;
        }
        return true;
    }

    private void goNextMenu(Account loggedInAccount, Stage stage) {
        MainMenuController mainMenuController = new MainMenuController(loggedInAccount);
        mainMenuController.start(stage);
    }

}
