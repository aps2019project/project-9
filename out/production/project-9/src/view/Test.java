package view;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AccountMenu accountMenu  = AccountMenu.getInstance();
        try {
            accountMenu.main(primaryStage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
