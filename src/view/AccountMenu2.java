package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AccountMenu2 extends Application {

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
            imageView.setImage(new Image(new FileInputStream("src/res/1.png")));
            imageView.setFitHeight(800);
            imageView.setFitWidth(900);
            imageView.setX(300);
            Group group = new Group(imageView);
            Scene scene = new Scene(group, height, width);
            scene.setFill(Color.DEEPPINK);
            scene.setOnMouseClicked(event -> showLeaderBoard(stage));
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
            //ImageView createAccountImageView = new ImageView(new Image(new FileInputStream("src/res/battered-axe.png")));
            //Button button = new Button("Create Account",imageView);

            Text createAccount = new Text(630, 200, "Create account");
            createAccount.setFont(accountMenuFont);
            //createAccount(request);

            Text login = new Text(630, 300, "Login");
            login.setFont(accountMenuFont);

            Text showLeaderBoard = new Text(630, 400, "show LeaderBoard");
            showLeaderBoard.setFont(accountMenuFont);
            //showLeaderBoard()

            Text help = new Text(630, 500, "Help");
            help.setFont(accountMenuFont);

            //to here

            Group root = new Group(imageView, text, createAccount, login, showLeaderBoard, help);
            Scene scene = new Scene(root, height, width);
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void showLeaderBoard(Stage stage){
        TableView<Account> table = new TableView<>();
        final ObservableList<Account> data =
                FXCollections.observableArrayList();
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);
        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("User Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Account, String>("userName"));

        TableColumn lastNameCol = new TableColumn("Wins");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Account, Integer>("numberOfWins"));


        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }
}
