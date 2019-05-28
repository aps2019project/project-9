package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class AccountMenu extends Application {
    double height;
    double width;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            stage.setTitle("project 9");
            stage.setMaximized(true);
            height = stage.getMaxHeight();
            width = stage.getMaxWidth();
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("C:\\Users\\WIN10\\Desktop\\project-9\\res\\1.png")));
            imageView.setFitHeight(100);
            imageView.setFitWidth(100);
            Group group = new Group(imageView);
            Scene scene = new Scene(group, height, width);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}