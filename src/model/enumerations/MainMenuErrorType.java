package model.enumerations;


public enum MainMenuErrorType {
    INVALID_COMMAND("Invalid Command");
    private String message;
    MainMenuErrorType(String message){
        this.message = message;
    }
    public String getMessage(){return message;}
}
