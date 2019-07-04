package server;

import javafx.application.Application;
import javafx.application.Platform;
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
        Button shop = getShopBtn();
        button.setLayoutX(200);
        button.setLayoutY(50);
        setBtnAction(button);
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
        root.getChildren().addAll(label, button, shop);
        primaryStage.setTitle("Server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setBtnAction(Button button) {
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
