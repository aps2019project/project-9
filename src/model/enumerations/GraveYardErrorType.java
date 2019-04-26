package model.enumerations;

public enum GraveYardErrorType {
    INVALID_CARD_ID("Invalid Card ID"),
    INVALID_COMMAND("Invalid Command");
    private String message;
    GraveYardErrorType(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
