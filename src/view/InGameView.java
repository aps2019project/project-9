package view;

import model.Battle;
import model.Player;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.InGameErrorType;
import model.items.Collectible;
import model.items.Flag;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;

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
        System.out.println("First Player Hero Health :");
        System.out.println(battle.getFirstPlayer().getHero().getHP());
        System.out.println("Second Player Hero Health :");
        System.out.println(battle.getSecondPlayer().getHero().getHP());
    }

    private void printOneFlagInfo(Battle battle) {
        System.out.println("The Flag Position : [ x , y ]");
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getX());
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getY());
        System.out.println("The Player Owning Flag :");
        System.out.println(battle.getPlayGround().getFlag().getOwningPlayer().getName());
        System.out.println("The minion owning the Flag : ");
        System.out.println(battle.getPlayGround().getFlag().getOwningMinion().getMinionName());
    }

    private void printMoreFlagsInfo(Battle battle) {
        int counter = 1;
        for (Flag flag : battle.getPlayGround().getFlags()) {
            System.out.printf("%s ( Minion ) Has A Flag From Player %s\n",
                    flag.getOwningMinion().getMinionName(), flag.getOwningPlayer().getName());
        }
    }

    public void showMinions(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            System.out.printf("%s : %s, health: %d, location: %d , %d ,power : %d\n",
                    minion.getCardID(), minion.getMinionName(), minion.getHP(), minion.getCell().getX()
                    , minion.getCell().getY(), minion.getAP());
        }
    }

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
        System.out.printf("%s moved to %d %d\n", card.getCardID(), x, y);
    }

    public void cardCantAttack(Card card) {
        System.out.printf("Card with %s can't attack\n", card.getCardID());
    }

    public void cardInserted(Card card, int x, int y) {
        System.out.printf("%s with %s inserted to %d %d\n", card.getName(), card.getCardID(), x, y);
    }

    public void showCollectibles(Player player, boolean isForAll) {
        if (isForAll) {
            for (Collectible collectedItem : player.getCollectedItems()) {
                System.out.printf("ItemName : %s ItemID : %s\n", collectedItem.getName(), collectedItem.getItemID());
            }
        } else {
            System.out.printf("ItemName : %s ItemId : %s", player.getSelectedCollectableItem().getName()
                    , player.getSelectedCollectableItem().getItemID());
        }
    }

    public void showCardName(Card card){
        System.out.println(card.getName());
    }
    public void help(){
        //
    }

}
