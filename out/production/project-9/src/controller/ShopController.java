package controller;

import javafx.stage.Stage;
import model.Account;
import model.Collection;
import model.Shop;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.enumerations.ShopRequestType;
import model.items.Item;
import view.ShopMenu;
import view.ShopRequest;
import view.ShopView;

public class ShopController {
    private ShopView view = ShopView.getInstance();
    private Account loggedInAccount;
    private Shop shop = Shop.getInstance();
    private ShopMenu shopMenu = ShopMenu.getInstance();

    public ShopController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void start(Stage stage) {
        try {
            shopMenu.start(stage, loggedInAccount.getCollection());
            ShopRequest request = new ShopRequest();
            switch (request.getType()) {
                case SEARCH_COLLECTION:
                    searchCollection(request.getItemOrCardName());
                    break;
               case SELL:
                    sell(request.getItemOrCardID());
                    break;
                case BUY:
                    buy(request.getItemOrCardName());
                    break;
                case SEARCH:
                    search(request.getItemOrCardName());
                    break;
                case SHOW:
                    view.show(shop);
                    break;
                case HELP:
                    view.help();
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void searchCollection(String itemOrCardName) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.search(itemOrCardName);
    }

    private void sell(String itemOrCardID) {
        if (loggedInAccount.getCollection().searchCardByID(itemOrCardID) == null
                && loggedInAccount.getCollection().searchItemByID(itemOrCardID) == null) {
            view.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_COLLECTION);
        } else {
            Card currentCard = loggedInAccount.getCollection().searchCardByID(itemOrCardID);
            if (currentCard != null) {// it is card not item
                Shop.getInstance().sell(currentCard, loggedInAccount);
                view.printError(ShopErrorType.SOLD_SUCCESSFUL);
            } else {// it is item not card
                Item currentItem = loggedInAccount.getCollection().searchItemByID(itemOrCardID);
                Shop.getInstance().sell(currentItem, loggedInAccount);
                view.printError(ShopErrorType.SOLD_SUCCESSFUL);
            }
        }
    }

    private void buy(String itemOrCardName) {
        if (shop.searchCardByName(itemOrCardName) == null
                && shop.searchItemByName(itemOrCardName) == null)
            view.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else if (shop.searchCardByName(itemOrCardName) != null) { // card not item
            Card currentCard = shop.searchCardByName(itemOrCardName);
            if (loggedInAccount.getMoney() < currentCard.getCost())
                view.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else {
                shop.buy(currentCard, loggedInAccount);
                view.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        } else {// item not card
            Item currentItem = shop.searchItemByName(itemOrCardName);
            if (loggedInAccount.getMoney() < currentItem.getCost())
                view.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else if (loggedInAccount.getCollection().getNumberOfItems() == 3) {
                view.printError(ShopErrorType.YOUR_COLLECTION_HAS_THREE_ITEMS);
            } else {
                shop.buy(currentItem, loggedInAccount);
                view.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        }
    }

    private void search(String itemOrCardName) {
        if (shop.searchCardByName(itemOrCardName) == null
                && shop.searchItemByName(itemOrCardName) == null)
            view.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else {
            if (shop.searchCardByName(itemOrCardName) != null) { // card not item
                view.showCardID(shop.searchCardByName(itemOrCardName));
            } else { // item not card
                view.showItemID(shop.searchItemByName(itemOrCardName));
            }
        }
    }
}
