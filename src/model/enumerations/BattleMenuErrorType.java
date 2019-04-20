package model.enumerations;

public enum BattleMenuErrorType {
    INVALID_COMMAND("Invalid Command"),
    SELECTED_DECK_FOR_SECOND_PLAYER_INVALID("Selected Deck For Second Player Is Invalid"),
    DECK_NAME_NOT_VALID("deck name not valid"),
    DECK_NOT_VALID("Selected Deck is not Valid"),
    INVALID_USERNAME("Invalid UserName"),
    OPPONENT_SUCCESSFULLY("Your Opponent Successfully Selected ..." +
            "\nNow Enter : Start multiplayer game [mode] [number of flags]");
    private String message;
    BattleMenuErrorType(String message){
        this.message = message;
    }
    public String getMessage(){return message;}
}
