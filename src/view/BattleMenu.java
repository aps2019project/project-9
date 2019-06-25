package view;

import controller.BattleMenuController;
import controller.MainMenuController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.enumerations.BattleMenuErrorType;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BattleMenu {
    private Account logInAccount;
    private BattleMenuController controller;

    public BattleMenu(Account logInAccount, BattleMenuController controller) {
        this.logInAccount = logInAccount;
        this.controller = controller;
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\BattleMenu.fxml"));
            Parent parent = loader.load();
            setButttomnsEventbattle(parent, stage);
            Scene scene = new Scene(parent, 1003, 562);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setButttomnsEventbattle(Parent parent, Stage stage) {
        Button multi = (Button) parent.lookup("#multi");
        setActionForButtons(multi);
        multi.setOnMouseClicked(mouseEvent -> multiPlayerPressed());
        Button single = (Button) parent.lookup("#single");
        setActionForButtons(single);
        single.setOnMouseClicked(mouseEvent -> singlePlayerPressed(stage));
        Button back = (Button) parent.lookup("#back");
        back.setOnMouseClicked(mouseEvent -> MainMenuController.getInstance(logInAccount).start(stage));
    }

    private void singlePlayerPressed(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\StoryCustom.fxml"));
            Parent parent = loader.load();
            setButttomnsEventsingle(parent, stage);
            Scene scene = new Scene(parent, 1003, 562);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setButttomnsEventsingle(Parent parent, Stage stage) {
        Button story = (Button) parent.lookup("#story");
        setActionForButtons(story);
        story.setOnMouseClicked(mouseEvent -> storyMenuShow(stage));
        Button custom = (Button) parent.lookup("#custom");
        setActionForButtons(custom);
        custom.setOnMouseClicked(mouseEvent -> customGamePressed(stage));
        Button back = (Button) parent.lookup("#back");
        back.setOnMouseClicked(mouseEvent -> start(stage));
    }

    private void storyMenuShow(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\StoryMenu.fxml"));
            Parent parent = loader.load();
            setStoryButtons(parent, stage);
            Scene scene = new Scene(parent, 1003, 562);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setStoryButtons(Parent parent, Stage stage) {
        Button easy = (Button) parent.lookup("#easy");
        easy.setOnMouseClicked(mouseEvent -> {
            if (logInAccount.getMainDeck().isValid())
                controller.startStoryModeGame(1, stage);
            else {
                printError(BattleMenuErrorType.YOUR_MAIN_DECK_NOT_VALID, stage);
            }
        });
        setActionForButtons(easy);
        Button inter = (Button) parent.lookup("#intermediate");
        inter.setOnMouseClicked(mouseEvent -> {
            if (logInAccount.getMainDeck().isValid())
                controller.startStoryModeGame(2, stage);
            else {
                printError(BattleMenuErrorType.YOUR_MAIN_DECK_NOT_VALID, stage);
            }
        });
        setActionForButtons(inter);
        Button hard = (Button) parent.lookup("#hard");
        hard.setOnMouseClicked(mouseEvent -> {
            if (logInAccount.getMainDeck().isValid())
                controller.startStoryModeGame(3, stage);
            else {
                printError(BattleMenuErrorType.YOUR_MAIN_DECK_NOT_VALID, stage);
            }
        });
        setActionForButtons(hard);
        Button back = (Button) parent.lookup("#back");
        back.setOnMouseClicked(mouseEvent -> singlePlayerPressed(stage));
    }

    private void customGamePressed(Stage stage) {
        List<String> choices = new ArrayList<>();
        for (Deck deck : logInAccount.getDecks()) {
            choices.add(deck.getName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(logInAccount.getMainDeck().getName(), choices);
        dialog.setTitle("Custom Game");
        dialog.setHeaderText("Please Select the Deck You wanna play with");
        dialog.setContentText("Deck:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(letter -> {
            List<String> modes = new ArrayList<>();
            modes.add("Hero Killed");
            modes.add("multi Flags");
            modes.add("One Flag");

            ChoiceDialog<String> d = new ChoiceDialog<>("", modes);
            d.setTitle("Custom Game");
            d.setHeaderText("Please Select the Game Type");
            d.setContentText("Types:");
            Optional<String> r = d.showAndWait();
            r.ifPresent(l -> handleGameTypes(l, letter, stage));
        });
    }

    private void handleGameTypes(String string, String deckName, Stage stage) {
        switch (string) {
            case "Hero Killed":
                if (logInAccount.findDeckByName(deckName).isValid())
                    controller.startCustomGame(deckName, 1, 0, stage);
                else {
                    printError(BattleMenuErrorType.DECK_NOT_VALID, stage);
                }
                break;
            case "One Flag":
                if (logInAccount.findDeckByName(deckName).isValid())
                    controller.startCustomGame(deckName, 2, 1, stage);
                else {
                    printError(BattleMenuErrorType.DECK_NOT_VALID, stage);
                }
                break;
            case "multi Flags":
                List<Integer> c = new ArrayList<>();
                for (int i = 1; i < 31; i++) {
                    c.add(i);
                }
                ChoiceDialog<Integer> dio = new ChoiceDialog<>(1, c);
                dio.setTitle("Custom Game");
                dio.setHeaderText("Please Select number of flags");
                dio.setContentText("number:");
                Optional<Integer> result1 = dio.showAndWait();

                result1.ifPresent(p -> {
                    if (logInAccount.findDeckByName(deckName).isValid())
                        controller.startCustomGame(deckName, 3, p, stage);
                    else {
                        printError(BattleMenuErrorType.DECK_NOT_VALID, stage);
                    }
                });
                break;
        }
    }

    private void multiPlayerPressed() {
        List<String> choices = new ArrayList<>();
        for (Account account : Account.getAccounts()) {
            if (!account.getUserName().equals(logInAccount.getUserName()))
                choices.add(account.getUserName());
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("Choice Your Opponent");
        dialog.setHeaderText("Please Choice the opponent you wanna play with");
        dialog.setContentText("User Name:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(letter ->
                new Alert(Alert.AlertType.WARNING, "sorry this feature isn't available now").showAndWait());
    }

    private void printError(BattleMenuErrorType error, Stage stage) {
        switch (error) {
            case YOUR_MAIN_DECK_NOT_VALID:
                new Alert(Alert.AlertType.WARNING, "Your Main Deck is not valid");
                storyMenuShow(stage);
                break;
            case DECK_NOT_VALID:
                new Alert(Alert.AlertType.WARNING, "Your Selected Deck is not valid");
                singlePlayerPressed(stage);
                break;
        }

    }

    private void setActionForButtons(Button button) {
        String s = button.getStyle();
        double x = button.getLayoutX();
        double y = button.getLayoutY();
        Paint p = button.getTextFill();
        button.setOnMouseEntered(m -> {
            button.setTextFill(Color.rgb(255,0,34));
            button.setLayoutX(button.getLayoutX() - 20);
            button.setLayoutY(button.getLayoutY() - 20);
            button.setStyle("-fx-pref-height:350px;" +
                    "-fx-pref-width: 250px;" +
                    "-fx-background-color: transparent;");
        });
        button.setOnMouseExited(l -> {
            button.setTextFill(p);
            button.setLayoutX(x);
            button.setLayoutY(y);
            button.setStyle(s);
        });
    }
}
