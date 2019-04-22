package view;

import model.enumerations.InGameErrorType;
import model.enumerations.InGameRequestType;

import javax.imageio.plugins.tiff.ExifInteroperabilityTagSet;
import java.util.ArrayList;
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
    private static final String SELECT_ITEM = "select";
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
    private ArrayList<String> myCardIds; // for combo attack
    private String command;

    public ArrayList<String> getMyCardIds() {
        return myCardIds;
    }

    public String getOpponentCardID() {
        return opponentCardID;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCollectibleID() {
        return collectibleID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCardID() {
        return cardID;
    }

    public InGameErrorType getErrorType() {
        return errorType;
    }

    public String getCommand() {
        return command;
    }

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public boolean isValid(){
        return getType() != null ;
    }

    public InGameRequestType getType() {
        if (command.matches(GAME_INFO))
            return InGameRequestType.GAME_INFO;
        else if (command.matches(SHOW_MY_MINION))
            return InGameRequestType.SHOW_MY_MINIONS;
        else if (command.matches(SHOW_OPPONENT_MINION))
            return InGameRequestType.SHOW_OPPONENT_MINIONS;
        else if (command.substring(0, 14).matches(SHOW_CARD_INFO)
                && command.split(" ").length == 4) {
            cardID = command.split(" ")[3];
            return InGameRequestType.SHOW_CARD_INFO;
        } else if (command.substring(0, 6).matches(SELECT)
                && command.split(" ").length == 2) {
            cardID = command.split(" ")[1];
            return InGameRequestType.SELCET_CARD;
        } else if (command.substring(0, 7).matches(MOVE)
                && command.split(" ").length == 4
                && checkMoveSyntax(command)) {
            x = Integer.parseInt(command.split(" ")[2]);
            y = Integer.parseInt(command.split(" ")[3]);
            return InGameRequestType.MOVE_TO;
        } else if (command.substring(0, 6).matches(ATTACK) && command.split(" ").length == 2) {
            opponentCardID = command.split(" ")[1];
            return InGameRequestType.ATTACK;
        } else if (command.substring(0, 12).matches(COMBO_ATTACK)) {
            getCommandOfComboAttack(command);
            return InGameRequestType.COMBO_ATTACK;
        } else if (command.substring(0, 17).matches(USE_SPECIAL_POWER)
                && command.split(" ").length == 5
                && command.matches("use special power [1-5] [1-9]")) {
            x = Integer.parseInt(command.split(" ")[3]);
            y = Integer.parseInt(command.split(" ")[4]);
            return InGameRequestType.USE_SPECIAL_POWER;
        }else if(command.matches(SHOW_HAND))
            return InGameRequestType.SHOW_HAND;
        else if(command.substring(0,6).matches(INSERT) && checkInsertCommand(command))
            return InGameRequestType.INSERT;
        else if (command.matches(END_TURN))
            return InGameRequestType.END_TUNN;
        else if (command.matches(SHOW_COLLETIBLES))
            return InGameRequestType.SHOW_COLLECTIBLES;
        else if(command.matches(SELECT_ITEM)){
            collectibleID = command.split(" ")[1];
            return InGameRequestType.SELCET_ITEM;
        }else if (command.matches(SHOW_INFO))
            return InGameRequestType.SHOW_INFO;
        else if(command.matches(USE_ITEM) && command.split(" ").length == 3
        && checkItemUsecommand(command)){
            x = Integer.parseInt(command.split(" ")[1]);
            y = Integer.parseInt(command.split(" ")[2]);
            return InGameRequestType.USE;
        }else if (command.matches(SHOW_NEXT_CARD))
            return InGameRequestType.SHOW_NEXT_CARD;
        else if(command.matches(ENTER_GRAVEYARD))
            return InGameRequestType.ENTER_GRAVEYARD;
        else if (command.matches(HELP))
            return InGameRequestType.HELP;
        else if(command.matches("exit"))
            return InGameRequestType.EXIT;
        else if (command.matches(SHOW_MENU))
            return InGameRequestType.SHOW_MENU;
        else
            return null;
    }

    private boolean checkMoveSyntax(String command) {
        return command.matches("move to [1-5] [1-9]");
    }

    private void getCommandOfComboAttack(String command) {
        String[] commands = command.split(" ");
        opponentCardID = commands[2];
        myCardIds = new ArrayList<>();
        for (int i = 3; i < commands.length; i++) {
            myCardIds.add(commands[i]);
        }
    }

    private boolean checkInsertCommand(String command){
        if (command.split(" ").length == 5
                && command.split(" ")[3].matches("[1-5]")
                && command.split(" ")[4].matches("[1-9]")){
            cardName = command.split(" ")[1];
            x = Integer.parseInt(command.split(" ")[3]);
            y = Integer.parseInt(command.split(" ")[4]);
            return true;
        }
        return false;
    }

    private boolean checkItemUsecommand(String command){
        return command.matches("use [1-5] [1-9]");
    }
}
