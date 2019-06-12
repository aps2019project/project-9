package view;

import controller.AccountController;
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
        try {
           a.start(primaryStage,accountController);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        /*try {
            Account c = new Account("n","n");
            new GraphicalInGameView().showGame(primaryStage,new SinglePlayerBattle(1,c));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        new CustomCardMenu(new Account("m","n")).start();
    }
}
