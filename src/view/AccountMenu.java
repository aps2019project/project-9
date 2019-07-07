package view;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import client.ShortAccount;
import com.google.gson.reflect.TypeToken;
import controller.AccountController;
import data.JsonProcess;
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
import javafx.util.Duration;
import javafx.util.Pair;
import server.Account;
import model.BattleResult;
import model.enumerations.AccountErrorType;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;

public class AccountMenu {
    private AccountRequest accountRequest = new AccountRequest();
    private static AccountMenu instance = new AccountMenu();
    private static Stage stage;
    private static MediaPlayer mediaPlayer;

    static void stopMusic() {
        mediaPlayer.stop();
    }

    public static void startMusic() {
        mediaPlayer.play();
    }

    static void closeMainStage() {
        stage.close();
    }


    private AccountMenu() {
    }

    public static AccountMenu getInstance() {
        return instance;
    }

    public void start(Stage stage, AccountController account) throws FileNotFoundException {
        stage.setTitle("Duelyst");
        stage.getIcons().add(new Image("src/res/icon.jpg"));
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(new FileInputStream("src/res/AccountMenuImages/12.png")));
        imageView.setFitHeight(462);
        ImageView loading = new ImageView(new Image("file:src/res/AccountMenuImages/loading.gif"));
        loading.setFitWidth(50);
        loading.setFitHeight(50);
        loading.setX(50);
        loading.setY(462);
        Text text = new Text("Connected To Server , Click Any Where To Enter The Game ...");
        text.setFont(Font.loadFont("file:src/res/inGameResource/font1.ttf", 10));
        text.setY(490);
        text.setX(110);
        Group group = new Group(imageView);
        group.getChildren().add(loading);
        group.getChildren().add(text);
        Scene scene = new Scene(group, 420, 562);
        scene.setFill(Color.DEEPPINK);
        scene.setOnMouseClicked(event -> accountMenuShow(stage, account));
        stage.setScene(scene);
        stage.show();
    }

    public void accountMenuShow(Stage last, AccountController account) {
        try {
            //
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.LOGOUT);
            Client.sendRequest(clientRequest);
            //
            Stage stage = new Stage();
            stage.getIcons().add(new Image("file:src/res/icon.jpg"));
            AccountMenu.stage = stage;
            stage.setOnCloseRequest(windowEvent -> System.exit(0));
            stage.setTitle("Duelyst");
            Font accountMenuFont = Font.loadFont(
                    new FileInputStream(new File("src/res/Font/modern.TTF")), 40);
            Text text = new Text(371, 100, "Account Menu");
            text.setFont(accountMenuFont);
            text.setFill(Color.rgb(2, 14, 236));
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(new FileInputStream("src/res/AccountMenuImages/5.jpg")));
            imageView.setFitWidth(1003);
            imageView.setFitHeight(762);
            Group root = new Group();
            runMusic(root);
            Button createAccountButton = setCreateAccountButton(account);
            Button loginButton = setLoginButton(account);
            Button showLeaderBoard = setLeaderBoardButton();
            Button helpButton = setHelpButton();
            root.getChildren().addAll(imageView, text, loginButton, createAccountButton, showLeaderBoard, helpButton);
            Scene scene = new Scene(root, 1003, 562);
            stage.setScene(scene);
            last.close();
            stage.show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Button setCreateAccountButton(AccountController account) {
        try {
            ImageView createAccountImageView;
            createAccountImageView = new ImageView(new Image(
                    new FileInputStream("src/res/AccountMenuImages/battered-axe.png")));
            createAccountImageView.setFitWidth(100);
            createAccountImageView.setFitHeight(50);
            Button button = new Button("Create Account", createAccountImageView);
            button.setLayoutX(385);
            button.setLayoutY(160);
            button.setStyle("-fx-background-color: #beaf92");
            setOnMouseAction(button);
            button.setOnMouseClicked(event -> {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Create Account");
                dialog.setHeaderText("Please enter a username and pass word");
                ButtonType createAccountButtonType = new ButtonType("Create Account", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(createAccountButtonType, ButtonType.CANCEL);
                GridPane grid = getGridPane();
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
                    //stage.close();
                    account.createAccount(accountRequest);
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
        mediaPlayer = player;
        MediaView mediaView = new MediaView(player);
        player.play();
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        root.getChildren().add(mediaView);
    }

    private void setOnMouseAction(Button button) {
        button.setPrefWidth(235);
        button.setStyle("-fx-background-color: #beaf92");
        button.setOnMouseEntered(mouseEvent -> {
            button.setStyle("-fx-background-color: black");
            button.setTextFill(Color.WHITE);
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setStyle("-fx-background-color: #beaf92");
            button.setTextFill(Color.BLACK);
        });
    }

    private Button setLoginButton(AccountController account) {
        try {
            ImageView loginImageview = new ImageView(new Image(
                    new FileInputStream("src/res/AccountMenuImages/bow-arrow.png")));
            loginImageview.setFitWidth(100);
            loginImageview.setFitHeight(50);
            Button button = new Button("Login", loginImageview);
            button.setLayoutX(385);
            button.setLayoutY(280);
            setOnMouseAction(button);
            button.setOnMouseClicked(event -> {
                Dialog<Pair<String, String>> dialog = new Dialog<>();
                dialog.setTitle("Login Dialog");
                dialog.setHeaderText("Look, a Custom Login Dialog");
                ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
                dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
                GridPane grid = getGridPane();
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
                username.textProperty().addListener((observable, oldValue, newValue) ->
                        loginButton.setDisable(newValue.trim().isEmpty()));
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
                    account.login(accountRequest);
                });
            });
            return button;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private GridPane getGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        return grid;
    }

    private Button setLeaderBoardButton() {
        try {
            ImageView loginImageView = new ImageView(
                    new Image(new FileInputStream("src/res/AccountMenuImages/sharp-shuriken.png")));
            loginImageView.setFitWidth(100);
            loginImageView.setFitHeight(50);
            Button button = new Button("show LeaderBoard", loginImageView);
            button.setLayoutX(385);
            button.setLayoutY(380);
            button.setStyle("-fx-background-color: #beaf92");
            setOnMouseAction(button);
            button.setOnMouseClicked(event -> showLeaderBoard());
            return button;
        } catch (FileNotFoundException e) {
            // TODO e.printStackTrace();
        }
        return null;
    }

    private Button setHelpButton() {
        try {
            ImageView loginImageView = new ImageView(
                    new Image(new FileInputStream("src/res/BattleMenuImages/life-buoy.png")));
            loginImageView.setFitWidth(100);
            loginImageView.setFitHeight(50);
            Button button = new Button("Help", loginImageView);
            button.setLayoutX(385);
            button.setLayoutY(480);
            button.setStyle("-fx-background-color: #beaf92");
            setOnMouseAction(button);
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

    private ArrayList<ShortAccount> getAccounts() {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.ACCOUNT_LIST);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        ArrayList<ShortAccount> accounts = JsonProcess.getGson().fromJson(response
                , new TypeToken<ArrayList<ShortAccount>>() {
                }.getType());
        return accounts;
    }

    private ArrayList<BattleResult> getBattleResults(Account account) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.BATTLE_RESULT_LIST);
        AccountRequest request = new AccountRequest();
        request.setUserName(account.getUserName());
        clientRequest.setAccountRequest(request);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        ArrayList<BattleResult> battleResults = JsonProcess.getGson().fromJson(response,
                new TypeToken<ArrayList<BattleResult>>() {
                }.getType());
        return battleResults;
    }

    private Account getAccount(ShortAccount account) {
        String userName = account.getUserName();
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.FIND_ACCOUNT);
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUserName(userName);
        clientRequest.setAccountRequest(accountRequest);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        return JsonProcess.getGson().fromJson(response, Account.class);
    }

    private void showLeaderBoard() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image("src/res/icon.jpg"));
        stage.setTitle("LeaderBoard");
        TableView<ShortAccount> table = new TableView<>();
        final ObservableList<ShortAccount> data = FXCollections.observableArrayList(getAccounts());
        table.setEditable(true);

        TableColumn firstNameCol = new TableColumn("User Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<ShortAccount, String>("userName"));

        TableColumn lastNameCol = new TableColumn("Wins");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<ShortAccount, Integer>("numberOfWins"));

        TableColumn isOnline = new TableColumn("Status");
        isOnline.setMinWidth(50);
        isOnline.setCellValueFactory(new PropertyValueFactory<ShortAccount, String>("isOnline"));

        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, isOnline);

        table.setOnMouseClicked(mouseEvent -> {
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.FIND_ACCOUNT);
            AccountRequest accountRequest = new AccountRequest();
            accountRequest.setUserName(table.getSelectionModel().getSelectedItem().getUserName());
            clientRequest.setAccountRequest(accountRequest);
            Client.sendRequest(clientRequest);
            Account account = JsonProcess.getGson().fromJson(Client.getResponse(), Account.class);
            Stage secondStage = new Stage();
            secondStage.setTitle(account.getUserName());
            Group root = new Group();
            Label label = new Label("Games Done :");
            root.getChildren().add(label);
            ListView<String> listView = new ListView<>();
            for (BattleResult battleResult : getBattleResults(account)) {
                listView.getItems().add(battleResult.toString());
            }
            //TODO for replay show
            /*listView.setOnMouseClicked(mouseEvent1 -> {
                BattleResult battleResult = account.getBattleResults()
                        .get(listView.getSelectionModel().getSelectedIndex());
                //
                //show replay
                //
            });*/
            listView.setLayoutY(20);
            listView.setPrefSize(400, 180);
            root.getChildren().add(listView);
            Scene scene = new Scene(root, 400, 200);
            secondStage.setScene(scene);
            secondStage.show();
        });

        Label label = new Label("Click On Any Account To See Brief Information");
        label.setLayoutX(0);
        label.setLayoutY(410);
        Group root = new Group(table);
        root.getChildren().add(label);
        Scene scene = new Scene(root, 250, 450);
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
