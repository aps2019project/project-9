package view;

import model.Battle;
import model.BattleResult;
import model.Hand;
import model.Player;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.InGameErrorType;
import model.enumerations.MinionName;
import model.items.Collectible;
import model.items.Flag;

import java.util.ArrayList;


public class InGameView {
    private static final InGameView IN_GAME_VIEW = new InGameView();

    public static InGameView getInstance() {
        return IN_GAME_VIEW;
    }

    private InGameView() {
    }

    public void printfError(InGameErrorType errorType) {
        System.out.println(errorType.getMessage());
    }

    public void showGameInfo(Battle battle) {
        switch (battle.getGameMode()) {
            case HERO_KILL:
                printHeroKillGameInfo(battle);
                break;
            case ONE_FLAG:
                printOneFlagInfo(battle);
                break;
            case FLAGS:
                printMoreFlagsInfo(battle);
                break;
        }
    }

    private void printHeroKillGameInfo(Battle battle) {
        System.out.println("Your Mana : " + battle.getCurrenPlayer().getMana());
        System.out.println("First Player Hero Health :");
        System.out.println(battle.getFirstPlayer().getHero().getHP());
        System.out.println("Second Player Hero Health :");
        System.out.println(battle.getSecondPlayer().getHero().getHP());
    }

    private void printOneFlagInfo(Battle battle) {
        System.out.println("Your Mana : " + battle.getCurrenPlayer().getMana());
        System.out.println("The Flag Position : [ x , y ]");
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getX());
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getY());
        System.out.println("The Player Owning Flag :");
        System.out.println(battle.getPlayGround().getFlag().getOwningPlayer().getName());
        System.out.println("The minion owning the Flag : ");
        System.out.println(battle.getPlayGround().getFlag().getOwningMinion().getMinionName());
    }

    private void printMoreFlagsInfo(Battle battle) {
        System.out.println("Your Mana : " + battle.getCurrenPlayer().getMana());
        int counter = 1;
        for (Flag flag : battle.getPlayGround().getFlags()) {
            System.out.printf("%s ( Minion ) Has A Flag From Player %s\n",
                    flag.getOwningMinion().getMinionName(), flag.getOwningPlayer().getName());
        }
    }

    public void showMinions(Player player) {
        int[][] places = new int[5][9];
        for (Minion minion : player.getMinionsInPlayGround()) {
            if (minion != null) {
                System.out.printf("Battle ID : %s , name : %s, health: %d, location: %d , %d ,power : %d ,card ID : %d\n",
                        minion.getBattleID(), minion.getName(), minion.getHP(), minion.getCell().getX()
                        , minion.getCell().getY(), minion.getAP() , minion.getCardID());
                places[minion.getCell().getX()][minion.getCell().getY()]=1;
            }
        }
        //TODO adding special show for minions ( graphical )
        for (Minion minion : player.getOpponent().getMinionsInPlayGround()) {
            places[minion.getCell().getX()][minion.getCell().getY()] = -1;
        }
        System.out.println("F : Friendly Hero or Minion\nE : Enemy Hero or Minion");
        for (int i = 0; i < 5; i++) {
            System.out.print("|");
            for (int j = 0; j < 9; j++) {
                if(places[i][j] == 0){
                    System.out.print("_");
                }else if(places[i][j]==1){
                    System.out.print("F");
                }else{
                    System.out.print("E");
                }
                System.out.print("|");
            }
            System.out.println();
        }

    } // ones in the play ground

    public void showCardInfo(Card card) {
        if (card instanceof Minion) {
            if (card instanceof Hero) {
                Hero hero = (Hero) card;
                System.out.println(hero.toString());
            } else {
                Minion minion = (Minion) card;
                System.out.println(minion.toString());
            }
        } else if (card instanceof Spell) {
            Spell spell = (Spell) card;
            System.out.println(spell.toString());
        }
    }

    public void cardMoved(Card card, int x, int y) {
        System.out.printf("%s moved to %d %d\n", card.getBattleID(), x, y);
    }

    public void cardCantAttack(Card card) {
        System.out.printf("Card with %s can't attack\n", card.getBattleID());
    }

    public void cardInserted(Card card, int x, int y) {
        System.out.printf("%s with card ID : %s inserted to %d %d\n", card.getName(), card.getCardID(), x, y);
    }

    public void showCollectibles(Player player) {
        for (Collectible collectedItem : player.getCollectedItems()) {
            System.out.printf("ItemName : %s ItemID : %s\n", collectedItem.getName(), collectedItem.getItemID());
        }
    }

    public void showCardName(Card card) {
        System.out.println(card.getName());
    }

    public void help(Player player) {
        ArrayList<Minion> canMove = new ArrayList<>();
        ArrayList<Minion> opponentMinions = player.getOpponent().getMinionsInPlayGround();
        if (player.getHero().isCanMove())
            canMove.add(player.getHero());
        System.out.println("Cards In Hand :");
        for (Card card : player.getHand().getCards()) {
            System.out.printf("card name : %s\n", card.getName());
        }
        for (Minion minion : player.getMinionsInPlayGround()) {
            if (minion.isCanMove())
                canMove.add(minion);
        }
        System.out.println("Friendly Minions That Can Move :");
        for (Minion minion : canMove) {
            System.out.printf("Battle ID = %s , current cell:( %d , %d )\n", minion.getBattleID(),
                    minion.getCell().getX(), minion.getCell().getY());
        }
        System.out.println("All opponent minions :");
        for (Minion minion : opponentMinions) {
            System.out.printf("Battle ID = %s -> current cell : ( %d , %d )\n", minion.getBattleID(),
                    minion.getCell().getX(), minion.getCell().getY());
        }
    }

    public void endGameOutput(BattleResult result) {
        System.out.printf("Winner : %s\nThe Prize of winner is %d derick\n", result.getWinner(), result.getPrize());
        System.out.println("Enter :\nEND GAME");
    }

    public void showHand(Hand hand) {
        int counter = 1;
        System.out.println("Cards In Hand :");
        for (Card card : hand.getCards()) {
            if (card instanceof Spell) {
                System.out.printf("%d . %s ( Spell ) - cardID : %d\n", counter++, card.getName(), card.getCardID());
                System.out.println(card.toString());
            }
            else {
                System.out.printf("%d . %s ( Minion ) - cardID : %d\n", counter++, card.getName(), card.getCardID());
                System.out.println(card.toString());
            }
        }
        System.out.println("Next Card in Hand :");
        System.out.println(hand.getNext().getName() +
                ((hand.getNext() instanceof Spell) ? ("( Spell )") : ("( Minion )")));
    }

    public void showItemInfo(Player player) {
        if (player.getSelectedCollectableItem() == null)
            System.out.println("No Item Selected");
        else
            System.out.printf("Item Name : %s , Description : %s",
                    player.getSelectedCollectableItem().getDesc()
                    , player.getSelectedCollectableItem().getName());
    }

    public void showMenu() {
        System.out.print("Menu Commands :\n" +
                "Game info ( also showing manas )\n" + "show my minions\n"
                + "show opponent minions\n" + "show card info [card id]\n" +
                "select [card id]\n" +
                "move to x y\n" +
                "attack [opponent card id]\n" +
                "Attack combo [opponent card id] [my card id] [my card id] [...]\n" +
                "Use special power x y\n" +
                "show hand\n" +
                "insert [card name] in x y\n" +
                "end turn\n" +
                "show collectibles\n" +
                "select item [collectible id]\n" +
                "show info\n" +
                "use x y\n" +
                "show next card\n" +
                "enter graveyard\n" +
                "help\n" +
                "end game\n" +
                "exit\n" +
                "show menu\n");
    }
}
