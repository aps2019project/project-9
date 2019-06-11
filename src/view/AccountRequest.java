package view;

import model.enumerations.AccountErrorType;
import model.enumerations.AccountRequestType;

public class AccountRequest {
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

    public void setCommand(String command) {
        this.command = command;
    }

    public AccountRequestType getType() {
        if(command == null){
            return null;
        }
        else if (command.equals("create account")) {
            return AccountRequestType.CREATE_ACCOUNT;
        } else if (command.equals("login")) {
            return AccountRequestType.LOGIN;
        }
        return null;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
