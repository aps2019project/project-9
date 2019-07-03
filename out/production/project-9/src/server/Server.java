package server;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static ArrayList<ClientHandler> onlineClients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            savePort(serverSocket.getLocalPort());
            Socket socket;
            System.out.println("waiting for client to connected ...");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("new client connected");
                ClientHandler handler = new ClientHandler(generateRandomKey(), socket);
                onlineClients.add(handler);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String generateRandomKey() {
        //TODO
        return "mohsen";
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
