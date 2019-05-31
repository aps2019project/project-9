package view;

import model.enumerations.AccountErrorType;
import model.enumerations.AccountRequestType;

import java.security.PublicKey;
import java.util.Scanner;

public class AccountRequest {
    private Scanner scanner = new Scanner(System.in);
    private static final String CREATE_ACCOUNT = "create account";
    private static final String LOGIN = "login";
    private static final String SHOW_LEADERBOARD = "show leaderboard";
    private static final String HELP = "help";
    private static final String EXIT = "exit";
    private AccountErrorType errorType = null;
    private String command;
    private String userName;
    private String passWord;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public void setErrorType(AccountErrorType errorType) {
        this.errorType = errorType;
    }

    public AccountErrorType getErrorType() {
        return errorType;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void getNewCommand() {
        this.command = scanner.nextLine().toLowerCase();
    }

    public boolean isValid() {
        AccountRequestType requestType = getType();
        if (requestType == null) {
            return false;
        }
        return true;
    }

    public AccountRequestType getType() {
        if (command.length() >= 14 && command.substring(0, 14).matches(CREATE_ACCOUNT)
                && (command.split(" ").length == 3)) {
            userName = command.split(" ")[2];
            return AccountRequestType.CREATE_ACCOUNT;
        } else if (command.length() >= 5 && command.substring(0, 5).matches(LOGIN)
                && (command.split(" ").length == 2)) {
            userName = command.split(" ")[1];
            return AccountRequestType.LOGIN;
        } else if (command.length() >= 16 && command.substring(0, 16).matches(SHOW_LEADERBOARD) && (command.split(" ").length == 2))
            return AccountRequestType.SHOW_LEADERBOARDS;
        else if (command.length() >= 4 && command.substring(0, 4).matches(HELP))
            return AccountRequestType.HELP;
        else if (command.length() >= 4 && command.substring(0, 4).matches(EXIT))
            return AccountRequestType.EXIT;
        else
            return null;
    }

    public String getUserName() {
        return userName;
    }


    public String getPassWord() {
        return passWord;
    }
}
