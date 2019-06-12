package view;

import controller.AccountController;
import controller.BattleMenuController;
import controller.CollectionController;
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
        CollectionMenu c = new CollectionMenu(new CollectionController(account));
        try {
            c.start(primaryStage);

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
