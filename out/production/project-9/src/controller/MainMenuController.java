package controller;

import javafx.stage.Stage;
import model.Account;
import model.enumerations.MainMenuErrorType;
import view.MainMenu;

public class MainMenuController {
    private MainMenu mainMenu = MainMenu.getInstance();
    private static MainMenuController mainMenuController = new MainMenuController();

    public Account getLoggedInAccount() {
        return loggedInAccount;
    }

    private Account loggedInAccount;

    private MainMenuController() {
    }

    public static MainMenuController getInstance(Account loggedInAccount) {
        mainMenuController.loggedInAccount = loggedInAccount;
        return mainMenuController;
    }


    public void start(Stage stage) {
        mainMenu.start(stage,loggedInAccount);
    }

    public void goCollectionMenu(Account loggedInAccount) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.main();
    }

    public void goBattleMenu() {
        if (loggedInAccount.getMainDeck() == null || !loggedInAccount.getMainDeck().isValid()) {
            mainMenu.printError(MainMenuErrorType.SELECTED_DECK_INVALID);
        } else {
            BattleMenuController battleMenuController = new BattleMenuController(loggedInAccount);
            battleMenuController.main(new Stage());
        }
    }

    public void goShopMenu(Account loggedInAccount) {
        ShopController shopController = new ShopController(loggedInAccount);
        shopController.start();
    }
}
