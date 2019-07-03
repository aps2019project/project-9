package client;

import com.google.gson.Gson;
import controller.AccountController;
import data.JsonProcess;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Account;
import view.AccountMenu;
import view.AccountRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


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

    public static void saveAccount(Account account) {
        //TODO
        //it seems there's no need to tell server to save account , client can do it too.
    }
}
