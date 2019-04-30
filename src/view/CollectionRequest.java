package view;

import model.enumerations.CollectionErrorType;
import model.enumerations.CollectionRequestType;

import java.util.Scanner;

public class CollectionRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String EXIT = "exit";
    private static final String SHOW = "show";
    private static final String SEARCH = "search";
    private static final String SAVE = "save";
    private static final String CREATE_DECK = "create deck";
    private static final String DELETE_DECK = "delete deck";
    private static final String ADD = "add";
    private static final String REMOVE = "remove";
    private static final String VALIDATE = "validate deck";
    private static final String SELECT_DECK = "select deck";
    private static final String SHOW_ALL_DECKS = "show all decks";
    private static final String SHOW_DECK = "show deck";
    private static final String HELP = "help";
    private String command;
    private CollectionErrorType errorType;
    private String deckName;
    private String cardOrItamName;

    public String getCommand() {
        return command;
    }

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public CollectionErrorType getErrorType() {
        return errorType;
    }

    public boolean isValid() {
        CollectionRequestType requestType = getType();
        if (requestType == null) {
            return false;
        }
        if (requestType == CollectionRequestType.SAVE
                || requestType == CollectionRequestType.HELP
                || requestType == CollectionRequestType.SHOW
                || requestType == CollectionRequestType.EXIT)
            return true;
        switch (requestType) {
            case ADD:
                cardOrItamName = command.split(" ")[1];
                deckName = command.split(" ")[4];
                return checkAddSyntax();
            case REMOVE:
                cardOrItamName = command.split(" ")[1];
                deckName = command.split(" ")[4];
                return checkRemoveSyntax();
            case SEARCH:
                cardOrItamName = command.split(" ")[1];
                return (command.split(" ").length == 2);
            case SHOW_DECK:
                deckName = command.split(" ")[2];
                return (command.split(" ").length == 3);
            case CREATE_DECK:
                deckName = command.split(" ")[2];
                return (command.split(" ").length == 3);
            case DELETE_DECK:
                deckName = command.split(" ")[2];
                return (command.split(" ").length == 3);
            case SELECT_DECK:
                deckName = command.split(" ")[2];
                return (command.split(" ").length == 3);
            case VALIDATE_DECK:
                deckName = command.split(" ")[2];
                return (command.split(" ").length == 3);
            case SHOW_ALL_DECKS:
                return (command.split(" ").length == 3);
        }
        return true;
    }

    public CollectionRequestType getType() {
        if (command.split(" ").length == 1) {
            if (command.matches(EXIT))
                return CollectionRequestType.EXIT;
            else if (command.matches(HELP))
                return CollectionRequestType.HELP;
            else if (command.matches(SAVE))
                return CollectionRequestType.SAVE;
            else if (command.matches(SHOW))
                return CollectionRequestType.SHOW;
            else
                return null;
        } else if (command.length() >= 6 && command.substring(0, 6).matches(SEARCH)
                && command.split(" ").length == 2)
            return CollectionRequestType.SEARCH;
        else if (command.length() >= 11 && command.substring(0, 11).matches(CREATE_DECK)
        && command.split(" ").length == 3)
            return CollectionRequestType.CREATE_DECK;
        else if (command.length() >= 11 && command.substring(0, 11).matches(DELETE_DECK)
        && command.split(" ").length == 3)
            return CollectionRequestType.DELETE_DECK;
        else if (command.length() >= 3 && command.substring(0, 3).matches(ADD)
        && command.split(" ").length == 5)
            return CollectionRequestType.ADD;
        else if (command.length() >= 6 && command.substring(0, 6).matches(REMOVE)
        && command.split(" ").length == 5)
            return CollectionRequestType.REMOVE;
        else if (command.length() >= 13 && command.substring(0, 13).matches(VALIDATE)
        && command.split(" ").length == 3)
            return CollectionRequestType.VALIDATE_DECK;
        else if (command.length() >= 11 && command.substring(0, 11).matches(SELECT_DECK)
        && command.split(" ").length == 3)
            return CollectionRequestType.SELECT_DECK;
        else if (command.length() >= 14 && command.substring(0, 14).matches(SHOW_ALL_DECKS))
            return CollectionRequestType.SHOW_ALL_DECKS;
        else if (command.length() >= 9 && command.substring(0, 9).matches(SHOW_DECK)
        && command.split(" ").length == 3)
            return CollectionRequestType.SHOW_DECK;
        else
            return null;
    }

    public boolean checkAddSyntax() {
        String[] commands = command.trim().split(" ");
        return (commands.length == 5
                && commands[2].equals("to")
                && commands[3].equals("deck"));
    }

    public boolean checkRemoveSyntax() {
        String[] commands = command.trim().split(" ");
        return (commands.length == 5
                && commands[2].equals("from")
                && commands[3].equals("deck"));
    }

    public String getDeckName() {
        return deckName;
    }

    public String getCardOrItamName() {
        return cardOrItamName;
    }
}
