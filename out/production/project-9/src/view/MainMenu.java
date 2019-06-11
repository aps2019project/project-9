package view;

import controller.AccountController;
import controller.MainMenuController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.enumerations.MainMenuErrorType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainMenu {
    private static double HEIGHT;
    private static double WIDTH;
    private static final MainMenu MAINMENU = new MainMenu();

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        return MAINMENU;
    }

    public void start(Stage stage, MainMenuController controller) {
        try {
            HEIGHT = stage.getMaxHeight();
            WIDTH = stage.getMaxWidth();
            stage.setMaximized(true);
            Font mainMenuFont = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 60);
            Text text = new Text(600, 100, "Main Menu");
            text.setFont(mainMenuFont);
            text.setFill(Color.rgb(2, 14, 236));

            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/MainMenuImages/2.jpg")));
            imageView.setX(-300);
            imageView.setFitWidth(2100);
            imageView.setFitHeight(900);

            Group root = new Group(imageView, text);
            setButtons(root,controller,stage);
            Scene scene = new Scene(root, WIDTH, HEIGHT);
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void setButtons(Group root, MainMenuController controller, Stage stage) {
        try {
            Font collectionFont = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 25);
            Button collection = new Button("Collection");
            collection.setFont(collectionFont);
            collection.setLayoutX(680);
            collection.setLayoutY(200);
            collection.setScaleX(3);
            collection.setScaleY(2.5);
            collection.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f;");
            collection.setOnMouseClicked(event -> {
                controller.goCollectionMenu(controller.getLoggedInAccount());
            });

            Font shopFont = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 30);
            Button shop = new Button("shop");
            shop.setFont(shopFont);
            shop.setLayoutX(720);
            shop.setLayoutY(350);
            shop.setScaleX(3);
            shop.setScaleY(2.5);
            shop.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f;");
            shop.setOnMouseClicked(event -> {
                controller.goShopMenu(controller.getLoggedInAccount(),stage);
            });

            Font battleFont = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 35);
            Button battle = new Button("battle");
            battle.setFont(battleFont);
            battle.setLayoutX(690);
            battle.setLayoutY(500);
            battle.setScaleX(3);
            battle.setScaleY(2.5);
            battle.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f;");
            battle.setOnMouseClicked(event -> {
                controller.goBattleMenu(controller.getLoggedInAccount());
            });

            Font logOutFont = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 26);
            Button logOut = new Button("Log Out");
            logOut.setFont(logOutFont);
            logOut.setLayoutX(720);
            logOut.setLayoutY(650);
            logOut.setScaleX(3);
            logOut.setScaleY(2.5);
            logOut.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f;");
            logOut.setOnMouseClicked(event -> {
                try {
                    AccountController accountController = new AccountController();
                    accountController.start(stage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            root.getChildren().addAll(collection, shop, battle, logOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printError(MainMenuErrorType e) {
        new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
    }
}