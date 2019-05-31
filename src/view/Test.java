package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            AccountMenu.getInstance().checkTest(primaryStage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
