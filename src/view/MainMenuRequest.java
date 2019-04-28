package view;

import model.enumerations.MainMenuErrorType;
import model.enumerations.MainMenuRequestType;

import java.util.Scanner;

public class MainMenuRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String ENTER = "enter";
    private static final String BATTLE = "battle";
    private static final String COLLECTION = "collection";
    private static final String SHOP = "shop";
    private static final String EXIT = "exit";
    private static final String HELP = "help";
    private String command;
    private MainMenuErrorType errorType;

    public String getCommand(){return command;}
    public void getNewCommand(){
        command = scanner.nextLine().toLowerCase();
    }

    public MainMenuErrorType getErrorType(){
        return errorType;
    }

    public boolean isValid(){
        MainMenuRequestType requestType = getType();
        if(requestType == null) {
            errorType = MainMenuErrorType.INVALID_COMMAND;
            return false;
        }
        else {
            return true;
        }
    }

    public MainMenuRequestType getType(){
        if(command.length() >= 5 && command.substring(0,5).matches(ENTER) && (command.split(" ").length == 2)){
            String[] commands = command.split(" ");
            if(commands[1].matches(BATTLE))
                return MainMenuRequestType.BATTLE;
            else if(commands[1].matches(SHOP))
                return MainMenuRequestType.SHOP;
            else if(commands[1].matches(COLLECTION))
                return MainMenuRequestType.COLLECTION;
            else
                return null;
        } else if (command.matches(EXIT))
            return MainMenuRequestType.EXIT;
        else if(command.matches(HELP))
            return MainMenuRequestType.HELP;
        return null;
    }
}
