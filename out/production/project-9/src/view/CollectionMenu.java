package view;

import java.util.*;

import controller.CollectionController;
import controller.MainMenuController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Account;
import model.Deck;
import model.enumerations.CollectionErrorType;

public class CollectionMenu {

    private int count = 0;
    private ImageView slideshowImageView;
    private Account account;
    private CollectionController controller;

    public CollectionMenu(CollectionController controller) {
        this.account = controller.getLoggedInAccount();
        this.controller = controller;
    }

    public void start(Stage stage) {
        try {
            stage.setMaximized(true);

            Group root = new Group();
            TableView tableView = setTable();

            runSlideShow(root);
            setButtons(root, stage, tableView);
            root.getChildren().add(tableView);
            Scene scene = new Scene(root, 1000, 800);
            stage.setScene(scene);
            scene.getStylesheets().add("src/res/CSS/CollectionButtonStyle.css");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView setTable() {
        TableView tableView = new TableView();
        tableView.setTranslateX(900);
        return tableView;
    }

    private void runSlideShow(Group root) {
        //change this if you wanna add picture
        final int size = 4;
        long delay = 7000;

        ArrayList<Image> images = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            images.add(new Image("/src/res/collection-menu-images/" + i + ".jpg"));
        }
        slideshowImageView = new ImageView(images.get(0));
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                slideshowImageView.setImage(images.get(count++));
                if (count >= images.size()) {
                    count = 0;
                }
            }
        }, 0, delay);
        slideshowImageView.setFitHeight(810);
        slideshowImageView.setFitWidth(1600);
        root.getChildren().addAll(slideshowImageView);
    }

    private void setButtons(Group root, Stage stage, TableView t) {
        int addX = 30;
        int addY = 60;
        int startX = 30;
        int startY = 320;

        Button selectDeck = new Button("Select Deck");
        selectDeck.setLayoutX(startX);
        selectDeck.setLayoutY(startY);

        selectDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Select main Deck");
            dio.setHeaderText("Select your main deck");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();

            result1.ifPresent(p -> controller.selectDeck(p));
        });

        Button createDeck = new Button("Create Deck");
        createDeck.setLayoutX(startX + addX);
        createDeck.setLayoutY(startY + addY);

        createDeck.setOnMouseClicked(m -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create Deck");
            dialog.setHeaderText("Please enter Deck name");
            dialog.setContentText("Name :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> controller.createDeck(name));
        });

        Button deleteDeck = new Button("Delete Deck");
        deleteDeck.setLayoutX(startX + 2 * addX);
        deleteDeck.setLayoutY(startY + 2 * addY);

        deleteDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Delete deck");
            dio.setHeaderText("Select deck you wanna delete");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();
            result1.ifPresent(p -> controller.deleteDeck(p));
        });

        Button showAllDecks = new Button("Show All Decks");
        showAllDecks.setLayoutX(startX + 3 * addX);
        showAllDecks.setLayoutY(startY + 3 * addY);

        showAllDecks.setOnMouseClicked(m -> {

        });

        Button showDeck = new Button("Show Deck");
        showDeck.setLayoutX(startX + 4 * addX);
        showDeck.setLayoutY(startY + 4 * addY);

        Button save = new Button("Save");
        save.setLayoutX(startX + 5 * addX);
        save.setLayoutY(startY + 5 * addY);

        save.setOnMouseClicked(m -> {
            //TODO
        });
        Button help = new Button("help");
        help.setLayoutX(startX + 6 * addX);
        help.setLayoutY(startY + 6 * addY);

        help.setOnMouseClicked(m -> {
            new Alert(Alert.AlertType.INFORMATION,
                    "show Collection ---> show collection\n" +
                            "create deck --> create a deck\n" +
                            "delete deck --> delete a deck\n" +
                            "select deck --> set a deck for MainDeck Assigning \n" +
                            "show all decks --> show all of your decks\n" +
                            "show deck --> show a deck by name\n" +
                            "save --> save your changes\n" +
                            "exit --> back to main menu\n").showAndWait();
        });

        Button back = new Button("Exit");
        back.setLayoutX(startX + 7 * addX);
        back.setLayoutY(startY + 7 * addY);
        back.setOnMouseClicked(m -> MainMenuController.getInstance().start(stage));

        Button triangleButton = new Button("show\nCollection");
        triangleButton.setStyle("-fx-background-color: \n " +
                "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                "    -fx-background-radius: 8,7,6;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        triangleButton.setLayoutX(40);
        triangleButton.setLayoutY(700);

        root.getChildren().addAll(selectDeck, createDeck, deleteDeck, showAllDecks, showDeck, save, help, back,
                triangleButton);
    }

    public void printError(CollectionErrorType error) {
        switch (error) {
            case DECK_NAME_EXISTS:
                new Alert(Alert.AlertType.WARNING, error.getMessage());
                break;
            case DECK_CREATED:
                new Alert(Alert.AlertType.INFORMATION, error.getMessage());
                break;
            case DECK_NOT_VALID:
                new Alert(Alert.AlertType.WARNING, error.getMessage());
                break;
        }
    }

    private ChoiceDialog<String> setDecksList() {
        List<String> c = new ArrayList<>();
        for (Deck key : account.getDecks()) {
            c.add(key.getName());
        }
        return new ChoiceDialog<>(account.getMainDeck().getName(), c);
    }
}