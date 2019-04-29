package controller;

import model.Account;
import model.enumerations.MainMenuErrorType;
import model.enumerations.MainMenuRequestType;
import view.MainMenuRequest;
import view.MainMenuView;

public class MainMenuController {
    private MainMenuView mainMenuView = MainMenuView.getInstance();
    private Account loggedInAccount;

    MainMenuController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void main() {
        boolean isFinished = false;
        do {
            mainMenuView.help();
            MainMenuRequest request = new MainMenuRequest();
            request.getNewCommand();
            if (request.getType() == MainMenuRequestType.EXIT) {
                isFinished = true;
            }
            if (!request.isValid()) {
                mainMenuView.printError(request.getErrorType());
                continue;
            }
            switch (request.getType()) {
                case COLLECTION:
                    goCollectionMenu(loggedInAccount);
                    break;
                case BATTLE:
                    goBattleMenu(loggedInAccount);
                    break;
                case SHOP:
                    goShopMenu(loggedInAccount);
                    break;
                case HELP:
                    break;
            }
        } while (!isFinished);

    }

    public void goCollectionMenu(Account loggedInAccount) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.main();
    }

    public void goBattleMenu(Account loggedInAccount) {
        // checking the validation of main deck
        if(loggedInAccount.getMainDeck() == null || !loggedInAccount.getMainDeck().isValid()) {
            mainMenuView.printError(MainMenuErrorType.SELECTED_DECK_INVALID);
        }else {
            BattleMenuController battleMenuController = new BattleMenuController(loggedInAccount);
            battleMenuController.main();
        }
    }

    public void goShopMenu(Account loggedInAccount) {
        ShopController shopController = new ShopController(loggedInAccount);
        shopController.main();
    }
}
