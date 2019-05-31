package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class ShopMenu {


    public void start(Stage stage) {
        try {
            stage.setMaximized(true);
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/res/shop.jpg")));
            imageView.setFitHeight(810);
            imageView.setFitWidth(1600);



            Group root = new Group(imageView);
            Scene scene = new Scene(root, stage.getMaxHeight(), stage.getMaxWidth());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
