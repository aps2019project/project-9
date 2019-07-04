package client;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GlobalChat {

    public static void showChat() {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 400, 400);
        ListView<String> chat = new ListView<>();
        chat.setPrefSize(250, 300);
        TextField textField = getTextField();
        root.getChildren().addAll(textField, chat);
        Thread thread = getChatRefreshThread(chat);
        thread.setDaemon(true);
        thread.start();
        scene.setOnMouseExited(mouseEvent -> thread.interrupt());
        stage.setTitle("Global Chat");
        stage.setScene(scene);
        stage.show();
    }

    private static TextField getTextField() {
        TextField textField = new TextField();
        textField.setLayoutX(100);
        textField.setLayoutY(320);
        textField.setOnAction(actionEvent -> {
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.SEND_MESSAGE);
            clientRequest.setMessage(textField.getText());
            Client.sendRequest(clientRequest);
            textField.setText("");
        });
        return textField;
    }

    private static Thread getChatRefreshThread(ListView<String> chat) {
        Thread thread = new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                Platform.runLater(() -> {
                    ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.GET_CHAT);
                    Client.sendRequest(clientRequest);
                    String response = Client.getResponse();
                    ArrayList<String> arrayList = new Gson().fromJson(response, new TypeToken<ArrayList<String>>() {
                    }.getType());
                    chat.getItems().clear();
                    chat.getItems().addAll(arrayList);
                });
            }
        });
        return thread;
    }


}
