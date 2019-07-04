package controller;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import data.JsonProcess;
import javafx.stage.Stage;
import server.Account;
import model.enumerations.AccountErrorType;
import view.AccountMenu;
import view.AccountRequest;

import java.io.FileNotFoundException;

public class AccountController {
    private static AccountMenu accountMenu = AccountMenu.getInstance();


    public void start(Stage stage) throws FileNotFoundException {
        accountMenu.start(stage, this);
    }

    public void login(AccountRequest request) {
        Stage stage = new Stage();
        if (!isUserNameValid(request)) {

            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.IS_PASS_VALID);
            clientRequest.setAccountRequest(request);
            Client.sendRequest(clientRequest);
            String response = Client.getResponse();

            if (response.equals("true")) {
                ClientRequest newRequest = new ClientRequest(Client.getAuthToken(), RequestType.FIND_ACCOUNT);
                newRequest.setAccountRequest(request);
                Client.sendRequest(newRequest);
                String json = Client.getResponse();
                Account account = JsonProcess.getGson().fromJson(json, Account.class);
                ClientRequest clientRequest1 = new ClientRequest(Client.getAuthToken(), RequestType.LOGGED_IN);
                clientRequest1.setLoggedInUserName(account.getUserName());
                Client.sendRequest(clientRequest1);
                goNextMenu(account, stage);
                //goNextMenu(Account.findAccount(request.getUserName()), stage);
            } else
                accountMenu.printError(AccountErrorType.INVALID_PASSWORD);

        } else
            accountMenu.printError(AccountErrorType.INVALID_USERNAME);
    }

    public void createAccount(AccountRequest request) {
        if (isUserNameValid(request)) {
            Stage stage = new Stage();
            ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.CREATE_ACCOUNT);
            clientRequest.setAccountRequest(request);
            Client.sendRequest(clientRequest);
            //Account newAccount = new Account(request.getUserName(), request.getPassWord());
            Account newAccount = JsonProcess.getGson().fromJson(Client.getResponse(), Account.class);
            ClientRequest clientRequest1 = new ClientRequest(Client.getAuthToken(), RequestType.LOGGED_IN);
            clientRequest1.setLoggedInUserName(newAccount.getUserName());
            Client.sendRequest(clientRequest1);
            goNextMenu(newAccount, stage);
        } else {
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountMenu.printError(request.getErrorType());
        }
    }

    private boolean isUserNameValid(AccountRequest request) {
        ClientRequest clientRequest = new ClientRequest(Client.getAuthToken(), RequestType.IS_USER_VALID);
        clientRequest.setAccountRequest(request);
        Client.sendRequest(clientRequest);
        String response = Client.getResponse();
        //TODO
        System.out.println(response);
        if (response.equals("true"))
            return true;
        else {
            return false;
        }
        /*if (Account.isUserNameToken(request.getUserName())) {
            request.setErrorType(AccountErrorType.USERNAME_EXISTS);
            accountMenu.printError(request.getErrorType());
            return false;
        }
        return true;*/
    }

    private void goNextMenu(Account loggedInAccount, Stage stage) {
        Client.sendRequest(new ClientRequest(Client.getAuthToken(), RequestType.NEXT));
        MainMenuController mainMenuController = MainMenuController.getInstance(loggedInAccount);
        mainMenuController.start(stage);
    }

}
