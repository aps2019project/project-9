package view;

import controller.InGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Battle;

import java.io.IOException;
import java.net.URL;

public class NewInGameView {

    public void showGame(Stage stage , Battle battle) throws IOException {
        InGameController inGameController = new InGameController(battle);
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);

        stage.setScene(scene);
        stage.show();
    }
}
