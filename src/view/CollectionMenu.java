package view;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CollectionMenu {

    int j = 0;
    double orgCliskSceneX, orgReleaseSceneX;
    Button lbutton, rButton;
    ImageView imageView;
    private int size = 4;

    public void start(Stage primaryStage) {
        // images in src folder.
        try {
            primaryStage.setMaximized(true);

            GridPane root = new GridPane();
            root.setAlignment(Pos.CENTER);

            lbutton = new Button("<");
            rButton = new Button(">");

            Image images[] = new Image[size];
            for (int i = 0; i < size; i++) {
                images[i] = new Image("/src/res/collection-menu-images/" + i + ".jpg");
            }

            imageView = new ImageView(images[j]);
            imageView.setCursor(Cursor.CLOSED_HAND);

            imageView.setOnMousePressed(circleOnMousePressedEventHandler);

            Date point = new Date();

            //TODO please

            if (orgCliskSceneX > orgReleaseSceneX) {
                lbutton.fire();
            } else {
                rButton.fire();
            }

            rButton.setOnAction(e -> {
                j = j + 1;
                if (j == size) {
                    j = 0;
                }
                imageView.setImage(images[j]);

            });
            lbutton.setOnAction(e -> {
                j = j - 1;
                if (j == 0 || j > size + 1 || j == -1) {
                    j = size - 1;
                }
                imageView.setImage(images[j]);

            });

            imageView.setFitHeight(800);
            imageView.setFitWidth(1600);

            HBox hBox = new HBox();
            hBox.setSpacing(15);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(imageView);

            root.add(hBox, 1, 1);
            Scene scene = new Scene(root, 800, 300);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    EventHandler<MouseEvent> circleOnMousePressedEventHandler = new EventHandler<>() {
        @Override
        public void handle(MouseEvent t) {
            orgCliskSceneX = t.getSceneX();
        }
    };
}