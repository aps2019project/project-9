package view;


import model.Account;
import model.enumerations.AccountErrorType;

public class AccountView {
    private static final AccountView ACCOUNT_VIEW = new AccountView();

    private AccountView(){}

    public static AccountView getInstance(){return ACCOUNT_VIEW;}

    public void printError(AccountErrorType errorType){
        System.out.println(errorType.getMessage());
    }

    public void showLeaderBoards(){
        int counter = 1;
        for (Account account : Account.getAccounts()) {
            System.out.printf("%d - UserName : %s - Wins : %d\n"
                    ,counter++,account.getUserName(),account.getNumberOfWins());
        }
    }

    public void showHelp(){
        System.out.println("<<----- Account Menu ----->>");
        System.out.println("create account [username] ( creating a new account by entering a username and password )" );
        System.out.println("login [username] ( login to your account by entering username and password )");
        System.out.println("show leaderboards");
        System.out.println("help ( show this list )");
    }
}
