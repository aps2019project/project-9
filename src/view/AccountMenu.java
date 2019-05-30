package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
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
            imageView.setImage(new Image(new FileInputStream("src/res/1.png")));
            imageView.setFitHeight(800);
            imageView.setFitWidth(900);
            imageView.setX(300);
            Group group = new Group(imageView);
            Scene scene = new Scene(group, height, width);
            scene.setFill(Color.DEEPPINK);
            scene.setOnMouseClicked(event -> accountMenuShow(stage));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void accountMenuShow(Stage stage) {
        try {
            Font accountMenuFont = Font.loadFont(new FileInputStream(new File("src/res/modern.TTF")), 40);
            Text text = new Text(651, 100, "Account Menu");
            text.setFont(accountMenuFont);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/5.jpg")));
            imageView.setFitWidth(1700);
            imageView.setFitHeight(1050);

            ////most converted to button
            Text createAccount = new Text(630, 200, "Create account");
            createAccount.setFont(accountMenuFont);

            Text login = new Text(630, 300, "Login");
            login.setFont(accountMenuFont);

            Text showLeaderBoard = new Text(630, 400, "show LeaderBoard");
            showLeaderBoard.setFont(accountMenuFont);

            Text help = new Text(630, 500, "Help");
            help.setFont(accountMenuFont);

            //to here

            Group root = new Group(imageView, text, createAccount, login, showLeaderBoard, help);
            stage.setScene(new Scene(root, height, width));
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
