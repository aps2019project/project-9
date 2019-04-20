package model.enumerations;


public enum MainMenuErrorType {
    INVALID_COMMAND("Invalid Command"),
    SELECTED_DECK_INVALID("Selected Deck Is Invalid");
    private String message;
    MainMenuErrorType(String message){
        this.message = message;
    }
    public String getMessage(){return message;}
}
