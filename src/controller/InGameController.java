package controller;


import model.Battle;
import model.Cell;
import model.Player;
import model.cards.Card;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.InGameErrorType;
import model.enumerations.InGameRequestType;
import model.items.Collectible;
import view.InGameRequest;
import view.InGameView;

public class InGameController {
    private Battle battle;
    private InGameView inGameView = InGameView.getInstance();

    public InGameController(Battle battle) {
        this.battle = battle;
    }

    public void main() {
        boolean isFinished = false;
        do {
            InGameRequest request = new InGameRequest();
            request.getNewCommand();
            if (request.getType() == InGameRequestType.EXIT) {
                inGameView.printfError(InGameErrorType.EXIT_IN_THE_MIDDLE);
                isFinished = true;
            }
            if (!request.isValid()) {
                inGameView.printfError(InGameErrorType.INVALID_COMMAND);
                continue;
            }
            switch (request.getType()) {
                case HELP:
                    inGameView.help(battle.getCurrenPlayer());
                    break;
                case USE:
                    use(battle.getCurrenPlayer(), request.getX(), request.getY());
                    break;
                case ATTACK:
                    attack(battle.getCurrenPlayer(), request.getOpponentCardID());
                    break;
                case INSERT:
                    // ID assigning ( battle ID )
                    battle.getCurrenPlayer().getSelectedCard().setBattleID(
                            battle.getCurrenPlayer().getName() + "_" + battle.getCurrenPlayer().getSelectedCard().getName()
                                    + "_" + numberOfUseInBattle(battle));
                    insert(request.getCardName(),request.getX(),request.getY());
                    break;
                case MOVE_TO:
                    /////////////////////////////////
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
        } while (!isFinished);
    }

    private void showCardInfo(String cardID) {
        Card card = battle.getCurrenPlayer().getDeck().getCardByID(cardID);
        if (card == null)
            inGameView.printfError(InGameErrorType.INVALID_CARD_ID);
        else {
            inGameView.showCardInfo(card);
        }
    }

    private void selectCard(String cardID) {
        //
    }

    private int numberOfUseInBattle(Battle battle) { // ali
        int ID = 0;
        for (Card key : battle.getCurrenPlayer().getGraveYard().getCards()) {
            if (key.getBattleID().split("_")[0].equals(battle.getCurrenPlayer().getName()) &&
                    key.getBattleID().split("_")[1].equals(battle.getCurrenPlayer().getSelectedCard().getName()))
                ID++;
        }
        for (Card key : battle.getCurrenPlayer().getMinionsInPlayGround()) {
            if (key.getBattleID().split("_")[0].equals(battle.getCurrenPlayer().getName()) &&
                    key.getBattleID().split("_")[1].equals(battle.getCurrenPlayer().getSelectedCard().getName()))
                ID++;
        }
        return ID;
    }

    private void use(Player player, int x, int y) {
        Cell cell = player.getBattle().getPlayGround().getCell(x, y);
        if (player.getSelectedCollectableItem() == null) {
            inGameView.printfError(InGameErrorType.NO_SELECTED_ITEM);
        } else {
            if (!((Collectible) player.getSelectedCollectableItem()).isValidCell(cell))
                inGameView.printfError(InGameErrorType.INVALID_TARGET);
            else
                ((Collectible) player.getSelectedCollectableItem()).useItem();
        }
    }

    private void attack(Player currentPlayer, String opponentCardId) {
        // opponentId is the battle id of the opponent card
        if (currentPlayer.getSelectedCard() == null) {
            inGameView.printfError(InGameErrorType.NO_SELECTED_CARD);
        } else if (currentPlayer.getOpponent().findMinionByIdInPlayGround(opponentCardId) == null) {
            inGameView.printfError(InGameErrorType.INVALID_CARD_ID);
        } else {
            Minion opponentMinion = currentPlayer.getOpponent().findMinionByIdInPlayGround(opponentCardId);
            if (!((Minion) currentPlayer.getSelectedCard()).isValidCell(opponentMinion.getCell())) {
                inGameView.printfError(InGameErrorType.UNAVAILABLE_FOR_ATTACK);
            } else if (!((Minion) currentPlayer.getSelectedCard()).isCanAttack()) {
                inGameView.cardCantAttack(currentPlayer.getSelectedCard());
            } else {
                ((Minion) currentPlayer.getSelectedCard()).attack(opponentMinion.getCell());
            }
        }
    }

    private void insert(String cardName, int x, int y) {
        if (battle.getCurrenPlayer().getHand().getCardByName(cardName) == null) {
            inGameView.printfError(InGameErrorType.INVALID_CARD_NAME);
        } else {
            Cell cell = battle.getPlayGround().getCell(x, y);
            Player player = battle.getCurrenPlayer();
            Card friendlyCard = battle.getCurrenPlayer().getHand().getCardByName(cardName);
            if (player.getMana() < friendlyCard.getMP()) {
                inGameView.printfError(InGameErrorType.NOT_HAVE_ENOUGH_MANA);
            } else if (friendlyCard instanceof Minion) {
                if (!player.getCellsToInsertMinion().contains(cell)
                        || !((Minion) friendlyCard).isValidCell(cell)) {
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                } else {
                    ((Minion) friendlyCard).putInMap(cell);
                    player.reduceMana(friendlyCard.getMP());
                }
            } else {
                if (!((Spell) friendlyCard).isValidTarget(cell))
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                else {
                    ((Spell)friendlyCard).castSpell(cell);
                    player.reduceMana(friendlyCard.getMP());
                }
            }
        }
    }

}
