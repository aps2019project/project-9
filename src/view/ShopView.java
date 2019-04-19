package view;

import model.Collection;
import model.Shop;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.items.Item;

public class ShopView {
    private static final ShopView VIEW = new ShopView();

    public static ShopView getInstance(){return VIEW;}

    public void printError(ShopErrorType errorType){
        System.out.println(errorType.getMessage());
    }
    public void help(){

    }

    public void showCollection(Collection collection){

    }

    public void show(){
        // will be fixed
    }

    public void showCardID(Card card){
        System.out.println(card.getCardID());
    }
    public void showItemID(Item item){
        System.out.println(item.getItemID());
    }
}
