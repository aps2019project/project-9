package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Stage;
import model.Account;
import model.Deck;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleMenu {
    public void start(Stage stage) throws IOException {
        stage.setMaximized(true);

        FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\BattleMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 100, 100);
        stage.setScene(scene);

        stage.show();
    }

    public void storyCustom(Stage stage) throws IOException {
        stage.setMaximized(true);

        FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\StoryCustom.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 1300, 600);
        stage.setScene(scene);

        stage.show();
    }

    public void storyMenu(Stage stage) throws IOException {
        stage.setMaximized(true);

        FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\StoryMenu.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent, 1300, 600);
        stage.setScene(scene);

        stage.show();
    }

    void customGamePresed(Account loggedInAccount){
        List<String> choices = new ArrayList<>();
        for (Deck deck : loggedInAccount.getDecks()) {
            choices.add(deck.getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(loggedInAccount.getMainDeck().getName(), choices);
        dialog.setTitle("Choice Your Deck");
        dialog.setHeaderText("Please Choice the deck you wanna play with");
        dialog.setContentText("Deck:");
        Optional<String> result = dialog.showAndWait();


        //TODO
        //result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }
    void multiPlayerPreesed(Account loggedAccount){
        List<String> choices = new ArrayList<>();
        for (Account account : Account.getAccounts()) {
            if (!account.getUserName().equals(loggedAccount.getUserName()))
                choices.add(account.getUserName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Choice Your Opponent");
        dialog.setHeaderText("Please Choice the opponent you wanna play with");
        dialog.setContentText("User Name:");
        Optional<String> result = dialog.showAndWait();


        //TODO
        //result.ifPresent(letter -> System.out.println("Your choice: " + letter));
    }
}
