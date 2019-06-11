package view;

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
        BattleMenu b = new BattleMenu();
        AccountMenu a =new AccountMenu();
        CollectionMenu c = new CollectionMenu();
        GraveYard g = new GraveYard();
        MainMenu m = MainMenu.getInstance();
        ShopMenu s = ShopMenu.getInstance();
        try {
            //b.customGamePresed(new Account("mamad","ali"));
            a.start(primaryStage);
            //a.start(primaryStage);
            //c.start(primaryStage);
            //m.start(primaryStage);
            //s.start(primaryStage,new Collection());
           // g.start(primaryStage,graveyard);
            //c.start(primaryStage);
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
