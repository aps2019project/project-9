package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
        root.getChildren().addAll(label, button);
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
