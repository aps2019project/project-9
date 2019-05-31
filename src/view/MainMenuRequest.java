package view;

import model.enumerations.MainMenuRequestType;

public class MainMenuRequest {
    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public MainMenuRequestType getType() {
        switch (command) {
            case "battle":
                return MainMenuRequestType.BATTLE;
            case "shop":
                return MainMenuRequestType.SHOP;
            case "collection":
                return MainMenuRequestType.COLLECTION;
            case "exit":
                return MainMenuRequestType.EXIT;
            case "help":
                return MainMenuRequestType.HELP;
        }
        return null;
    }
}
