package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Collection;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {                 //all start must have try catch
        ShopMenu s = ShopMenu.getInstance();
        GraveYard g = new GraveYard();
        AccountMenu a = AccountMenu.getInstance();
        try {
            a.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
