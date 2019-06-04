package view;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Collection;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GraveYard g = new GraveYard();
        try {
            g.start(primaryStage, new Collection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
