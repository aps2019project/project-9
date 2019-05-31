package view;

import model.enumerations.GraveYardRequestType;

import java.util.Scanner;

public class GraveYardRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String SHOW_INFO = "show info";
    private static final String SHOW_CARDS = "show cards";
    private String cardId;
    private String command;

    public String getCardId() {
        return cardId;
    }

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public GraveYardRequestType getType() {
        if (command.matches(SHOW_CARDS))
            return GraveYardRequestType.SHOW_CARDS;
        else if (command.split(" ").length == 3 && command.substring(0, 9).matches(SHOW_INFO)) {
            cardId = command.split(" ")[2];
            return GraveYardRequestType.SHOW_CARD_INFO;
        }
        else if (command.matches("exit"))
            return GraveYardRequestType.EXIT;
        else return null;
    }
}
