package view;

import model.Battle;
import model.enumerations.InGameErrorType;

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

    public void showGameInfo(Battle battle){
        switch (battle.getGameMode()){
            case HERO_KILL:
                printHeroKillGameInfo(battle);
                break;
            case ONE_FLAG:
            case FLAGS:
        }
    }
    private void printHeroKillGameInfo(Battle battle){
        System.out.println("First Player Hero Health :");
        System.out.println(battle.getFirstPlayer().getHero().getHP());
        System.out.println("Second Player Hero Health :");
        System.out.println(battle.getSecondPlayer().getHero().getHP());
    }
    private void printOneFlagInfo(Battle battle){
        System.out.println("The Flag Position : [ x , y ]");
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getX());
        System.out.println(battle.getPlayGround().getFlag().getCurrentCell().getY());
    }

}
