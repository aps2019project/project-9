package controller;

import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Account;
import model.Shop;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.items.Item;
import view.ShopMenu;

public class ShopController {
    private Account loggedInAccount;
    private Shop shop = Shop.getInstance();
    private ShopMenu shopMenu = ShopMenu.getInstance();

    public ShopController(Account loggedInAccount) {
        this.loggedInAccount = loggedInAccount;
    }

    public void start() {
        try {
            shopMenu.start(new Stage(), loggedInAccount.getCollection(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchCollection(String itemOrCardName, TableView tableView) {
        CollectionController collectionController = new CollectionController(loggedInAccount);
        collectionController.search(itemOrCardName, tableView);
    }

    public void sell(String itemOrCardID) {
        Card currentCard = loggedInAccount.getCollection().searchCardByID(itemOrCardID);
        if (currentCard != null) {// it is card not item
            Shop.getInstance().sell(currentCard, loggedInAccount);
            shopMenu.printError(ShopErrorType.SOLD_SUCCESSFUL);
        } else {// it is item not card
            Item currentItem = loggedInAccount.getCollection().searchItemByID(itemOrCardID);
            Shop.getInstance().sell(currentItem, loggedInAccount);
            shopMenu.printError(ShopErrorType.SOLD_SUCCESSFUL);
        }
    }

    public void buy(String itemOrCardName) {
        if (shop.searchCardByName(itemOrCardName) != null) { // card not item
            Card currentCard = shop.searchCardByName(itemOrCardName);
            if (loggedInAccount.getMoney() < currentCard.getCost())
                shopMenu.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else {
                shop.buy(currentCard, loggedInAccount);
                shopMenu.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        } else {// item not card
            Item currentItem = shop.searchItemByName(itemOrCardName);
            if (loggedInAccount.getMoney() < currentItem.getCost())
                shopMenu.printError(ShopErrorType.NOT_ENOUGH_MONEY);
            else if (loggedInAccount.getCollection().getNumberOfItems() == 3) {
                shopMenu.printError(ShopErrorType.YOUR_COLLECTION_HAS_THREE_ITEMS);
            } else {
                shop.buy(currentItem, loggedInAccount);
                shopMenu.printError(ShopErrorType.BOUGHT_SUCCESSFUL);
            }
        }
    }

    public void searchShop(String itemOrCardName, TableView cardTable, TableView itemTable) {
        if (shop.searchCardByName(itemOrCardName) == null && shop.searchItemByName(itemOrCardName) == null)
            shopMenu.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else {
            if (shop.searchCardByName(itemOrCardName) != null) { // card
                shopMenu.showCardBuy(cardTable, itemTable, shop.searchCardByName(itemOrCardName));
            } else { // item
                shopMenu.showItemBuy(cardTable, itemTable, shop.searchItemByName(itemOrCardName));
            }
        }
    }

    public void searchCollection(String itemOrCardName, TableView cardTable, TableView itemTable) {
        if (shop.searchCardByName(itemOrCardName) == null && shop.searchItemByName(itemOrCardName) == null)
            shopMenu.printError(ShopErrorType.CARD_OR_ITEM_NOT_IN_SHOP);
        else {
            if (shop.searchCardByName(itemOrCardName) != null) { // card
                shopMenu.showCardSell(cardTable, itemTable, shop.searchCardByName(itemOrCardName));
            } else { // item
                shopMenu.showItemSell(cardTable, itemTable, shop.searchItemByName(itemOrCardName));
            }
        }
    }
}
