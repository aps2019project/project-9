package view;

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
                System.out.println("card name : " + card.getName() + " decs : " + card.getDesc());
            }
        }

        public void showCardInfo(Card card){
            System.out.printf("card name : %s\ndescription : %s" , card.getName() , card.getDesc());
        }
}
