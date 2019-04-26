package model.enumerations;

public enum InGameErrorType {
    INVALID_COMMAND("Invalid Command"),
    INVALID_CARD_ID("Invalid card ID"),
    INVALID_TARGET("Invalid Target"),
    UNAVAILABLE_FOR_ATTACK("opponent minion is unavailable for attack"),
    INVALID_CARD_NAME("Invalid Card Name"),
    NOT_HAVE_ENOUGH_MANA("You Don't Have Enough Mana"),
    EXIT_IN_THE_MIDDLE("Exited in The Middle Of Game ..."),
    NO_SELECTED_ITEM("No Selected Collectible Item"),
    NO_SELECTED_CARD("No Selected Card"),
    CAN_NOT_MOVE("This Card Can not Move");
    private String message;
    public String getMessage() {
        return message;
    }
    InGameErrorType(String message){
        this.message = message;
    }
}
