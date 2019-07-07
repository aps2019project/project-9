package server;

import client.GameRequest;
import data.JsonProcess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.BattleResult;
import model.Deck;
import model.MultiPlayerBattle;
import model.cards.Card;
import model.enumerations.GameMode;
import model.items.Item;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Server extends Application {
    static ArrayList<ClientHandler> onlineClients = new ArrayList<>();
    static ArrayList<String> userNamesLoggedIn = new ArrayList<>();
    static ArrayList<String> globalChat = new ArrayList<>();
    static ArrayList<GameRequest> gameRequests = new ArrayList<>();
    static MultiPlayerBattle multiPlayerBattle;
    private ServerSocket serverSocket;
    private static final int DEFAULT_PORT = 8000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Font font = Font.loadFont(new FileInputStream(new File("src/res/Font/aks.Ttf")), 18);
            Font smallerFont = Font.loadFont(new FileInputStream(new File("src/res/Font/aks.Ttf")), 14);
            primaryStage.getIcons().add(new Image("file:src/res/icon.jpg"));
            Group root = new Group();
            Scene scene = new Scene(root, 400, 400);
            scene.getStylesheets().add("CSS/Server.css");
            Label label = new Label("");
            Button onLineUsers = new Button("OnLine Users");
            setOnLineUserButton(onLineUsers, font);
            Button allAccounts = getAllAccountsBtn(font);
            Button shop = getShopBtn(font);
            showGameRequests(root, smallerFont);
            setOnLineClientsBtn(root, font);
            setCustomCardCreation(root, smallerFont);

            ServerSocket serverSocket = new ServerSocket(getPort());
            this.serverSocket = serverSocket;
            label.setFont(font);
            label.setText("server is listening on port : " + serverSocket.getLocalPort());
            label.setLayoutX(10);
            Thread thread = serverRefreshThread();
            thread.setDaemon(true);
            thread.start();
            root.getChildren().addAll(label, onLineUsers, shop, allAccounts);
            primaryStage.setTitle("Server");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOnLineUserButton(Button button, Font font) {
        button.setFont(font);
        button.setLayoutX(10);
        button.setLayoutY(30);
        setUserBtnAction(button);
    }

    private int getPort() {
        try {
            File file = new File("src/server/config.txt");
            Scanner scanner = new Scanner(file);
            String str = scanner.next();
            return Integer.parseInt(str.replaceAll("\\D+", "")); //int Port =
        } catch (IOException e) {
            e.printStackTrace();
            return DEFAULT_PORT;
        }
    }

    private void showGameRequests(Group group, Font font) {
        ListView listView = new ListView();
        Button button = new Button("refresh game requests");
        button.setFont(font);
        button.setLayoutX(200);
        button.setLayoutY(220);
        listView.setLayoutY(60);
        Label label = new Label("game requests");
        label.setFont(font);
        label.setLayoutX(200);
        label.setLayoutY(30);
        listView.setLayoutX(200);
        listView.setPrefWidth(190);
        listView.setPrefHeight(150);
        group.getChildren().addAll(label, listView, button);
        button.setOnMouseClicked(mouseEvent -> {
            listView.getItems().addAll(gameRequests);
        });
    }

    private void setUserBtnAction(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 400, 400);
            ListView<String> clients = new ListView<>();
            clients.getItems().addAll(userNamesLoggedIn);
            root.getChildren().addAll(clients);
            stage.setScene(scene);
            stage.show();
        });
    }

    private void setOnLineClientsBtn(Group group, Font font) {
        Button button = new Button("Online Clients");
        button.setFont(font);
        button.setLayoutX(10);
        button.setLayoutY(210);
        button.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 400, 400);
            stage.setScene(scene);
            ListView<String> client = new ListView<>();
            for (ClientHandler onlineClient : onlineClients) {
                client.getItems().add(onlineClient.getAuthToken());
            }
            root.getChildren().addAll(client);
            stage.show();
        });
        group.getChildren().add(button);
    }


    private Button getAllAccountsBtn(Font font) {
        Button button = new Button("Accounts");
        button.setFont(font);
        button.setLayoutX(10);
        button.setLayoutY(90);
        button.setOnMouseClicked(mouseEvent -> {
            Group root = new Group();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400, 400);
            ListView<String> accounts = new ListView<>();
            ArrayList<Account> allAccounts = JsonProcess.getSavedAccounts();
            Collections.sort(allAccounts);
            for (Account account : allAccounts) {
                accounts.getItems().add(account.getUserName() + " -> wins : " + account.getNumberOfWins()
                        + " -> looses : " + account.getNumberOfLoose());
            }
            accounts.setOnMouseClicked(mouseEvent1 -> {
                Account account = Account.findAccount(accounts.getSelectionModel().getSelectedItem().split(" ")[0]);
                try {
                    showInformation(account);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            root.getChildren().addAll(accounts);
            stage.setScene(scene);
            stage.show();
        });
        return button;
    }

    private void setCustomCardCreation(Group group, Font font) {
        Button button = new Button("Create Custom Card");
        button.setFont(font);
        button.setLayoutX(10);
        button.setLayoutY(270);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                new CustomCardMenu().start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        group.getChildren().add(button);
    }

    private void showInformation(Account account) throws IOException {
        Stage stage = new Stage();
        Group group = new Group();
        Scene scene = new Scene(group);
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/accountInformation.fxml"));
        Parent parent = fxmlLoader.load();
        group.getChildren().add(parent);
        ((Button) parent.lookup("#returnBtn")).setOnMouseClicked(mouseEvent -> stage.close());
        ((Label) parent.lookup("#userName")).setText(account.getUserName());
        String text = "";
        text += "account user name : " + account.getUserName();
        text += "\naccount pass word : " + account.getPassWord();
        text += "\nnumber Of Wins : " + account.getNumberOfWins();
        text += "\nnumber Of Loose : " + account.getNumberOfLoose();
        text += "\nmoney : " + account.getMoney();
        text += "\nGames Done : ";
        for (BattleResult battleResult : account.getBattleResults()) {
            text += "\n" + battleResult.toString();
        }
        text += "\nmain deck : " + account.getMainDeck().getName();
        text += "\nall decks : ";
        for (Deck deck : account.getDecks()) {
            text += "\n" + deck.getName();
        }
        ((TextArea) parent.lookup("#desc")).setText(text);
        stage.setTitle("Account Information");
        stage.setScene(scene);
        stage.show();
    }

    private Button getShopBtn(Font font) {
        Button button = new Button("Show Shop");
        button.setFont(font);
        button.setLayoutX(10);
        button.setLayoutY(150);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/serverShop.fxml"));
                Parent parent = fxmlLoader.load();
                Scene scene = new Scene(parent);
                Button refresh = ((Button) parent.lookup("#refresh"));
                refresh.getStylesheets().add("CSS/Server.css");
                refresh.setFont(font);
                refresh.setOnMouseClicked(mouseEvent1 -> refreshShop(parent));
                stage.setScene(scene);
                stage.setTitle("Server Shop");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return button;
    }

    private void refreshShop(Parent parent) {
        Shop shop = Shop.getInstance();
        ListView<String> cards = ((ListView) parent.lookup("#cards"));
        ListView<String> items = ((ListView) parent.lookup("#items"));
        TextArea desc = ((TextArea) parent.lookup("#desc"));
        cards.getItems().clear();
        items.getItems().clear();
        cards.setOnMouseClicked(mouseEvent -> {
            String card = cards.getSelectionModel().getSelectedItem();
            Card card1 = shop.searchCardByName(card);
            desc.setText(card1.getName() + "\n" + "cost : " + card1.getCost()
                    + "\nnumbers remaining : " + shop.cardNumbers.get(card1.getName()));
        });
        items.setOnMouseClicked(mouseEvent -> {
            String item = items.getSelectionModel().getSelectedItem();
            Item card1 = shop.searchItemByName(item);
            desc.setText(card1.getName() + "\n" + "cost : " + card1.getCost()
                    + "\nnumbers remaining : " + shop.itemNumbers.get(card1.getName()));
        });
        for (Card card : shop.getCards()) {
            cards.getItems().add(card.getName());
        }
        for (Item item : shop.getItems()) {
            items.getItems().add(item.getName());
        }
    }

    private Thread serverRefreshThread() {
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Socket socket;
                    socket = serverSocket.accept();
                    ClientHandler handler = new ClientHandler(generateRandomKey(), socket);
                    onlineClients.add(handler);
                    handler.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateRandomKey() {
        int count = 8;
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        for (ClientHandler onlineClient : onlineClients) {
            if (onlineClient.getAuthToken().equals(builder.toString()))
                return generateRandomKey();
        }
        return builder.toString();
    }

    private static void savePort(int port) {
        try {
            FileWriter fileWriter = new FileWriter("src/server/config.txt");
            fileWriter.write(port);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void startGame() {
        String firstPlayer = gameRequests.get(0).getUserRequested();
        String secondPlayer = gameRequests.get(1).getUserRequested();
        GameMode gameMode = gameRequests.get(0).getGameMode();
        int flags = gameRequests.get(0).getNumberOfFlags();
        try {
            for (ClientHandler onlineClient : onlineClients) {
                if (onlineClient.getUserName().equals(firstPlayer))
                    onlineClient.outputStream.writeUTF("game start");
                if (onlineClient.getUserName().equals(secondPlayer))
                    onlineClient.outputStream.writeUTF("game start");
            }
            gameRequests.clear();
            MultiPlayerBattle battle = new MultiPlayerBattle(Account.findAccount(firstPlayer)
                    , Account.findAccount(secondPlayer), gameMode, flags);
            multiPlayerBattle = battle;
            //TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
