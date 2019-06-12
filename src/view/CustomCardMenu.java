package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Account;

public class CustomCardMenu {
    private Account loggedAccount;

    public CustomCardMenu(Account loggedAccount){
        this.loggedAccount = loggedAccount;
    }

    public void start(){
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root,1003,562);
        stage.setScene(scene);
        stage.setTitle("Custom Card Menu");
        stage.getIcons().add(new Image("file:src/res/icon.jpg"));

        stage.show();
    }


}
