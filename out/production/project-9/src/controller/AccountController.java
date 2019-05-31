package controller;

import model.Account;
import model.enumerations.AccountErrorType;
import view.AccountMenu;
import view.AccountRequest;

public class AccountController {
    private static AccountMenu accountMenu ;//= AccountMenuExpired.getInstance();


    public void main() {
        //accountMenu.start(args);
        AccountRequest request = accountMenu.getAccountRequest();

        switch (request.getType()) {
            case CREATE_ACCOUNT:
                createAccount(request);
                break;
            case LOGIN:
                login(request);
                break;
            default:
                break;
        }
    }

    private void login(AccountRequest request) {
        if (Account.isUserNameToken(request.getUserName())) {
            if (Account.isPassWordValid(request.getUserName(), request.getPassWord())) {
                goNextMenu(Account.findAccount(request.getUserName()));
            } else
                accountMenu.printError(AccountErrorType.INVALID_PASSWORD);
        } else
            accountMenu.printError(AccountErrorType.INVALID_USERNAME);
    }

    private void createAccount(AccountRequest request) {
        if (isUserNameValid(request.getUserName(), request)) {
            Account newAccount = new Account(request.getUserName(), request.getPassWord());
            goNextMenu(newAccount);
        }
    }

    private boolean isUserNameValid(String userName, AccountRequest request) {
        if (Account.isUserNameToken(userName)) {
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountMenu.printError(request.getErrorType());
            return false;
        }
        return true;
    }

    private void goNextMenu(Account loggedInAccount) {
        MainMenuController mainMenuController = new MainMenuController(loggedInAccount);
        //mainMenuController.start(argss);
    }

}
