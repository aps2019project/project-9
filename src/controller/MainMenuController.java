package controller;

import javafx.stage.Stage;
import model.Account;
import model.enumerations.MainMenuErrorType;
import view.MainMenu;

public class MainMenuController {
    private MainMenu mainMenu = MainMenu.getInstance();

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    private Account loggedInAccount;

    MainMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void start(Stage stage) {
        mainMenu.start(stage,this);
    }

    public void goCollectionMenu(Account loggedInAccount) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.main();
    }

    public void goBattleMenu(Account loggedInAccount) {
        if (loggedInAccount.getMainDeck() == null || !loggedInAccount.getMainDeck().isValid()) {
            mainMenu.printError(MainMenuErrorType.SELECTED_DECK_INVALID);
        } else {
            BattleMenuController battleMenuController = new BattleMenuController(loggedInAccount);
            battleMenuController.main();
        }
    }

    public void goShopMenu(Account loggedInAccount, Stage stage) {
        ShopController shopController = new ShopController(loggedInAccount);
        shopController.start(stage);
    }
}
