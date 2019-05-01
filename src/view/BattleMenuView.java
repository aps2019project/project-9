package view;

import model.Account;
import model.Deck;
import model.enumerations.BattleMenuErrorType;

public class BattleMenuView {
    private static final BattleMenuView BATTLE_MENU_VIEW = new BattleMenuView();
    private static final String FIRST_LEVEL = "1 . Level : 1, Hero : WHITE_DEEV, mode : 1";
    private static final String SECOND_LEVEL = "2 . Level : 2, Hero : ZAHAK, mode : 2";
    private static final String THIRD_LEVEL = "3 . Level : 3, Hero : ARASH, mode : 3";


    public static BattleMenuView getInstance() {
        return BATTLE_MENU_VIEW;
    }

    public void showSingleMultiPlayerMenu() {
        System.out.println("<<------Battle Menu----->>");
        System.out.println("1 . Single Player");
        System.out.println("2 . Multi Player");
        System.out.println("3 . Exit");
        System.out.println("To Enter An Option : Enter [ Option ]");
    }

    public void showSinglePlayerMenu() {
        System.out.println("1 . Story\n2 . Custom Game");
        System.out.println("to enter an option : Enter [ Option ]");
    }

    public void showStoryMenu() {
        // show the levels
        System.out.println(FIRST_LEVEL);
        System.out.println(SECOND_LEVEL);
        System.out.println(THIRD_LEVEL);
        System.out.println("Enter Option Number ...");
    }

    public void showCustomGameMenu(Account loggedInAccount) {
        System.out.println("Your Main Deck :");
        System.out.println(loggedInAccount.getMainDeck().toString());
        System.out.println("Your Other Decks :");
        for (Deck deck : loggedInAccount.getDecks()) {
            System.out.println(deck.toString());
        }
        System.out.println("To Start Enter This Format:\nStart game [deck name] [mode] [number of flags]");
    }

    public void printError(BattleMenuErrorType errorType) {
        System.out.println(errorType.getMessage());
    }

    public void showUserNames(Account loggedAccount) {
        int counter = 1;
        System.out.println("Available Accounts :");
        for (Account account : Account.getAccounts()) {
            if (!account.getUserName().equals(loggedAccount.getUserName()))
                System.out.println(counter++ + " . " + account.getUserName());
        }
        System.out.println("Enter : \nSelect user [ username ]");
    }

}
