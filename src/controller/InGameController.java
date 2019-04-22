package controller;


import model.Battle;
import model.cards.Card;
import model.enumerations.InGameErrorType;
import model.enumerations.InGameRequestType;
import view.InGameRequest;
import view.InGameView;

public class InGameController {
    private Battle battle;
    private InGameView inGameView = InGameView.getInstance();
    public InGameController(Battle battle){
        this.battle = battle;
    }
    public void main(){
        boolean isFinished = false;
        do {
            InGameRequest request = new InGameRequest();
            request.getNewCommand();
            if(request.getType()== InGameRequestType.EXIT)
                isFinished = true;
            if(!request.isValid()){
                inGameView.printfError(InGameErrorType.INVALID_COMMAND);
                continue;
            }
            switch (request.getType()){
                case HELP:
                    inGameView.help();
                    break;
                case USE:
                case ATTACK:
                case INSERT:
                case MOVE_TO:
                case END_GAME:
                case END_TUNN:
                case GAME_INFO:
                    inGameView.showGameInfo(battle);
                    break;
                case SHOW_HAND:
                case SHOW_INFO:
                case SHOW_MENU:
                case SELCET_CARD:

                case SELCET_ITEM:
                case COMBO_ATTACK:
                case SHOW_CARD_INFO:
                    showCardInfo(request.getCardID());
                    break;
                case SHOW_NEXT_CARD:
                case ENTER_GRAVEYARD:
                case SHOW_MY_MINIONS:
                    inGameView.showMinions(battle.getCurrenPlayer());
                    break;
                case SHOW_COLLECTIBLES:
                case USE_SPECIAL_POWER:
                case SHOW_OPPONENT_MINIONS:
                    inGameView.showMinions(battle.getCurrenPlayer().getOpponent());
                    break;
            }
        }while (!isFinished);
    }

    private void showCardInfo(String cardID){
        Card card = battle.getCurrenPlayer().getDeck().getCardByID(cardID);
        if(card == null)
            inGameView.printfError(InGameErrorType.INVALID_CARD_ID);
        else{
            inGameView.showCardInfo(card);
        }
    }
    private void selectCard(String cardID){

        battle.getCurrenPlayer().getMinionsInPlayGround().
    }
}
