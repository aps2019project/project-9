package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenu extends Application {
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
            imageView.setImage(new Image(new FileInputStream(".\\res\\1.png")));
            imageView.setFitHeight(800);
            imageView.setFitWidth(900);
            imageView.setX(300);
            Group group = new Group(imageView);
            Scene scene = new Scene(group, height, width);
            scene.setFill(Color.DEEPPINK);
            scene.setOnMouseClicked(m -> mainMenuShow(stage));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void mainMenuShow(Stage stage) {
        try {
            Text text = new Text(100, 50, "Main Menu");
            text.setFont(Font.loadFont(new FileInputStream(new File(".\\res\\modern.TTF")), 50));
            text.setX(650);
            text.setY(100);
            text.setFill(Color.rgb(2,14,236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream(".\\res\\4.jpg")));
            imageView.setX(-300);
            imageView.setFitWidth(2100);
            imageView.setFitHeight(900);
            stage.setScene(new Scene(new Group(imageView, text), height, width));
            stage.show();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}