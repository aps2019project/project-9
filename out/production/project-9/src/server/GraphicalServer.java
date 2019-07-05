package server;

import data.JsonProcess;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.cards.Card;
import model.items.Item;
import view.AccountMenu;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class GraphicalServer extends Application {
    static ArrayList<ClientHandler> onlineClients = new ArrayList<>();
    static ArrayList<String> userNamesLoggedIn = new ArrayList<>();
    static ArrayList<String> globalChat = new ArrayList<>();
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400);
        Label label = new Label("");
        Button button = new Button("OnLine Users");
        Button allAccounts = getAllAccountsBtn();
        Button shop = getShopBtn();
        button.setLayoutX(200);
        button.setLayoutY(50);
        setUserBtnAction(button);
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            this.serverSocket = serverSocket;
            savePort(serverSocket.getLocalPort());
            label.setText("server is listening on port : " + serverSocket.getLocalPort());
            Thread thread = serverRefreshThread();
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        root.getChildren().addAll(label, button, shop, allAccounts);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
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

    private Button getAllAccountsBtn() {
        Button button = new Button("Accounts");
        button.setLayoutX(200);
        button.setLayoutY(100);
        button.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 400, 400);
            ListView<String> accounts = new ListView<>();
            ArrayList<Account> allAccounts = JsonProcess.getSavedAccounts();
            for (Account allAccount : allAccounts) {
                accounts.getItems().add(allAccount.getUserName());
            }
            accounts.setOnMouseClicked(mouseEvent1 -> {
                Account account = Account.findAccount(accounts.getSelectionModel().getSelectedItem());
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
        text += "\nmoney : " + account.getMoney();
        text += "\nmain deck : " + account.getMainDeck();
        text += "\nall decks : " + account.getDecks();
        ((TextArea) parent.lookup("#desc")).setText(text);
        stage.setTitle("Account Information");
        stage.setScene(scene);
        stage.show();
    }

    private Button getShopBtn() {
        Button button = new Button("Show Shop");
        button.setLayoutX(150);
        button.setLayoutY(200);
        button.setOnMouseClicked(mouseEvent -> {
            try {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/serverShop.fxml"));
                Parent parent = fxmlLoader.load();
                Group group = new Group();
                group.getChildren().addAll(parent);
                Scene scene = new Scene(group);
                Button refresh = ((Button) parent.lookup("#refresh"));
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
                    + "\nnumbers remaining : " + shop.cardNumbers.get(card1));
        });
        items.setOnMouseClicked(mouseEvent -> {
            String item = items.getSelectionModel().getSelectedItem();
            Item card1 = shop.searchItemByName(item);
            desc.setText(card1.getName() + "\n" + "cost : " + card1.getCost()
                    + "\nnumbers remaining : " + shop.itemNumbers.get(card1));
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
}
