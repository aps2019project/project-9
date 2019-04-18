package view;

import model.enumerations.MainMenuErrorType;

public class MainMenuView {
    private static final MainMenuView MAIN_MENU_VIEW = new MainMenuView();

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        return MAIN_MENU_VIEW;
    }

    public void help(){
        System.out.println("1 . Collection\n2. Shop\n3 . Battle\n4 . Exit\n5 . Help");
    }

    public void printError(MainMenuErrorType errorType){
        System.out.println(errorType.getMessage());
    }
}
