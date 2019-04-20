package view;

import model.enumerations.BattleMenuErrorType;
import model.enumerations.BattleMenuRequestType;

import java.util.Scanner;

public class BattleMenuRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String SINGLE_PLAYER = "enter single player";
    private static final String MULTI_PLAYER = "enter multi player";
    private static final String STORY = "story";
    private static final String CUSTOM_GAME = "custom game";
    private static final String START_GAME = "start game";
    private static final String ENTER = "enter";
    private static final String EXIT = "exit";
    private String deckName;
    private String mode;
    private String numberOfFlags;
    private String command;
    private String userName;

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public BattleMenuRequestType getTypeInSingleMultiPlayerMenu() {
        if (command.substring(0, 5).matches(ENTER) && command.split(" ").length == 3) {
            if (command.matches(SINGLE_PLAYER))
                return BattleMenuRequestType.SINGLE_PLAYER;
            else
                return BattleMenuRequestType.MUTLI_PLAYER;
        } else if (command.matches(EXIT)) {
            return BattleMenuRequestType.EXIT;
        } else {
            return null; // invalid command
        }
    }

    public BattleMenuRequestType getTypeSinglePlayerMenu() {
        if (command.substring(0, 5).matches(ENTER) && command.split(" ").length == 3) {
            if (command.matches(CUSTOM_GAME))
                return BattleMenuRequestType.CUSTOM_GAME;
            else
                return BattleMenuRequestType.STORY_GAME;
        } else if (command.matches(EXIT)) {
            return BattleMenuRequestType.EXIT;
        } else {
            return null; // invalid command
        }
    }

    public BattleMenuRequestType getTypeStoryMode() {
        if (command.matches("1")) {
            return BattleMenuRequestType.FIRST_LEVEL;
        } else if (command.matches("2")) {
            return BattleMenuRequestType.SECOND_LEVEL;
        } else if (command.matches("3")) {
            return BattleMenuRequestType.THIRD_LEVEL;
        } else
            return null;
    }

    public BattleMenuRequestType getTypeOfCustomGame() {
        String[] commands = command.split(" ");
        if (commands.length == 5 && commands[0].equals("start") && commands[1].equals("game")) {
            numberOfFlags = commands[4];
            deckName = commands[2];
            mode = commands[3];
            if (numberOfFlags.matches("[0-9]{2}") && mode.matches("[1-3]"))
                return BattleMenuRequestType.START_GAME;
            else
                return null;
        } else if (command.matches(EXIT)) {
            return BattleMenuRequestType.EXIT;
        } else
            return null;
    }

    public String getDeckName() {
        return deckName;
    }

    public String getMode() {
        return mode;
    }

    public String getNumberOfFlags() {
        return numberOfFlags;
    }

    public String getUserName() {
        return userName;
    }

    public BattleMenuRequestType getTypeMultiPlayer() {
        // for select user [ username ] and Start MultiPlayer game
        if (command.split(" ").length == 3
                && command.substring(0, 11).matches("select user")) {
            userName = command.split("")[2];
            return BattleMenuRequestType.SELECT_USER;
        } else if (command.split(" ").length == 5
                && command.substring(0, 22).matches("start multiplayer game")) {
            mode = command.split(" ")[3];
            numberOfFlags = command.split(" ")[4];
            return BattleMenuRequestType.START_GAME;
        }else if(command.matches(EXIT))
            return BattleMenuRequestType.EXIT;
        else
            return null;
    }
}
