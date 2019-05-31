package model.enumerations;

public enum AccountErrorType {
    INVALID_COMMAND("Invalid Command"),
    INVALID_USERNAME("Invalid UserName"),
    INVALID_PASSWORD("Invalid PassWord"),
    USERNAME_EXISTS("This UserName Is Already Token"),
    ENTER_PASSWORD("Enter Your Password :");
    private String message;

    AccountErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
