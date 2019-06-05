package view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Battle;

import java.io.IOException;
import java.net.URL;

public class ShowReplay {
    public void showBattle(Battle battle, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        Scene scene = new Scene(parent);
        Button button = ((Button) scene.lookup("#endTurn"));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setTextFill(Color.RED);
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
