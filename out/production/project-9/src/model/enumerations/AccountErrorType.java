package model.enumerations;

public enum AccountErrorType {
    INVALID_USERNAME("Invalid UserName"),
    INVALID_PASSWORD("Invalid PassWord"),
    USERNAME_EXISTS("This UserName Is Already Token");
    private String message;

    AccountErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
