package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class BattleMenu {
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);
        Button b = new Button();

        FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\BattleMenu.fxml"));
        Parent parent = loader.load();
        Slider slider = new Slider();
        slider.setOnMouseClicked(mouseEvent -> new Alert(Alert.AlertType.INFORMATION, "Mamad"));


        Scene scene = new Scene(parent, 100, 100);
        stage.setScene(scene);

        stage.show();
    }
    @FXML
    public void singlePlayerPressed(ActionEvent event){
        new Alert(Alert.AlertType.INFORMATION,"salalm").showAndWait();
    }
}
