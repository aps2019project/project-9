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
        /*BattleMenu b = new BattleMenu();
        try {
            b.multiPlayerPreesed(new Account("ali","mamad"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            new NewInGameView().showGame(primaryStage,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
