package server;

import client.Client;
import client.ClientRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import data.JsonProcess;
import model.Account;
import model.BattleResult;
import view.AccountRequest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private String authToken;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;

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
            while (true) {
                accountRequest();
            }
        } catch (IOException e) {
            System.out.println("client disconnected or interrupted");
            e.printStackTrace();
        }
    }

    private Gson gson = new Gson();

    private void accountRequest() throws IOException {
        String received = inputStream.readUTF();
        ClientRequest request = gson.fromJson(received, ClientRequest.class);
        if (request.getAuthKey().equals(authToken)) {
            switch (request.getType()) {
                case CREATE_ACCOUNT:
                    Account account = new Account(request.getAccountRequest().getUserName(),
                            request.getAccountRequest().getPassWord());
                    Gson gson = JsonProcess.getGson();
                    outputStream.writeUTF(gson.toJson(account, Account.class));
                    //TODO
                    System.out.println("account made");
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
                    String toSend = JsonProcess.getGson().toJson(Account.getAccounts()
                            , new TypeToken<ArrayList<Account>>() {
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
            }
        }
    }

    public String getAuthToken() {
        return authToken;
    }
}
