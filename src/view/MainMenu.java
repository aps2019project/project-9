package view;

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
import model.Account;
import data.JsonProcess;
import model.enumerations.MainMenuErrorType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainMenu {
    private static final MainMenu MAINMENU = new MainMenu();

    private MainMenu() {
    }

    public static MainMenu getInstance() {
        return MAINMENU;
    }

    public void start(Stage stage, Account loggedAccount) {
        try {
            Font mainMenuFont = Font.loadFont(
                    new FileInputStream(new File("src/res/Font/ALGER.TTF")), 55);
            Text text = new Text(385, 100, "Main Menu");
            text.setFont(mainMenuFont);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/MainMenuImages/2.jpg")));
            imageView.setX(-200);
            imageView.setFitWidth(1203);
            imageView.setFitHeight(562);
            Group root = new Group(imageView, text);
            setButtons(root, MainMenuController.getInstance(loggedAccount), stage);
            Scene scene = new Scene(root, 1003, 562);
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/res/icon.jpg"));
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setActionsAndStyles(Button button) {
        button.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f;");
        button.setOnMouseEntered(mouseEvent ->
                button.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: red"));
        button.setOnMouseExited(mouseEvent ->
                button.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #020d7f"));
    }

    private void setButtons(Group root, MainMenuController controller, Stage stage) {
        try {
            Font font = Font.loadFont(new FileInputStream(new File("src/res/Font/ALGER.TTF")), 13);
            Button collection = new Button("Collection");
            collection.setFont(font);
            collection.setLayoutX(480);
            collection.setLayoutY(200);
            collection.setScaleX(3);
            collection.setScaleY(2.5);
            setActionsAndStyles(collection);
            collection.setOnMouseClicked(event -> controller.goCollectionMenu(controller.getLoggedInAccount()));

            Button shop = new Button("shop");
            shop.setFont(font);
            shop.setLayoutX(485);
            shop.setLayoutY(300);
            shop.setScaleX(3);
            shop.setScaleY(2.5);
            setActionsAndStyles(shop);
            shop.setOnMouseClicked(event -> controller.goShopMenu(controller.getLoggedInAccount()));

            Button battle = new Button("battle");
            battle.setFont(font);
            battle.setLayoutX(485);
            battle.setLayoutY(400);
            battle.setScaleX(3);
            battle.setScaleY(2.5);
            setActionsAndStyles(battle);
            battle.setOnMouseClicked(event -> controller.goBattleMenu(stage));

            Button logOut = new Button("Save And Log Out");
            logOut.setFont(font);
            logOut.setLayoutX(465);
            logOut.setLayoutY(500);
            logOut.setScaleX(3);
            logOut.setScaleY(2.5);
            setActionsAndStyles(logOut);
            logOut.setOnMouseClicked(event -> {
                //saving
                JsonProcess.saveAccount(controller.getLoggedInAccount());
                stage.close();
            });

            Button defaultCard = new Button("Custom\n  Card");
            defaultCard.setFont(font);
            defaultCard.setLayoutX(50);
            defaultCard.setLayoutY(450);
            defaultCard.setScaleX(2);
            defaultCard.setScaleY(2);
            defaultCard.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #ff1cd7;");
            defaultCard.setOnMouseEntered(mouseEvent ->
                    defaultCard.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #063e07"));
            defaultCard.setOnMouseExited(mouseEvent ->
                    defaultCard.setStyle("-fx-background-color: rgba(0,0,0,0);-fx-text-fill: #ff1cd7"));
            defaultCard.setOnMouseClicked(ma -> GoToCustomCardMenu(controller.getLoggedInAccount()));

            ImageView imageView = new ImageView(new Image("src\\res\\MainMenuImages\\1.png"));
            imageView.setScaleY(0.5);
            imageView.setScaleX(0.5);
            imageView.setLayoutX(-30);
            imageView.setLayoutY(320);
            imageView.setOnMouseClicked(m -> GoToCustomCardMenu(controller.getLoggedInAccount()));

            root.getChildren().addAll(imageView, collection, shop, battle, logOut, defaultCard);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void GoToCustomCardMenu(Account loggedAccount) {
        CustomCardMenu customCardMenu = new CustomCardMenu(loggedAccount);
        try {
            customCardMenu.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printError(MainMenuErrorType e) {
        new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
    }
}