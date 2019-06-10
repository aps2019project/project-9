package view;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Battle;
import model.Hand;
import model.Player;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class NewInGameView {
    private static HashMap<Integer[], Integer[]> cells = new HashMap<>();

    public void showGame(Stage stage, Battle battle) throws IOException {
        InGameController inGameController = new InGameController(battle);
        Group group = new Group();
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        group.getChildren().add(parent);
        Scene scene = new Scene(group);
        setCursor(scene);
        setExitBtn(parent);
        stage.getIcons().add(new Image("src\\res\\icon.jpg"));
        setNextTurnButton(parent);
        setCellsAction(parent);
        //
        Pane pane = ((Pane) parent.lookup("#cell33"));
        ImageView imageView = new ImageView(new Image("file:src/res/minions/3.gif"));

        setImageRotateForPlayGround(imageView);
        pane.getChildren().add(imageView);
        //
        MediaView mediaView = getBackGroundMusic();
        group.getChildren().add(mediaView);
        stage.setTitle("Duelyst");
        stage.setScene(scene);
        stage.show();
    }

    private static void updateHand(Hand hand){
        /*Pane pane = ((Pane) parent.lookup("#hand1"));
        ImageView imageView = new ImageView(new Image("file:src/res/heroes/1.gif"));
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        pane.getChildren().add(imageView);*/
    }

    private static void setCellsAction(Parent parent){
        String path = "file:src/res/inGameResource/cellBack1.png";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView imageView = new ImageView(new Image(path));
                Pane pane = ((Pane) parent.lookup("#cell" + i + j));
                pane.setOnMouseEntered(mouseEvent -> pane.getChildren().add(imageView));
                pane.setOnMouseExited(mouseEvent -> pane.getChildren().remove(imageView));
            }
        }
    }

    private static void setManas(Parent parent, Player player, boolean isFirst) {
        int mana = player.getMana();
        String num = (isFirst) ? ("1") : ("2");
        String path = "file:src/res/inGameResource/mana.png";
        for (int i = 1; i <= 9; i++) {
            if (i <= mana) {
                Pane pane2 = ((Pane) parent.lookup("#mana" + num + (i)));
                pane2.getChildren().add(new ImageView(new Image(path)));
            } else {
                Pane pane2 = ((Pane) parent.lookup("#mana" + num + (i)));
                pane2.getChildren().clear();
            }
        }
    }

    private static void setExitBtn(Parent parent) {
        Pane pane = ((Pane) parent.lookup("#closePane"));
        ImageView imageView = ((ImageView) pane.getChildren().get(0));
        pane.setOnMouseEntered(mouseEvent -> {
            imageView.setImage(new Image("file:src/res/inGameResource/close_button2.png"));
        });
        pane.setOnMouseExited(mouseEvent -> {
            imageView.setImage(new Image("file:src/res/inGameResource/button_close.png"));
        });
    }

    private static void setCursor(Scene scene) {
        scene.setCursor(new ImageCursor(new Image("src/res/inGameResource/cursor.png")));
    }

    private static void setNextTurnButton(Parent parent) {
        Image image1 = new Image("src/res/inGameResource/nextTurn.png");
        Image image2 = new Image("file:src\\res\\inGameResource\\nextTurn2.png");
        Pane pane = ((Pane) parent.lookup("#nextTurnPane"));
        ImageView imageView = ((ImageView) parent.lookup("#nextTurnButtonBack"));
        Label label = ((Label) parent.lookup("#nextTurnBtnLabel"));
        pane.setOnMouseEntered(mouseEvent -> {
            imageView.setImage(image2);
            label.setTextFill(Color.BLACK);
        });
        pane.setOnMouseExited(mouseEvent -> {
            imageView.setImage(image1);
            label.setTextFill(Color.web("#2884dd"));
        });
    }

    private static void setImageRotateForPlayGround(ImageView imageView) {
        imageView.setRotate(-67.4);
        imageView.setRotationAxis(new Point3D(-13, -9, -11));
    }


    private MediaView getBackGroundMusic() {
        Media media = new Media(new File("src/res/inGameResource/music_playmode.m4a").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        return new MediaView(mediaPlayer);
    }
}
