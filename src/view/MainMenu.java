package view;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
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

public class MainMenu extends Application {
    private static final double HEIGHT = 400;
    private static final double WIDTH = 400;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            Group root = new Group();
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            Text text = new Text(100, 50, "Main Menu");
            text.setFont(Font.loadFont(new FileInputStream(new File("src/res/modern.TTF")), 50));
            text.setX(650);
            text.setY(100);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/4.jpg")));
            imageView.setX(-300);
            imageView.setFitWidth(2100);
            imageView.setFitHeight(900);
            Button collection = new Button("Collection");
            Button shop = new Button("shop");
            Button battle = new Button("battle");
            Button exit = new Button("exit");
            addToGroup(root, text, collection, shop, battle, exit);
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void addToGroup(Group root, Node... nodes) {
        for (Node node : nodes) {
            root.getChildren().add(node);
        }
    }

}