import controller.AccountController;

public class Main {
    public static void main(String[] args){
        AccountController accountController = new AccountController();
        try{
            accountController.main();
        } catch (StringIndexOutOfBoundsException e){
            System.out.println("Invalid Command");
        }

    }
}
