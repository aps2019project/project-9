import controller.AccountController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.AccountMenu;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        AccountMenu a = AccountMenu.getInstance();
        try {
            a.start(primaryStage,new AccountController());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
