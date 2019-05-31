package view;

import model.enumerations.ShopErrorType;
import model.enumerations.ShopRequestType;

import java.util.Scanner;

public class ShopRequest {
    Scanner scanner = new Scanner(System.in);
    private static final String EXIT = "exit";
    private static final String SHOW_COLLECTION = "show collection";
    private static final String SEARCH = "search";
    private static final String SEARCH_COLLECTION = "search collection";
    private static final String BUY = "buy";
    private static final String SELL = "sell";
    private static final String SHOW = "show";
    private static final String HELP = "help";
    private ShopErrorType errorType;
    private String command;
    private String itemOrCardName;
    private String itemOrCardID;

    public void getNewCommand() {
        command = scanner.nextLine().toLowerCase();
    }

    public ShopErrorType getErrorType() {
        return errorType;
    }

    public boolean isValid() {
        ShopRequestType requestType = getType();
        if (requestType == null) {
            errorType = ShopErrorType.INVALID_COMMAND;
            return false;
        } else
            return true;
    }

    public ShopRequestType getType() {
        if (command.split(" ").length == 1) {
            if (command.matches(EXIT))
                return ShopRequestType.EXIT;
            else if (command.matches(HELP))
                return ShopRequestType.HELP;
            else if (command.matches(SHOW))
                return ShopRequestType.SHOW;
            else
                return null;
        } else {
            if (command.length() >= 15 && command.substring(0, 15).matches(SHOW_COLLECTION) &&
                    command.split(" ").length == 2) {
                return ShopRequestType.SHOW_COLLECTION;
            } else if (command.length() >= 6 && command.substring(0, 6).matches(SEARCH)
                    && command.split(" ").length == 2) {
                itemOrCardName = command.split(" ")[1];
                return ShopRequestType.SEARCH;
            } else if (command.length() >= 17 && command.substring(0, 17).matches(SEARCH_COLLECTION)
                    && command.split(" ").length == 3) {
                itemOrCardName = command.split(" ")[2];
                return ShopRequestType.SEARCH_COLLECTION;
            } else if (command.length() >= 3 && command.substring(0, 3).matches(BUY)
                    && command.split(" ").length == 2) {
                itemOrCardName = command.split(" ")[1];
                return ShopRequestType.BUY;
            } else if (command.length() >= 4 && command.substring(0, 4).matches(SELL)
                    && command.split(" ").length == 2){
                itemOrCardID = command.split(" ")[1];
                return ShopRequestType.SELL;
            } else {
                return null;
            }
        }
    }

    public String getItemOrCardID() {
        return itemOrCardID;
    }

    public String getItemOrCardName() {
        return itemOrCardName;
    }
}
