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
        System.out.println("<<---- Shop Menu ---->>");
        System.out.println("1 . show Collection\n2 . search [item name|card name]\n3 . search collection [item name|" +
                "card name]\n4 . buy [item name|card name]\n5 . sell [item name|card name]\n6 . show (print cards" +
                " and items that is available in shop)\n7 . exit\n8 . help");
        System.out.println("enter [option] ( except for Help and Exit )");
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
