package view;

import model.enumerations.InGameErrorType;
import model.enumerations.InGameRequestType;

import java.util.Scanner;

public class InGameRequest {
    private Scanner scanner = new Scanner(System.in);
    private static final String GAME_INFO = "game info";
    private static final String SHOW_MY_MINION = "show my minions";
    private static final String SHOW_OPPONENT_MINION = "show opponent minions";
    private static final String SHOW_CARD_INFO = "show card info";
    private static final String SELECT = "select";
    private static final String MOVE = "move to";
    private static final String ATTACK = "attack";
    private static final String COMBO_ATTACK = "attack combo";
    private static final String USE_SPECIAL_POWER = "use special power";
    private static final String SHOW_HAND = "show hand";
    private static final String INSERT = "insert";
    private static final String END_TURN = "end turn";
    private static final String SHOW_COLLETIBLES = "show collectibles";
    private static final String SELECT_ITEM = "select item";
    private static final String SHOW_INFO = "show info"; // item
    private static final String USE_ITEM = "use";
    private static final String SHOW_NEXT_CARD = "show next card";
    private static final String ENTER_GRAVEYARD = "enter graveyard";
    private static final String HELP = "help";
    private static final String SHOW_MENU = "show menu";
    private static final String END_GAME = "end game";
    private InGameErrorType errorType;
    private String cardID;
    private int x;
    private int y;
    private String collectibleID;
    private String cardName;
    private String opponentCardID;
    private String[] myCardIds; // for combo attack
    private String command;

    public InGameErrorType getErrorType() {
        return errorType;
    }

    public String getCommand() {
        return command;
    }

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public InGameRequestType getType(){

    }
}
