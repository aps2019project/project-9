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

import java.util.ArrayList;

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
                            battle.getCurrenPlayer().getName() + "_"
                                    + battle.getCurrenPlayer().getSelectedCard().getName()
                                    + "_" + numberOfUseInBattle(battle));
                    insert(request.getCardName(), request.getX(), request.getY());
                    break;
                case MOVE_TO:
                    move(battle.getCurrenPlayer(), request.getX(), request.getY());
                    break;
                case END_GAME:
                    // not complete .....
                    // inGameView.endGameOutput();
                    isFinished = true;
                    break;
                case END_TURN:
                    battle.getCurrenPlayer().endTurn();
                    break;
                case GAME_INFO:
                    inGameView.showGameInfo(battle);
                    break;
                case SHOW_HAND:
                    inGameView.showHand(battle.getCurrenPlayer().getHand());
                    break;
                case SHOW_INFO: // for selected collectible item information
                    inGameView.showItemInfo(battle.getCurrenPlayer());
                    break;
                case SHOW_MENU:
                    inGameView.showMenu();
                    break;
                case SELCET_CARD:
                    selectCard(request.getCardID());
                    break;
                case SELCET_ITEM:
                    selectItem(request.getCollectibleID());
                    break;
                case COMBO_ATTACK:
                    comboAttack(battle.getCurrenPlayer(), request.getComboCardIds(), request.getOpponentCardID());
                    break;
                case SHOW_CARD_INFO:
                    showCardInfo(request.getCardID());
                    break;
                case SHOW_NEXT_CARD:
                    inGameView.showCardInfo(battle.getCurrenPlayer().getHand().getNext());
                    break;
                case ENTER_GRAVEYARD:
                    GraveYardController graveYardController = new GraveYardController(battle.getCurrenPlayer());
                    graveYardController.main();
                    break;
                case SHOW_MY_MINIONS:
                    inGameView.showMinions(battle.getCurrenPlayer());
                    break;
                case SHOW_COLLECTIBLES:
                    inGameView.showCollectibles(battle.getCurrenPlayer());
                    break;
                case USE_SPECIAL_POWER:
                    useSpecialPower(request.getX(),request.getY());
                    break;
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

    private void selectCard(String battleID) {
        // from play ground
        Player player = battle.getCurrenPlayer();
        for (Minion minion : player.getMinionsInPlayGround()) {
            if (minion.getBattleID().equals(battleID)) {
                player.setSelectedCard(minion);
                return;
            }
        }
        inGameView.printfError(InGameErrorType.INVALID_CARD_ID);
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
            else {
                player.useCollectableItem();
            }
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
                currentPlayer.setSelectedCard(null);
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
                        || cell.hasCardOnIt()) {
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                } else {
                    player.insertCard(friendlyCard,cell);
                    inGameView.cardInserted(friendlyCard, x, y);
                }
            } else {
                if (!((Spell) friendlyCard).isValidTarget(cell))
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                else {
                    player.insertCard(friendlyCard,cell);
                    inGameView.cardInserted(friendlyCard, x, y);
                }
            }
        }
    }

    private void move(Player player, int x, int y) {
        if (player.getSelectedCard() == null)
            inGameView.printfError(InGameErrorType.NO_SELECTED_CARD);
        else if (!((Minion) player.getSelectedCard()).isCanMove())
            inGameView.printfError(InGameErrorType.CAN_NOT_MOVE);
        else {
            Minion selectedMinion = (Minion) player.getSelectedCard();
            Cell targetCell = battle.getPlayGround().getCell(x, y);
            if (!selectedMinion.isValidCellForMove(targetCell))
                inGameView.printfError(InGameErrorType.INVALID_TARGET);
            else {
                player.move(selectedMinion, targetCell);
                player.setSelectedCard(null);
                inGameView.cardMoved(selectedMinion, x, y);
            }
        }
    }

    private void selectItem(String itemID) {
        Player player = battle.getCurrenPlayer();
        int id;
        try {
            id = Integer.parseInt(itemID);
        } catch (NumberFormatException e) {
            inGameView.printfError(InGameErrorType.INVALID_COLLECTIBLE_ID);
            return;
        }
        for (Collectible collectedItem : player.getCollectedItems()) {
            if (collectedItem.getItemID() == id) {
                player.setSelectedCollectableItem(collectedItem);
                return;
            }
        }
        inGameView.printfError(InGameErrorType.INVALID_COLLECTIBLE_ID);
    }

    private void comboAttack(Player player, ArrayList<String> cards, String opponentCardId) {
        Player opponent = player.getOpponent();
        Minion opponentMinion = opponent.findMinionByIdInPlayGround(opponentCardId);
        if (opponentMinion == null) {
            inGameView.printfError(InGameErrorType.INVALID_OPPONENT_CARD_ID);
            return;
        }
        ArrayList<Minion> friendlyMinions = new ArrayList<>();
        for (String card : cards) {
            if (player.findMinionByIdInPlayGround(card) != null)
                friendlyMinions.add(player.findMinionByIdInPlayGround(card));
            else {
                inGameView.printfError(InGameErrorType.INVALID_CARD_ID);
                return;
            }
        }
        // opponent minion and friendly minions all are ok
        Cell targetCell = opponentMinion.getCell();
        for (Minion friendlyMinion : friendlyMinions) {
            if (!friendlyMinion.isValidCell(targetCell)) {
                inGameView.printfError(InGameErrorType.UNAVAILABLE_FOR_ATTACK);
                return;
            }
            if (!friendlyMinion.isCanAttack()) {
                inGameView.cardCantAttack(friendlyMinion);
                return;
            }
        }
        // all can attack and all is in range
        player.comboAttack(targetCell, friendlyMinions);
    }

    private void useSpecialPower(int x, int y) {
        Cell targetCell = battle.getPlayGround().getCell(x, y);
        Player player = battle.getCurrenPlayer();
        if (player.getHero().getHeroSpell() == null) {
            inGameView.printfError(InGameErrorType.HERO_NOT_HAVE_SPELL);
        } else if (player.getMana() < player.getHero().getHeroSpell().getMP()) {
            inGameView.printfError(InGameErrorType.NOT_HAVE_ENOUGH_MANA);
        } else if (!player.getHero().getHeroSpell().isValidTarget(targetCell)) {
            inGameView.printfError(InGameErrorType.INVALID_TARGET);
        } else if (!player.getHero().isSpellReady()) {
            inGameView.printfError(InGameErrorType.HERO_COOL_DOWN);
        } else {
            player.getHero().useSpecialPower(targetCell);
        }
    }
}
