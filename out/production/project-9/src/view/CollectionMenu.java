package view;

import java.util.*;

import controller.CollectionController;
import controller.MainMenuController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.Deck;
import model.cards.Card;
import model.enumerations.CollectionErrorType;

public class CollectionMenu {

    private int count = 0;
    private ImageView slideshowImageView;
    private Account account;
    private CollectionController controller;
    private static boolean isColomnsset = false;
    private static boolean havaBottun = false;

    public CollectionMenu(CollectionController controller) {
        this.account = controller.getLoggedInAccount();
        this.controller = controller;
    }

    public void start() {
        try {
            Stage stage = new Stage();
            Group root = new Group();
            TableView tableView = setTable();

            runSlideShow(root);
            setButtons(root, stage, tableView);
            root.getChildren().add(tableView);
            Scene scene = new Scene(root, 1003, 562);
            stage.setScene(scene);
            scene.getStylesheets().add("src/res/CSS/CollectionButtonStyle.css");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView setTable() {
        TableView tableView = new TableView();
        tableView.setTranslateX(600);
        tableView.setLayoutY(30);
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
        slideshowImageView.setFitHeight(562);
        slideshowImageView.setFitWidth(1003);
        root.getChildren().addAll(slideshowImageView);
    }

    private void setButtons(Group root, Stage stage, TableView tableView) {
        int addX = 20;
        int addY = 45;
        int startX = 20;
        int startY = 190;


        Button selectDeck = new Button("Select Deck");
        selectDeck.setScaleX(0.8);
        selectDeck.setScaleY(0.8);
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
        createDeck.setScaleX(0.8);
        createDeck.setScaleY(0.8);
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
        deleteDeck.setScaleX(0.8);
        deleteDeck.setScaleY(0.8);
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
        showAllDecks.setScaleX(0.8);
        showAllDecks.setScaleY(0.8);
        showAllDecks.setLayoutX(startX + 3 * addX);
        showAllDecks.setLayoutY(startY + 3 * addY);

        showAllDecks.setOnMouseClicked(m -> {
            showAllDecksTable(tableView);
        });

        Button showDeck = new Button("Show Deck");
        showDeck.setScaleX(0.8);
        showDeck.setScaleY(0.8);
        showDeck.setLayoutX(startX + 4 * addX);
        showDeck.setLayoutY(startY + 4 * addY);

        showDeck.setOnMouseClicked(m -> {
            showDeckTable(tableView);
        });

        Button save = new Button("Save");
        save.setScaleX(0.8);
        save.setScaleY(0.8);
        save.setLayoutX(startX + 5 * addX);
        save.setLayoutY(startY + 5 * addY);

        save.setOnMouseClicked(m -> {
            //TODO
        });
        Button help = new Button("help");
        help.setScaleX(0.8);
        help.setScaleY(0.8);
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
        back.setScaleX(0.8);
        back.setScaleY(0.8);
        back.setLayoutX(startX + 7 * addX);
        back.setLayoutY(startY + 7 * addY);
        back.setOnMouseClicked(m -> stage.close());

        Button showCollection = new Button("show\nCollection");
        selectDeck.setScaleX(0.9);
        selectDeck.setScaleY(0.9);
        showCollection.setStyle("-fx-background-color: \n " +
                "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                "    -fx-background-radius: 8,7,6;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        showCollection.setLayoutX(35);
        showCollection.setLayoutY(480);
        showCollection.setOnMouseClicked(m -> showCollectionTable(tableView));

        root.getChildren().addAll(selectDeck, createDeck, deleteDeck, showAllDecks, showDeck, save, help, back,
                showCollection);
    }

    private void showCollectionTable(TableView tableView) {
        if (!havaBottun) {
            setColumns(tableView);
            havaBottun = true;
        }
        if (!isColomnsset) {
            addAddButtonToTable(tableView);
            isColomnsset = true;
        }
        tableView.getItems().clear();
        tableView.getItems().addAll(FXCollections.observableArrayList(account.getCollection().getCards()));
        tableView.getItems().addAll(FXCollections.observableArrayList(account.getCollection().getItems()));
    }

    private void showDeckTable(TableView tableView) {
        List<String> choices = new ArrayList<>();
        for (int i = 0; i < account.getDecks().size(); i++) {
            choices.add(account.getDecks().get(i).getName());
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(account.getMainDeck().getName(), choices);
        dialog.setTitle("Choice Deck");
        dialog.setHeaderText("Please Choose a deck");
        dialog.setContentText("Decks :");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(letter -> {
            setupColumns(tableView);
            Deck temp = account.findDeckByName(letter);
            showDeck(tableView, temp);
        });
    }

    private void setupColumns(TableView tableView) {
        if (havaBottun) {
            tableView.getColumns().remove(6);
            havaBottun = false;
        }
        if (!isColomnsset) {
            setColumns(tableView);
            isColomnsset = true;
        }
        tableView.getItems().clear();
    }

    private void setColumns(TableView tableView) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setMaxWidth(100);

        TableColumn<String, Card> column2 = new TableColumn<>("card Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("cardType"));
        column2.setMaxWidth(100);

        TableColumn<String, Card> column3 = new TableColumn<>("price");
        column3.setCellValueFactory(new PropertyValueFactory<>("cost"));
        column2.setMaxWidth(100);

        TableColumn<String, Card> column4 = new TableColumn<>("MP");
        column4.setCellValueFactory(new PropertyValueFactory<>("MP"));
        column2.setMaxWidth(100);

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column2.setMaxWidth(100);
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));
        column5.setMaxWidth(300);

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
    }

    private void showDeck(TableView tableView, Deck temp) {
        tableView.getItems().addAll(FXCollections.observableArrayList(new Card(temp.getDeckCost(),
                temp.getAvarageMana(), null, 0, temp.getName(), "")));

        tableView.getItems().addAll(FXCollections.observableArrayList(temp.getCards()));
        tableView.getItems().addAll(FXCollections.observableArrayList(temp.getItem()));
    }

    private void showAllDecksTable(TableView tableView) {
        setupColumns(tableView);
        for (int i = 0; i < account.getDecks().size(); i++) {
            Deck temp = account.getDecks().get(i);
            showDeck(tableView, temp);
        }
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

    private void addAddButtonToTable(TableView table) {
        TableColumn actionCol = new TableColumn("Add");
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));
        Callback<TableColumn<Card, String>, TableCell<Card, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Card, String> param) {
                final TableCell<Card, String> cell = new TableCell<>() {
                    final Button btn = new Button("Add");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Card card = getTableView().getItems().get(getIndex());
                                List<String> c = new ArrayList<>();
                                for (Deck key : account.getDecks()) {
                                    c.add(key.getName());
                                }
                                ChoiceDialog<String> dialog = new ChoiceDialog<>(
                                        account.getMainDeck().getName() + "", c);
                                dialog.setTitle("Choice Deck");
                                dialog.setHeaderText("Choose a deck to add this card to");
                                dialog.setContentText("Decks :");

                                Optional<String> result = dialog.showAndWait();
                                result.ifPresent(letter -> {
                                    controller.add(letter, card.getName());
                                });
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        table.getColumns().add(actionCol);
    }

}