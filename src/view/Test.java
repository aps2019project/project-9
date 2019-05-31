package view;

import javafx.application.Application;
import javafx.stage.Stage;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AccountMenu accountMenu  = AccountMenu.getInstance();
        ShopMenu s = new ShopMenu();
        try {
            s.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
