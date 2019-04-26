package controller;

import model.Player;
import model.cards.Card;
import model.enumerations.GraveYardErrorType;
import model.enumerations.GraveYardRequestType;
import view.GraveYardRequest;
import view.GraveYardView;

public class GraveYardController {
    private GraveYardView view = GraveYardView.getInstance();
    private Player player;

    public GraveYardController(Player player) {
        this.player = player;
    }

    public void main() {
        boolean isFinished = false;
        do {
            GraveYardRequest request = new GraveYardRequest();
            request.getNewCommand();
            if (request.getType() == null) {
                view.printError(GraveYardErrorType.INVALID_COMMAND);
                continue;
            } else if (request.getType() == GraveYardRequestType.EXIT) {
                isFinished = true;
            }
            switch (request.getType()) {
                case SHOW_CARD_INFO:
                    String cardId = request.getCardId();
                    Card card = player.getGraveYard().getCardByID(cardId);
                    if (card == null)
                        view.printError(GraveYardErrorType.INVALID_CARD_ID);
                    else
                        view.showCardInfo(card);
                    break;
                case SHOW_CARDS:
                    view.showCards(player.getGraveYard().getCards());
                    break;
            }
        } while (!isFinished);
    }
}
