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
import model.items.Item;
import view.GraphicalInGameView;
import view.InGameRequest;
import view.InGameView;

import java.util.ArrayList;

public class InGameController {
    private Battle battle;
    private InGameView inGameView = InGameView.getInstance();
    private static ArrayList<InGameRequest> inGameRequests = new ArrayList<>();

    public InGameController(Battle battle) {
        this.battle = battle;
        battle.setInGameRequests(inGameRequests);
    }

    public void main(InGameRequest request, String userName) {
        if (battle.getCurrenPlayer().getName().equals(userName)) {
            InGameRequestType type = request.getRequestType();
            //
            if (request.getType() == InGameRequestType.ATTACK) {
                Card selectedCard = battle.getCurrenPlayer().getSelectedCard();
                inGameRequests.add(new InGameRequest("select " + selectedCard.getBattleID()));
            } else if (request.getType() == InGameRequestType.MOVE_TO) {
                Card selectedCard = battle.getCurrenPlayer().getSelectedCard();
                inGameRequests.add(new InGameRequest("select " + selectedCard.getBattleID()));
            } else if (request.getType() == InGameRequestType.USE) {
                Item item = battle.getCurrenPlayer().getSelectedCollectableItem();
                inGameRequests.add(new InGameRequest("select item " + item.getItemID()));
            }
            inGameRequests.add(request);
            //
            switch (type) {
                case SELECT_ITEM:
                    selectItem(request.getCollectibleID());
                    break;
                case SELECT_CARD:
                    selectCard(request.getCardID());
                    break;
                case EXIT:
                case USE_SPECIAL_POWER:
                    useSpecialPower(request.getX(), request.getY());
                    break;
                case ENTER_GRAVEYARD:
                case COMBO_ATTACK:
                    comboAttack(battle.getCurrenPlayer(), request.getComboCardIds(), request.getOpponentCardID());
                    break;
                case END_GAME:
                case MOVE_TO:
                    move(battle.getCurrenPlayer(), request.getX(), request.getY());
                    break;
                case INSERT:
                    insert(request.getCardName(), request.getX(), request.getY());
                    break;
                case ATTACK:
                    attack(battle.getCurrenPlayer(), request.getOpponentCardID());
                    break;
                case USE:
                    use(battle.getCurrenPlayer(), request.getX(), request.getY()); // for collectible item
                    break;
                case END_TURN:
                    battle.getCurrenPlayer().endTurn(inGameRequests);
                    break;
            }
        } else {
            GraphicalInGameView.showError(InGameErrorType.NOT_YOUR_TURN);
        }
    }

    public void main() {
        inGameView.printfError(InGameErrorType.GAME_STARTED);
        boolean isFinished = false;
        main:
        do {
            //try {
            InGameRequest request = new InGameRequest("");
            request.getNewCommand();
            if (inGameView.isFinished) {
                break;
            }
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
                    use(battle.getCurrenPlayer(), request.getX(), request.getY()); // for collectible item
                    break;
                case ATTACK:
                    attack(battle.getCurrenPlayer(), request.getOpponentCardID());
                    break;
                case INSERT:
                    // ID assigning ( battle ID )

                    insert(request.getCardName(), request.getX(), request.getY());
                    break;
                case MOVE_TO:
                    move(battle.getCurrenPlayer(), request.getX(), request.getY());
                    break;
                case END_GAME:
                    ////////////////////////////
                    break main;
                case END_TURN:
                    battle.getCurrenPlayer().endTurn(inGameRequests);
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
                case SELECT_CARD:
                    selectCard(request.getCardID());
                    break;
                case SELECT_ITEM:
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
                    graveYardController.start();
                    break;
                    /*GraveYardController graveYardController = new GraveYardController(battle.getCurrenPlayer());
                    graveYardController.main();
                    break;*/
                case SHOW_MY_MINIONS:
                    inGameView.showMinions(battle.getCurrenPlayer());
                    break;
                case SHOW_COLLECTIBLES:
                    inGameView.showCollectibles(battle.getCurrenPlayer());
                    break;
                case USE_SPECIAL_POWER:
                    useSpecialPower(request.getX(), request.getY());
                    break;
                case SHOW_OPPONENT_MINIONS:
                    inGameView.showMinions(battle.getCurrenPlayer().getOpponent());
                    break;

            }
            /*} catch (Exception e) {
                e.printStackTrace();
            }*/
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

    private int numberOfUseInBattle(Player player, Minion friendlyCard) {
        int ID = 1;
        for (Minion minion : player.getMinionsInPlayGround()) {
            if (minion.getMinionName().equals(friendlyCard.getMinionName()))
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
                GraphicalInGameView.cantAttack(currentPlayer.getSelectedCard().getBattleID());
                inGameRequests.remove(inGameRequests.get(inGameRequests.size() - 1));
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
                GraphicalInGameView.notEnoughMana();
                inGameRequests.remove(inGameRequests.get(inGameRequests.size() - 1));
            } else if (friendlyCard instanceof Minion) {
                if (!player.getCellsToInsertMinion().contains(cell)
                        || cell.hasCardOnIt()) {
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                } else {
                    // ID assigning
                    finalThingsInInsertingCard(friendlyCard, player, cell);

                }
            } else {
                if (!((Spell) friendlyCard).isValidTarget(cell))
                    inGameView.printfError(InGameErrorType.INVALID_TARGET);
                else {
                    // ID assigning
                    finalThingsInInsertingCard(friendlyCard, player, cell);
                }
            }
        }
    }

    public static void finalThingsInInsertingCard(Card friendlyCard, Player player, Cell cell) {
        if (friendlyCard instanceof Minion) {
            friendlyCard.setBattleID(player);
        }
        player.insertCard(friendlyCard, cell);

        //inGameView.cardInserted(friendlyCard, x, y);
    }

    private void move(Player player, int x, int y) {
        if (player.getSelectedCard() == null)
            inGameView.printfError(InGameErrorType.NO_SELECTED_CARD);
        else if (!((Minion) player.getSelectedCard()).isCanMove()) {
            GraphicalInGameView.showError(InGameErrorType.CAN_NOT_MOVE);
            inGameRequests.remove(inGameRequests.get(inGameRequests.size() - 1));
        } else {
            Minion selectedMinion = (Minion) player.getSelectedCard();
            Cell targetCell = battle.getPlayGround().getCell(x, y);
            if (!selectedMinion.isValidCellForMove(targetCell)) {
                inGameView.printfError(InGameErrorType.INVALID_TARGET);
            } else {
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
                GraphicalInGameView.cantAttack(friendlyMinion.getBattleID());
                inGameRequests.remove(inGameRequests.get(inGameRequests.size() - 1));
                return;
            }
        }
        // all can attack and all is in range
        player.comboAttack(targetCell, friendlyMinions);
    }

    private void useSpecialPower(int x, int y) { // not important where the cell is
        Cell targetCell = battle.getPlayGround().getCell(x, y);
        Player player = battle.getCurrenPlayer();
        if (player.getHero().getBuffs() == null && player.getHero().getCellAffect() == null) {
            GraphicalInGameView.showError(InGameErrorType.HERO_NOT_HAVE_SPELL);
        } else if (player.getMana() < player.getHero().getMP()) {
            GraphicalInGameView.notEnoughMana();
            inGameRequests.remove(inGameRequests.get(inGameRequests.size() - 1));
        } else if (!player.getHero().isSpellReady()) {
            inGameView.printfError(InGameErrorType.HERO_COOL_DOWN);
        } else {
            player.getHero().useSpecialPower(targetCell);
        }
    }

    public Battle getBattle() {
        return battle;
    }
}