package controller;

import model.Account;
import model.enumerations.AccountErrorType;
import model.enumerations.AccountRequestType;
import view.AccountRequest;
import view.AccountView;

public class AccountController {
    private AccountView accountView = AccountView.getInstance();

    public void main() {
        boolean isFinished = false;
        do {
            AccountRequest request = new AccountRequest();
            request.getNewCommand();
            if (request.getType() == AccountRequestType.EXIT) {
                isFinished = true;
            }
            if (!request.isValid()) {
                accountView.printError(AccountErrorType.INVALID_COMMAND);
                continue;
            }
            switch (request.getType()) {
                case CREATE_ACCOUNT:
                    createAccount(request.getUserName() , request);
                    break;
                case LOGIN:
                    login(request.getUserName() , request);
                    break;
                case SHOW_LEADERBOARDS:
                    showLeaderBoards();
                    break;
                case HELP:
                    accountView.showHelp();
                    break;
            }

        } while (!isFinished);
    }

    private void login(String userName , AccountRequest request) {
        if(Account.isUserNameToken(userName)){
            request.getNewCommand();
            if(Account.isPassWordValid(userName,request.getCommand())){
                goNextMenu(Account.findAccount(userName));
            }else
                accountView.printError(AccountErrorType.INVALID_PASSWORD);
        }else
            accountView.printError(AccountErrorType.INVALID_USERNAME);
    }

    private void createAccount(String userName , AccountRequest request) {
        if (isUserNameValid(userName,request)) {
            request.getNewCommand();
            Account newAccount = new Account(userName , request.getCommand());
            goNextMenu(newAccount);
        }
    }

    private boolean isUserNameValid(String userName , AccountRequest request){
        if(Account.isUserNameToken(userName)){
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountView.printError(request.getErrorType());
            return false;
        }
        return true;
    }

    private void showLeaderBoards() {
        accountView.showLeaderBoards();
    }

    private void goNextMenu(Account loggedInAccount){
        MainMenuController mainMenuController = new MainMenuController(loggedInAccount);
        mainMenuController.main();
    }

}
