package view;

import controller.AccountController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import model.Account;
import model.enumerations.AccountErrorType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Optional;

public class AccountMenu {
    private static double height;
    private static double width;
    private AccountRequest accountRequest = new AccountRequest();
    private static AccountMenu instance = new AccountMenu();

    private AccountMenu() {
    }

    public static AccountMenu getInstance() {
        return instance;
    }

    public void start(Stage stage, AccountController account) throws FileNotFoundException {
        stage.setTitle("project 9");
        stage.getIcons().add(new Image("src/res/icon.png"));

        /*Media media = new Media(new File("src\\res\\music\\backgroundmusic.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();*/

        stage.setMaximized(true);
        height = stage.getMaxHeight();
        width = stage.getMaxWidth();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new FileInputStream("src/res/AccountMenuImages/1.png")));
        imageView.setFitHeight(700);
        imageView.setFitWidth(810);
        imageView.setX(300);
        Group group = new Group(imageView);
        Scene scene = new Scene(group, height, width);
        scene.setFill(Color.DEEPPINK);
        scene.setOnMouseClicked(event -> {
            stage.close();
            accountMenuShow(stage, account);
        });
        stage.setScene(scene);
        stage.show();
    }

    private void accountMenuShow(Stage stage, AccountController account) {
        try {
            Font accountMenuFont = Font.loadFont(new FileInputStream(new File("src/res/Font/modern.TTF")), 40);
            Text text = new Text(651, 100, "Account Menu");
            text.setFont(accountMenuFont);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/AccountMenuImages/5.jpg")));
            imageView.setFitWidth(1700);
            imageView.setFitHeight(1050);
            Group root = new Group();
            runMusic(root);
            Button createAccountButton = setCreateAccountButton(account, stage);
            Button loginButton = setLoginButton(account, stage);
            Button showLeaderBoard = setLeaderBoardButton();
            Button helpButton = setHelpButton();

            root.getChildren().addAll(imageView, text, loginButton, createAccountButton, showLeaderBoard, helpButton);
            Scene scene = new Scene(root, height, width);
            stage.setScene(scene);
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Button setCreateAccountButton(AccountController account, Stage stage) {
        try {
            ImageView createAccountImageView;
            createAccountImageView = new ImageView(new Image(new FileInputStream("src/res/AccountMenuImages/battered-axe.png")));
            createAccountImageView.setFitWidth(100);
            createAccountImageView.setFitHeight(50);
            Button button = new Button("Create Account", createAccountImageView);
            button.setLayoutX(630);
            button.setLayoutY(200);
            button.setStyle("-fx-background-color: #beaf92");
            button.setOnMouseClicked(event -> {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Create Account");
                dialog.setHeaderText("Please enter a username and pass word");

                ButtonType createAccountButtonType = new ButtonType("Create Account", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(createAccountButtonType, ButtonType.CANCEL);

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField username = new TextField();
                username.setPromptText("Username");
                PasswordField password = new PasswordField();
                password.setPromptText("Password");

                grid.add(new Label("Username:"), 0, 0);
                grid.add(username, 1, 0);
                grid.add(new Label("Password:"), 0, 1);
                grid.add(password, 1, 1);

                Node loginButton = dialog.getDialogPane().lookupButton(createAccountButtonType);
                loginButton.setDisable(true);

                username.textProperty().addListener((observable, oldValue, newValue) ->
                        loginButton.setDisable(newValue.trim().isEmpty()));

                dialog.getDialogPane().setContent(grid);

                Platform.runLater(username::requestFocus);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == createAccountButtonType) {
                        return new Pair<>(username.getText(), password.getText());
                    }
                    return null;
                });

                Optional<Pair<String, String>> result = dialog.showAndWait();

                result.ifPresent(usernamePassword -> {
                    accountRequest.setUserName(usernamePassword.getKey());
                    accountRequest.setPassWord(usernamePassword.getValue());
                    account.createAccount(accountRequest, stage);
                });
            });
            return button;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void runMusic(Group root) {
        Media media = new Media(new File("src\\res\\music\\backgroundmusic.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        MediaView mediaView = new MediaView(player);
        player.play();
        root.getChildren().add(mediaView);
    }

    private Button setLoginButton(AccountController account, Stage stage) {
        ImageView loginImageview = null;
        try {
            loginImageview = new ImageView(new Image(new FileInputStream("src/res/AccountMenuImages/bow-arrow.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        loginImageview.setFitWidth(100);
        loginImageview.setFitHeight(50);
        Button button = new Button("Login", loginImageview);
        button.setLayoutX(630);
        button.setLayoutY(300);
        button.setStyle("-fx-background-color: #beaf92");
        button.setOnMouseClicked(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Login Dialog");
            dialog.setHeaderText("Look, a Custom Login Dialog");

            ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField username = new TextField();
            username.setPromptText("Username ");
            PasswordField password = new PasswordField();
            password.setPromptText("Password ");

            grid.add(new Label("Username : "), 0, 0);
            grid.add(username, 1, 0);
            grid.add(new Label("Password : "), 0, 1);
            grid.add(password, 1, 1);

            Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
            loginButton.setDisable(true);

            username.textProperty().addListener((observable, oldValue, newValue) -> {
                loginButton.setDisable(newValue.trim().isEmpty());
            });

            dialog.getDialogPane().setContent(grid);

            Platform.runLater(username::requestFocus);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(username.getText(), password.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();

            result.ifPresent(usernamePassword -> {
                accountRequest.setUserName(usernamePassword.getKey());
                accountRequest.setPassWord(usernamePassword.getValue());
                account.login(accountRequest, stage);
            });
        });
        return button;
    }

    private Button setLeaderBoardButton() {
        try {
            ImageView loginImageview = new ImageView(new Image(new FileInputStream("src/res/AccountMenuImages/sharp-shuriken.png")));
            loginImageview.setFitWidth(100);
            loginImageview.setFitHeight(50);
            Button button = new Button("show LeaderBoard", loginImageview);
            button.setLayoutX(630);
            button.setLayoutY(400);
            button.setStyle("-fx-background-color: #beaf92");
            button.setOnMouseClicked(event -> showLeaderBoard());
            return button;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Button setHelpButton() {
        try {
            ImageView loginImageview = new ImageView(new Image(new FileInputStream("src/res/BattleMenuImages/life-buoy.png")));
            loginImageview.setFitWidth(100);
            loginImageview.setFitHeight(50);
            Button button = new Button("Help", loginImageview);
            button.setLayoutX(630);
            button.setLayoutY(500);
            button.setStyle("-fx-background-color: #beaf92");
            button.setOnMouseClicked(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("HELP");
                alert.setHeaderText(null);
                alert.setHeight(300);
                alert.setWidth(250);
                alert.setContentText(
                        "create account --- > creating a new account by entering a username and password \n" +
                                "login --->  login to your account by entering username and password \n" +
                                "LeaderBoard  ---> show LeaderBoard \n");
                alert.showAndWait();
            });
            return button;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void showLeaderBoard() {
        Stage stage = new Stage();
        stage.setTitle("LeaderBoard");
        TableView<Account> table = new TableView<>();
        final ObservableList<Account> data = FXCollections.observableArrayList(Account.getAccounts());
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

        Group root = new Group(table);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void printError(AccountErrorType request) {
        switch (request) {
            case USERNAME_EXISTS:
                new Alert(Alert.AlertType.WARNING, "this username is taken").showAndWait();
                break;
            case INVALID_PASSWORD:
                new Alert(Alert.AlertType.WARNING, "Invalid password").showAndWait();
                break;
            case INVALID_USERNAME:
                new Alert(Alert.AlertType.WARNING, "Invalid username").showAndWait();
                break;
        }
    }

}
