package view;

import model.enumerations.AccountErrorType;

public class AccountRequest {
    private AccountErrorType errorType = null;
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

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }
}
