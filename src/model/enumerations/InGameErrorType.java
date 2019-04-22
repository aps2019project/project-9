package model.enumerations;

public enum InGameErrorType {
    INVALID_COMMAND("Invalid Command"),
    INVALID_CARD_ID("Invalid card ID"),
    INVALID_TARGET("Invalid Target"),
    UNAVAILABLE_FOR_ATTACK("opponent minion is unavailable for attack"),
    INVALID_CARD_NAME("Invalid Card Name"),
    NOT_HAVE_ENOUGH_MANA("You Don't Have Enough Mana");
    private String message;
    public String getMessage() {
        return message;
    }
    InGameErrorType(String message){
        this.message = message;
    }
}
