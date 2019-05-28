package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AccountMenu extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    double height;
    double width;

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
            scene.setOnMouseClicked(m -> accountMenuShow(stage));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void accountMenuShow(Stage stage) {
        try {
            Text text = new Text(100, 50, "Account Menu");
            text.setFont(Font.loadFont(new FileInputStream(new File(".\\res\\modern.TTF")), 50));
            text.setX(651);
            text.setY(100);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream(".\\res\\5.jpg")));
            imageView.setFitWidth(1700);
            imageView.setFitHeight(1050);
            Button createAccount = new Button();
            createAccount.setText("Create Account");
            createAccount.setLayoutX(100);
            createAccount.setLayoutY(100);

            stage.setScene(new Scene(new Group(imageView, text, createAccount), height, width));
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
