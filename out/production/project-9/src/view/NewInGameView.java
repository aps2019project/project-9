package view;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Battle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class NewInGameView {

    public void showGame(Stage stage, Battle battle) throws IOException {
        InGameController inGameController = new InGameController(battle);
        Group group = new Group();
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        group.getChildren().add(parent);
        Scene scene = new Scene(group);
        stage.getIcons().add(new Image("src\\res\\icon.jpg"));
        //
        MediaView mediaView = getBackGroundMusic();
        group.getChildren().add(mediaView);
        Pane pane = ((Pane) parent.lookup("#cell00"));
        pane.getChildren().add(new ImageView(new Image("src/res/minions/2.png")));
        pane.setStyle("-fx-background-color: blue");
        Pane pane3 = ((Pane) parent.lookup("#cell33"));
        pane3.getChildren().add(new ImageView(new Image("src/res/minions/5.png")));
        pane3.setStyle("-fx-background-color: blue");
        Pane pane1 = ((Pane) parent.lookup("#cell11"));
        pane1.setStyle("-fx-background-color: blue");
        pane1.getChildren().add(new ImageView(new Image("src/res/minions/1.png")));
        Pane pane2 = ((Pane) parent.lookup("#hand0"));
        pane2.getChildren().add(new ImageView(new Image("src/res/minions/1.png")));
        //
        stage.setTitle("Battle");
        stage.setScene(scene);
        stage.show();
    }


    private MediaView getBackGroundMusic(){
        Media media = new Media(new File("src/res/inGameResource/music_playmode.m4a").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        return new MediaView(mediaPlayer);
    }
}
