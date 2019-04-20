package view;

public class BattleMenuView {
    private static final BattleMenuView BATTLE_MENU_VIEW = new BattleMenuView();

    public static BattleMenuView getInstance(){
        return BATTLE_MENU_VIEW;
    }

    public void showSingleMultiPlayerMenu(){
        System.out.println("1 . Single Player");
        System.out.println("2 . Multi Player");
        System.out.println("To Enter An Option : Enter [ Option ]");
    }

}
