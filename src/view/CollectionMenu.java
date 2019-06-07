package view;

import java.util.*;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CollectionMenu {

    private int count = 0;
    private ImageView slideshowImageView;
    private final int size = 4;
    private long delay = 10000;

    public void start(Stage primaryStage) {
        try {
            primaryStage.setMaximized(true);

            GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);

            runSlideShow();

            HBox hBox = new HBox();
            hBox.setSpacing(15);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(slideshowImageView);

            root.add(hBox, 1, 1);
            Scene scene = new Scene(root, 800, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void runSlideShow() {
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
        slideshowImageView.setFitHeight(800);
        slideshowImageView.setFitWidth(1600);
    }
}