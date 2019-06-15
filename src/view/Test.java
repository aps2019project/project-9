package view;

import controller.*;
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
        AccountMenu a = AccountMenu.getInstance();
        Account account = new Account("ali", "b");
        AccountController accountController = new AccountController();
        ShopMenu s = ShopMenu.getInstance();
        CollectionMenu c = new CollectionMenu(new CollectionController(account));
        MainMenu m = MainMenu.getInstance();
        BattleMenu battleMenu = new BattleMenu(account,new BattleMenuController(account));
        Account account1 = new Account("b","b");
        try {
            //m.start(primaryStage,new Account("ali","n"));
            a.start(primaryStage, new AccountController());
            //s.start(primaryStage, new Collection());
            //a.start(primaryStage, accountController);
            //c.start();
            //battleMenu.start(primaryStage);
            //s.start(primaryStage,new Collection(), new ShopController(account));
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*MainMenuController mainMenuController = MainMenuController.getInstance(new Account("m","m"));
        mainMenuController.start(primaryStage);
        try {
            Account c = new Account("n","n");
            SinglePlayerBattle singlePlayerBattle = new SinglePlayerBattle(1, c);
            singlePlayerBattle.startBattle();
            new GraphicalInGameView().showGame(primaryStage,singlePlayerBattle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            new CustomCardMenu(new Account("m","n")).start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
