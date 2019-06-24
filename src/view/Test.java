package view;


import controller.AccountController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {                 //all start must have try catch
        AccountMenu a = AccountMenu.getInstance();
        try {
            a.start(primaryStage,new AccountController());
            //g.start(primaryStage);
            //a.start(primaryStage,new AccountController());
            //gc.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*MainMenuController mainMenuController = MainMenuController.getInstance(new Account("m","m"));
        mainMenuController.start(primaryStage);
        try {
            Account c = new Account("n", "n");
            SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(1, c);
            singlePlayerBattle.startBattle();
            new GraphicalInGameView().showGame(primaryStage, singlePlayerBattle, c);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new CustomCardMenu(new Account("m","n")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new InGameMethodsAndSource().start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GraphicalGraveYard graphicalGraveYard = new GraphicalGraveYard();
        graphicalGraveYard.start(new Account("m","m").getCollection());*/

    }
}
