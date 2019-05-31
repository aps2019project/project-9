package controller;

import model.Account;
import model.enumerations.MainMenuErrorType;
import view.MainMenu;
import view.MainMenuRequest;

public class MainMenuController {
    private MainMenu mainMenu = MainMenu.getInstance();
    private Account loggedInAccount;

    MainMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void main(String[] args) {
        mainMenu.main(args);
        MainMenuRequest request = mainMenu.getRequest();
        switch (request.getCommand()) {
            case "battle":
                goBattleMenu(loggedInAccount);
                break;
            case "shop":
                goShopMenu(loggedInAccount);
                break;
            case "collection":
                goCollectionMenu(loggedInAccount);
                break;
        }
    }

    private void goCollectionMenu(Account loggedInAccount) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.main();
    }

    private void goBattleMenu(Account loggedInAccount) {
        if (loggedInAccount.getMainDeck() == null || !loggedInAccount.getMainDeck().isValid()) {
            mainMenu.printError(MainMenuErrorType.SELECTED_DECK_INVALID);
        } else {
            BattleMenuController battleMenuController = new BattleMenuController(loggedInAccount);
            battleMenuController.main();
        }
    }

    private void goShopMenu(Account loggedInAccount) {
        ShopController shopController = new ShopController(loggedInAccount);
        shopController.main();
    }
}
