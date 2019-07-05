package client;

import com.google.gson.Gson;
import controller.AccountController;
import data.JsonProcess;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Cell;
import model.MultiPlayerBattle;
import model.enumerations.ItemName;
import server.Account;
import view.AccountMenu;
import view.AccountRequest;
import view.GraphicalInGameView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


public class Client extends Application {
    private static final int DEFAULT_PORT = 8000;
    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;
    private static String authToken;
    private static Gson gson = new Gson();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        if (connectToServer()) {
            AccountMenu a = AccountMenu.getInstance();
            try {
                a.start(primaryStage, new AccountController());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //TODO
            System.out.println("not connected");
            System.out.println("there is no active server");
        }
    }

    private static boolean connectToServer() {
        try {
            Socket socket = new Socket(InetAddress.getLocalHost(), getPort());
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            authToken = inputStream.readUTF();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int getPort() {
        try {
            FileReader reader = new FileReader("src/server/config.txt");
            int port = reader.read();
            reader.close();
            return port;
        } catch (IOException e) {
            e.printStackTrace();
            return DEFAULT_PORT;
        }
    }

    public static String getAuthToken() {
        return authToken;
    }

    public static void sendRequest(ClientRequest request) {
        try {
            outputStream.writeUTF(gson.toJson(request, ClientRequest.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getResponse() {
        try {
            return inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Account getAccount(String userName) {
        ClientRequest clientRequest = new ClientRequest(authToken, RequestType.FIND_ACCOUNT);
        AccountRequest request = new AccountRequest();
        request.setUserName(userName);
        clientRequest.setAccountRequest(request);
        sendRequest(clientRequest);
        String response = getResponse();
        return JsonProcess.getGson().fromJson(response, Account.class);
    }

    public static void saveAccount() {
        ClientRequest clientRequest = new ClientRequest(authToken, RequestType.SAVE_ACCOUNT);
        sendRequest(clientRequest);
    }

    public static Thread getWaitingThread(Stage previous, Stage stage) {
        return new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    try {
                        if (inputStream.available() > 0) {
                            if (inputStream.readUTF().equals("game start")) {
                                previous.close();
                                //TODO
                                MultiPlayerBattle battle = getBattleFromServer();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }

    private static MultiPlayerBattle getBattleFromServer() {
        ClientRequest clientRequest = new ClientRequest(authToken, RequestType.BATTLE);
        sendRequest(clientRequest);
        MultiPlayerBattle battle = JsonProcess.getGson().fromJson(getResponse(), MultiPlayerBattle.class);
        HashMap<ItemName, Cell> collectibles = getCollectibles();
        ArrayList<Cell> flags = getFlags();
        return battle;
    }

    private static HashMap<ItemName, Cell> getCollectibles() {

    }

    private static ArrayList<Cell> getFlags() {

    }
}
