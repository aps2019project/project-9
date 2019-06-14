package model.enumerations;


public enum CollectionErrorType {
    DECK_NAME_EXISTS("A Deck With This Name Already Exists"),
    DECK_NAME_NOT_EXISTS("There is no Deck With This Name"),
    CARD_NOT_IN_COLLECTION("There is no such Card ( Item ) in your Collection"),
    ALREADY_IN_DECK("It is already In Deck"),
    DECK_FULL("Deck is Full"),
    DECK_HAS_A_HERO("Your Deck Has a Hero"),
    NOT_IN_DECK("Selected Card Is not in Deck"),
    DECK_NOT_VALID("Your Deck is not Valid"),
    DECK_IS_VALID("Deck is Valid"),
    DECK_CREATED("Deck Created Successfully ..."),
    DECK_ALREADY_HAS_AN_ITEM("There is already an item in the deck"),
    REMOVED_SUCCESSFULLY("Card Or Item removed Successfully ...");
    private String message;
    CollectionErrorType(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
