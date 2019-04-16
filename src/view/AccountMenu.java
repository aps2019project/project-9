package view;

import model.Account;

import java.util.ArrayList;
import java.util.Scanner;

import static model.Account.*;

public class AccountMenu {
    private void createAccount(String userName, Scanner scanner) {
        if (!isUserNameValid(userName)) {
            System.out.println("An Account exists with this username!");
        } else {
            System.out.print("Please Enter A Password : ");
            String password = scanner.next();
            getAccounts().add(new Account(userName, password));
            System.out.println("Your account created!!");
        }
    }

    private void login(String username, Scanner scanner) {         //not complete
        if (!isUserNameValid(username)) {
            System.out.println("There isnt any account with this username.");
        } else {
            System.out.print("Please Enter Your Password : ");
            String password = scanner.next();
            if (isPassWordValid(username, password))
                Account.login(username,password);
            else {
                System.out.println("Your Entered passWord is Wrong.");
            }
        }
    }

    private void showLeaderBoards() {
        sortAccounts();
        int counter = 1;
        String string = new String("");
        for (Account key : getAccounts()) {
            string += counter + "-UserName : " + key.getUserName() + "-Wins : " + key.getNumberOfWins() + "\n";
        }
        System.out.println(string);
    }

    public void getInput() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        String[] temp = new String[10];
        if (str.contains("create account ")) {
            temp = str.split(" ");
            createAccount(temp[2], scanner);
        } else if (str.contains("login")) {
            temp = str.split(" ");
            login(temp[1],scanner);
        } else if (str.matches("show\\sleaderboard")) {
            showLeaderBoards();
        } else if (str.matches("save")) {
            //not complete
        } else if (str.matches("logout")) {
            logOut();
        } else if (str.matches("help"))
            showHelp();

    }

    public void showMenu() {
        //not complete
    }

    private void showHelp() {
        System.out.println("Help :\nCreate Account [username]\nLogin [username]\nShow LeaderBoard\nsave\nlogout");
    }

    private void logOut() {
        //not complete
    }
}
