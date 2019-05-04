package view;

import model.Player;
import model.cards.Card;
import model.enumerations.GraveYardErrorType;

import java.util.ArrayList;

public class GraveYardView {
        private static final GraveYardView GRAVE_YARD_VIEW = new GraveYardView();

        private GraveYardView(){}
        public static GraveYardView getInstance(){return GRAVE_YARD_VIEW;}

        public void printError(GraveYardErrorType errorType){
            System.out.println(errorType.getMessage());
        }

        public void showCards(ArrayList<Card> cards){
            for (Card card : cards) {
                System.out.println(card.toString());
            }
        }

        public void showCardInfo(Card card){
            System.out.printf("card name : %s\ndescription : %s" , card.getName() , card.getDesc());
        }

        public void help(Player player){
            System.out.printf("<<-------GraveYard for Player : %s--------->>\n",player.getName());
            System.out.println("show info\nshow cards\nexit");
        }
}
