package server;

import client.ClientRequest;
import client.ShortAccount;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.JsonProcess;
import model.BattleResult;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private String authToken;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private String userName;

    public ClientHandler(String key, Socket socket) {
        this.authToken = key;
        try {
            this.outputStream = new DataOutputStream(socket.getOutputStream());
            this.inputStream = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            outputStream.writeUTF(authToken);
            accountRequest();
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("client disconnected or interrupted , this exception is OK");
            GraphicalServer.onlineClients.remove(this);
            if (userName != null) {
                GraphicalServer.userNamesLoggedIn.remove(userName);
            }
        }
    }

    @Override
    public String toString() {
        return authToken;
    }

    private Gson gson = new Gson();

    private void accountRequest() throws IOException {
        while (true) {
            String received = inputStream.readUTF();
            ClientRequest request = gson.fromJson(received, ClientRequest.class);
            if (request.getAuthKey().equals(authToken)) {
                switch (request.getType()) {
                    case CREATE_ACCOUNT:
                        Account account = new Account(request.getAccountRequest().getUserName(),
                                request.getAccountRequest().getPassWord());
                        Gson gson = JsonProcess.getGson();
                        outputStream.writeUTF(gson.toJson(account, Account.class));
                        break;
                    case IS_USER_VALID:
                        if (Account.isUserNameToken(request.getAccountRequest().getUserName()))
                            outputStream.writeUTF("false");
                        else
                            outputStream.writeUTF("true");
                        break;
                    case IS_PASS_VALID:
                        if (Account.isPassWordValid(request.getAccountRequest().getUserName(),
                                request.getAccountRequest().getPassWord()))
                            outputStream.writeUTF("true");
                        else
                            outputStream.writeUTF("false");
                        break;
                    case FIND_ACCOUNT:
                        String userName = request.getAccountRequest().getUserName();
                        outputStream.writeUTF(JsonProcess.getGson().
                                toJson(Account.findAccount(userName), Account.class));
                        break;
                    case ACCOUNT_LIST:
                        ArrayList<ShortAccount> userNames = new ArrayList<>();
                        for (Account account1 : Account.getAccounts()) {
                            if (GraphicalServer.userNamesLoggedIn.contains(account1.getUserName()))
                                userNames.add(new ShortAccount(account1, "online"));
                            else
                                userNames.add(new ShortAccount(account1, "offline"));
                        }
                        String toSend = JsonProcess.getGson().toJson(userNames
                                , new TypeToken<ArrayList<ShortAccount>>() {
                                }.getType());
                        outputStream.writeUTF(toSend);
                        break;
                    case BATTLE_RESULT_LIST:
                        userName = request.getAccountRequest().getUserName();
                        Account account1 = Account.findAccount(userName);
                        toSend = JsonProcess.getGson().toJson(account1.getBattleResults(),
                                new TypeToken<ArrayList<BattleResult>>() {
                                }.getType());
                        outputStream.writeUTF(toSend);
                        break;
                    case LOGGED_IN:
                        GraphicalServer.userNamesLoggedIn.add(request.getLoggedInUserName());
                        this.userName = request.getLoggedInUserName();
                        break;
                    case GET_CHAT:
                        outputStream.writeUTF(new Gson().toJson(GraphicalServer.globalChat,
                                new TypeToken<ArrayList<String>>() {
                                }.getType()));
                        break;
                    case SEND_MESSAGE:
                        String message = request.getMessage();
                        if (this.userName != null)
                            GraphicalServer.globalChat.add(this.userName + " : " + message);
                        break;
                    case ONLINE_PLAYERS:
                        outputStream.writeUTF(new Gson().toJson(GraphicalServer.userNamesLoggedIn,
                                new TypeToken<ArrayList<String>>() {
                                }.getType()));
                        break;
                }
            }
        }
    }


    public String getAuthToken() {
        return authToken;
    }
}
