package view;

import controller.AccountController;
import controller.CollectionController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {                 //all start must have try catch
        /*AccountMenu a = AccountMenu.getInstance();
        AccountController accountController = new AccountController();
        ShopMenu s = ShopMenu.getInstance();
        CollectionMenu c = new CollectionMenu(new CollectionController(new Account("ali", "b")));
        try {
            c.start();
            //s.start(primaryStage, new Collection());
            //a.start(primaryStage, accountController);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*MainMenuController mainMenuController = MainMenuController.getInstance(new Account("m","m"));
        mainMenuController.start(primaryStage);*/
        try {
            Account c = new Account("n","n");
            SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(1, c);
            singlePlayerBattle.startBattle();
            new GraphicalInGameView().showGame(primaryStage,singlePlayerBattle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*try {
            new CustomCardMenu(new Account("m","n")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        /*try {
            new GraphicalViewResource().start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
