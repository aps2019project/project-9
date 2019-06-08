package view;

import java.io.File;
import java.util.*;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

public class CollectionMenu {

    private int count = 0;
    private ImageView slideshowImageView;
    private final int size = 4;
    private long delay = 10000;

    public void start(Stage primaryStage) {
        try {
            primaryStage.setMaximized(true);

            Group root = new Group();
            runMusic(root);
            runSlideShow(root);

            setButtons(root);
            Scene scene = new Scene(root, 800, 300);
            primaryStage.setScene(scene);
            scene.getStylesheets().add("src/res/CollectionButtonStyle.css");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runMusic(Group root){
        Media media = new Media(new File("src\\res\\music\\backgroundmusic.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
        player.play();
        root.getChildren().add(mediaView);
    }

    private void runSlideShow(Group root) {
        ArrayList<Image> images = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            images.add(new Image("/src/res/collection-menu-images/" + i + ".jpg"));
        }
        slideshowImageView = new ImageView(images.get(0));
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                slideshowImageView.setImage(images.get(count++));
                if (count >= images.size()) {
                    count = 0;
                }
            }
        }, 0, delay);
        slideshowImageView.setFitHeight(810);
        slideshowImageView.setFitWidth(1600);
        root.getChildren().addAll(slideshowImageView);
    }

    private void setButtons(Group root) {
        int addX = 30;
        int addY = 60;
        int startX = 30;
        int startY = 320;

        Button selectDeck = new Button("Select Deck");
        selectDeck.setLayoutX(startX);
        selectDeck.setLayoutY(startY);
        Button createDeck = new Button("Create Deck");
        createDeck.setLayoutX(startX + addX);
        createDeck.setLayoutY(startY + addY);
        Button deleteDeck = new Button("Delete Deck");
        deleteDeck.setLayoutX(startX + 2 * addX);
        deleteDeck.setLayoutY(startY + 2 * addY);
        Button showAllDecks = new Button("Show All Decks");
        showAllDecks.setLayoutX(startX + 3 * addX);
        showAllDecks.setLayoutY(startY + 3 * addY);
        Button showDeck = new Button("Show Deck");
        showDeck.setLayoutX(startX + 4 * addX);
        showDeck.setLayoutY(startY + 4 * addY);
        Button save = new Button("Save");
        save.setLayoutX(startX + 5 * addX);
        save.setLayoutY(startY + 5 * addY);
        Button help = new Button("help");
        help.setLayoutX(startX + 6 * addX);
        help.setLayoutY(startY + 6 * addY);
        Button back = new Button("Exit");
        back.setLayoutX(startX + 7 * addX);
        back.setLayoutY(startY + 7 * addY);
        Button triangleButton = new Button("show\nCollection");
        triangleButton.setStyle("-fx-background-color: \n " +
                "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                "    -fx-background-radius: 8,7,6;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        triangleButton.setLayoutX(40);
        triangleButton.setLayoutY(700);

        root.getChildren().addAll(selectDeck, createDeck, deleteDeck, showAllDecks, showDeck, save, help, back, triangleButton);
    }
}