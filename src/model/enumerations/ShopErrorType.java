package model.enumerations;

public enum ShopErrorType {
    INVALID_COMMAND("Invalid command"),
    CARD_OR_ITEM_NOT_IN_SHOP("Card or Item with this name does not exist in shop"),
    CARD_OR_ITEM_NOT_IN_COLLECTION("There is no such Card or Item in Collection"),
    NOT_ENOUGH_MONEY("You Don't Hava Enough Money :("),
    YOUR_COLLECTION_HAS_THREE_ITEMS("You have Three Items in your collection and can not have any more"),
    BOUGHT_SUCCESSFUL("Bought Successfully :)"),
    NOT_REMAINING("This Card Is Finished in Shop"),
    SOLD_SUCCESSFUL("Sold successfully :)");
    private String message;

    ShopErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
