package view;

import controller.AccountController;
import controller.BattleMenuController;
import controller.MainMenuController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Account;
import model.Collection;

import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {                 //all start must have try catch
        Account account = new Account("mamad","ali");
        BattleMenuController bc = new BattleMenuController(account);
        BattleMenu b = new BattleMenu(account,bc);
        AccountMenu a = AccountMenu.getInstance();
        CollectionMenu c = new CollectionMenu();
        GraveYard g = new GraveYard();
        MainMenu m = MainMenu.getInstance();
        ShopMenu s = ShopMenu.getInstance();
        AccountController ac = new AccountController();
        try {
            //bc.main(primaryStage);
            //ac.start(primaryStage);
            //b.start(primaryStage,account);
            //a.start(primaryStage);
            //a.start(primaryStage);
            //c.start(primaryStage);
            //m.start(primaryStage);
            //s.start(primaryStage,new Collection());
            // g.start(primaryStage,graveyard);
            //c.start(primaryStage);
            b.customGamePressed(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*try {
            new ShowReplay().showBattle(null,primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
