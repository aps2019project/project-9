package model.enumerations;

public enum BattleMenuErrorType {
    INVALID_COMMAND("Invalid Command"),
    SELECTED_DECK_FOR_SECOND_PLAYER_INVALID("Selected Deck For Second Player Is Invalid"),
    DECK_NOT_VALID("Selected Deck is not Valid"),
    YOUR_MAIN_DECK_NOT_VALID("Your Main Deck Is not Valid");

    private String message;
    BattleMenuErrorType(String message){
        this.message = message;
    }
    public String getMessage(){return message;}
}
