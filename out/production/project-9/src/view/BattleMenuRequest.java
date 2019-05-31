package view;

import model.enumerations.BattleMenuErrorType;
import model.enumerations.BattleMenuRequestType;

import java.util.Scanner;

public class BattleMenuRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String SINGLE_PLAYER = "enter single player";
    private static final String MULTI_PLAYER = "enter multi player";
    private static final String STORY = "enter story game";
    private static final String CUSTOM_GAME = "enter custom game";
    private static final String START_GAME = "start game";
    private static final String ENTER = "enter";
    private static final String EXIT = "exit";
    private String deckName;
    private String mode;
    private String numberOfFlags;
    private String command;
    private static String userName;
    private int modeInt;
    private int numberOfFlagsInt;

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public BattleMenuRequestType getTypeInSingleMultiPlayerMenu() {
        if (command.length() >= 5 && command.substring(0, 5).matches(ENTER) && command.split(" ").length == 3) {
            if (command.matches(SINGLE_PLAYER))
                return BattleMenuRequestType.SINGLE_PLAYER;
            else if (command.matches(MULTI_PLAYER))
                return BattleMenuRequestType.MUTLI_PLAYER;
        } else if (command.matches(EXIT)) {
            return BattleMenuRequestType.EXIT;
        } else {
            return null; // invalid command
        }
        return null;
    }

    public BattleMenuRequestType getTypeSinglePlayerMenu() {
        if (command.length() >= 5 && command.substring(0, 5).matches(ENTER) && command.split(" ").length == 3) {
            if (command.matches(CUSTOM_GAME))
                return BattleMenuRequestType.CUSTOM_GAME;
            else if (command.matches(STORY))
                return BattleMenuRequestType.STORY_GAME;
            else
                return null;
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
        String com = "";
        if (commands.length >= 2)
            com = commands[0] + " " + commands[1];
        if (com.matches(START_GAME) && commands.length >= 4) {
            deckName = commands[2];
            mode = commands[3];
            if (mode.equals("3") && commands.length >= 5)
                numberOfFlags = commands[4];
            if (mode.matches("[1-3]"))
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
            userName = command.split(" ")[2];
            return BattleMenuRequestType.SELECT_USER;
        } else if (command.split(" ").length >= 4
                && command.substring(0, 22).matches("start multiplayer game")) {
            mode = command.split(" ")[3];
            if (mode.equals("3")) {
                if (command.split(" ").length != 5)
                    return null;
                numberOfFlags = command.split(" ")[4];
            }
            try {
                modeInt = Integer.parseInt(mode);
                if (modeInt == 3)
                    numberOfFlagsInt = Integer.parseInt(numberOfFlags);
                else
                    numberOfFlagsInt = 0;
            } catch (NumberFormatException e) {
                return null;
            }
            return BattleMenuRequestType.START_GAME;
        } else if (command.matches(EXIT))
            return BattleMenuRequestType.EXIT;
        else
            return null;
    }

    public int getModeInt() {
        return modeInt;
    }

    public int getNumberOfFlagsInt() {
        return numberOfFlagsInt;
    }
}
