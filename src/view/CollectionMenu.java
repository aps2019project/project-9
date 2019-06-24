package view;

import java.net.URL;
import java.util.*;

import controller.CollectionController;
import data.DeckAddException;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Account;
import model.Deck;
import data.JsonProcess;
import model.cards.Card;
import model.enumerations.CardType;
import model.enumerations.CollectionErrorType;
import model.items.Item;

import static model.enumerations.CollectionErrorType.*;

public class CollectionMenu {

    private int count = 0;
    private ImageView slideshowImageView;
    private Account account;
    private CollectionController controller;
    private static boolean havebutton = false;

    public CollectionMenu(CollectionController controller) {
        this.account = controller.getLoggedInAccount();
        this.controller = controller;
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\collection.fxml"));
            Parent parent = loader.load();

            TableView cardtable = (TableView) parent.lookup("#cardtable");
            TableView itemtable = (TableView) parent.lookup("#itemtable");
            runSlideShow(parent);
            setButtons(parent, stage, cardtable, itemtable);
            setColumns(cardtable, itemtable);
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            scene.getStylesheets().add("src/res/CSS/CollectionButtonStyle.css");
            stage.getIcons().add(new Image("file:src/res/icon.jpg"));
            stage.setTitle("Collection Menu");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runSlideShow(Parent root) {
        //change this if you wanna add picture
        final int size = 4;
        long delay = 7000;

        ArrayList<Image> images = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            images.add(new Image("/src/res/collection-menu-images/" + i + ".jpg"));
        }
        slideshowImageView = (ImageView) root.lookup("#imageview");
        //slideshowImageView = new ImageView(images.get(0));
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                slideshowImageView.setImage(images.get(count++));
                if (count >= images.size()) {
                    count = 0;
                }
            }
        }, 0, delay);
    }

    private ChoiceDialog<String> getSavedDecksNames() {
        List<String> sample = JsonProcess.getSavedDecksNames();
        return new ChoiceDialog<>(null, sample);
    }

    private void setExportImportBtns(Parent root) {
        //////////////////////deck export importing process//////////////////
        //TODO export import buttons ( layouts Remained )->
        Button exportDeck = (Button) root.lookup("#exportdeck");
        Button importDeck = (Button) root.lookup("#importdeck");
        exportDeck.setOnMouseEntered(mouseEvent -> exportDeck.setLayoutX(exportDeck.getLayoutX() + 20));
        exportDeck.setOnMouseExited(mouseEvent -> exportDeck.setLayoutX(exportDeck.getLayoutX() - 20));
        importDeck.setOnMouseEntered(mouseEvent -> importDeck.setLayoutX(importDeck.getLayoutX() + 20));
        importDeck.setOnMouseExited(mouseEvent -> importDeck.setLayoutX(importDeck.getLayoutX() - 20));
        exportDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Select Deck To Export");
            dio.setHeaderText("Select Deck To Export");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();
            result1.ifPresent(p -> {
                try {
                    JsonProcess.exportDeckFromAccount(p, account);
                    //TODO
                    System.out.println(p + " " + account.getUserName());
                } catch (DeckAddException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("A Deck With This Name Has Already Saved");
                    alert.show();
                }
            });
        });
        importDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = getSavedDecksNames();
            dio.setTitle("Select Deck To Import");
            dio.setHeaderText("Select Deck To Import");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();
            result1.ifPresent(p -> {
                JsonProcess.addSavedDeckToAccount(account, p);
                //TODO
                System.out.println(p + " " + account.getUserName());
            });
        });
        ////////////////////////////////////end/////////////////////////////
    }

    private void setButtons(Parent root, Stage stage, TableView cardtable, TableView itemtable) {
        setExportImportBtns(root);

        Button selectDeck = setSelectButton(root);
        setBtnOnMouseOverAction(selectDeck);
        Button createDeck = setCreateDeckButton(root);
        setBtnOnMouseOverAction(createDeck);
        Button deleteDeck = setDeleteDeckButton(root);
        setBtnOnMouseOverAction(deleteDeck);
        Button showAllDecks = setShowAllDecksButton(root, cardtable, itemtable);
        setBtnOnMouseOverAction(showAllDecks);
        Button showDeck = setShowDeckButton(root, cardtable, itemtable);
        setBtnOnMouseOverAction(showDeck);
        Button save = setSaveButton(root);
        setBtnOnMouseOverAction(save);
        Button search = setSearchButton(root, cardtable, itemtable);
        setBtnOnMouseOverAction(search);
        Button help = setHelpButton(root);
        setBtnOnMouseOverAction(help);
        Button back = setBackButton(root, stage);
        setBtnOnMouseOverAction(back);
        Button validateDeck = setValidateDeck(root);
        setBtnOnMouseOverAction(validateDeck);
        Button showCollection = setShowCollectionButton(root, cardtable, itemtable);
    }

    private void setBtnOnMouseOverAction(Button button) {
        double layoutX = button.getLayoutX();
        button.setOnMouseEntered(mouseEvent -> {
            button.setStyle("-fx-text-fill: blue");
            button.setLayoutX(layoutX + 10);
        });
        button.setOnMouseExited(mouseEvent -> {
            button.setStyle("-fx-text-fill: black");
            button.setLayoutX(layoutX);
        });
    }

    private Button setShowCollectionButton(Parent root, TableView cardtable, TableView itemtable) {
        Button showCollection = (Button) root.lookup("#showcollection");
        showCollection.setStyle("-fx-background-color: \n " +
                "        linear-gradient(#f2f2f2, #d6d6d6),\n" +
                "        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\n" +
                "        linear-gradient(#dddddd 0%, #f6f6f6 50%);\n" +
                "    -fx-background-radius: 8,7,6;\n" +
                "    -fx-background-insets: 0,1,2;\n" +
                "    -fx-text-fill: black;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
        showCollection.setOnMouseClicked(m -> showCollectionTable(cardtable, itemtable));
        return showCollection;
    }

    private Button setValidateDeck(Parent root) {
        Button validate = (Button) root.lookup("#validate");
        validate.setOnMouseClicked(m ->{
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Select A Deck");
            dio.setHeaderText("Select a deck to validate");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();

            result1.ifPresent(p -> controller.selectDeck(p));
        });

        return validate;
    }

    private Button setBackButton(Parent root, Stage stage) {
        Button back = (Button) root.lookup("#back");
        back.setOnMouseClicked(m -> stage.close());
        return back;
    }

    private Button setHelpButton(Parent root) {
        Button help = (Button) root.lookup("#help");

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
        return help;
    }

    private Button setSearchButton(Parent root, TableView cardtable, TableView itemtable) {
        Button search = (Button) root.lookup("#search");
        search.setOnMouseClicked(m -> {
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Search");
            dialog.setHeaderText("Enter Card/item name");
            dialog.setContentText("Enter :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                showCard(cardtable, itemtable, name);
            });
        });
        return search;
    }

    private Button setSaveButton(Parent root) {
        Button save = (Button) root.lookup("#save");

        save.setOnMouseClicked(m -> {
            JsonProcess.saveAccount(account);
        });
        return save;
    }

    private Button setShowDeckButton(Parent root, TableView cardtable, TableView itemtable) {
        Button showDeck = (Button) root.lookup("#showdeck");

        showDeck.setOnMouseClicked(m -> {
            showDeckTable(cardtable, itemtable);
        });
        return showDeck;
    }

    private Button setShowAllDecksButton(Parent root, TableView cardtable, TableView itemtable) {
        Button showAllDecks = (Button) root.lookup("#showalldecks");
        showAllDecks.setOnMouseClicked(m -> {
            showAllDecksTable(cardtable, itemtable);
        });
        return showAllDecks;
    }

    private Button setDeleteDeckButton(Parent root) {
        Button deleteDeck = (Button) root.lookup("#deletedeck");
        deleteDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Delete deck");
            dio.setHeaderText("Select deck you wanna delete");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();
            result1.ifPresent(p -> controller.deleteDeck(p));
        });
        return deleteDeck;
    }

    private Button setCreateDeckButton(Parent group) {
        Button createDeck = (Button) group.lookup("#createdeck");

        createDeck.setOnMouseClicked(m -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Create Deck");
            dialog.setHeaderText("Please enter Deck name");
            dialog.setContentText("Name :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> controller.createDeck(name));
        });
        return createDeck;
    }

    private Button setSelectButton(Parent parent) {
        Button selectDeck = (Button) parent.lookup("#selectdeck");
        selectDeck.setOnMouseClicked(mouseEvent -> {
            ChoiceDialog<String> dio = setDecksList();
            dio.setTitle("Select main Deck");
            dio.setHeaderText("Select your main deck");
            dio.setContentText("Decks:");
            Optional<String> result1 = dio.showAndWait();
            result1.ifPresent(p -> controller.validateDeck(p));
        });
        return selectDeck;
    }

    private void showCard(TableView cardtable, TableView itemtable, String name) {
        cleartableandaddaddbutton(cardtable, itemtable);
        controller.search(name, cardtable, itemtable);
    }

    private void cleartableandaddaddbutton(TableView cardtable, TableView itemtable) {
        if (havebutton) {
            cardtable.getColumns().remove(5);
            itemtable.getColumns().remove(3);
        }
        havebutton = true;
        addAddButtonToTable(cardtable);
        addAddButtonToTable(itemtable);

        cardtable.getItems().clear();
        itemtable.getItems().clear();
    }

    private void showCollectionTable(TableView cardtable, TableView itemtable) {
        cleartableandaddaddbutton(cardtable, itemtable);
        cardtable.getItems().addAll(FXCollections.observableArrayList(account.getCollection().getCards()));
        itemtable.getItems().addAll(FXCollections.observableArrayList(account.getCollection().getItems()));
    }

    private void showDeckTable(TableView cardtable, TableView itemtable) {
        List<String> choices = new ArrayList<>();
        for (int i = 0; i < account.getDecks().size(); i++) {
            choices.add(account.getDecks().get(i).getName());
        }
        if (account.getDecks().size() != 0) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>(account.getMainDeck().getName(), choices);
            dialog.setTitle("Choice Deck");
            dialog.setHeaderText("Please Choose a deck");
            dialog.setContentText("Decks :");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(letter -> {
                cleartablesandadddeletebutton(cardtable, itemtable);
                showDeck(cardtable, itemtable, account.findDeckByName(letter));
            });
        } else {
            new Alert(Alert.AlertType.WARNING, "You Have no Deck!!!");
        }
    }

    private void setColumns(TableView cardtable, TableView itemtable) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setMaxWidth(100);

        TableColumn<String, Card> column2 = new TableColumn<>("card Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("cardType"));
        column2.setMaxWidth(100);

        TableColumn<String, Card> column3 = new TableColumn<>("price");
        column3.setCellValueFactory(new PropertyValueFactory<>("cost"));
        column3.setMaxWidth(50);

        TableColumn<String, Card> column4 = new TableColumn<>("MP");
        column4.setCellValueFactory(new PropertyValueFactory<>("MP"));
        column4.setMaxWidth(40);

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column2.setMaxWidth(100);
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));
        column5.setMaxWidth(300);

        TableColumn<String, Item> price = new TableColumn<>("price");
        price.setCellValueFactory(new PropertyValueFactory<>("cost"));
        TableColumn<String, Item> desc = new TableColumn<>("Description");
        desc.setCellValueFactory(new PropertyValueFactory<>("desc"));
        TableColumn<String, Item> itemName = new TableColumn<>("Name");
        itemName.setCellValueFactory(new PropertyValueFactory<>("name"));

        cardtable.getColumns().addAll(column1, column2, column3, column4, column5);

        itemtable.getColumns().addAll(itemName, price, desc);

    }

    private void showDeck(TableView cardtable, TableView itemtable, Deck temp) {
        cardtable.getItems().addAll(FXCollections.observableArrayList(new Card(temp.getDeckCost(),
                temp.getAvarageMana(), null, 0, temp.getName(), "")));
        itemtable.getItems().addAll(FXCollections.observableArrayList(new Card(temp.getDeckCost(),
                temp.getAvarageMana(), null, 0, temp.getName(), "")));

        cardtable.getItems().addAll(FXCollections.observableArrayList(temp.getCards()));
        itemtable.getItems().addAll(FXCollections.observableArrayList(temp.getItem()));
    }

    private void cleartablesandadddeletebutton(TableView cardtable, TableView itemtable) {
        if (havebutton) {
            cardtable.getColumns().remove(5);
            itemtable.getColumns().remove(3);
        }
        havebutton = true;
        addDeleteButtonToTable(cardtable);
        addDeleteButtonToTable(itemtable);
        cardtable.getItems().clear();
        itemtable.getItems().clear();
    }

    private void showAllDecksTable(TableView cardtable, TableView itemtable) {
        cleartablesandadddeletebutton(cardtable, itemtable);
        for (int i = 0; i < account.getDecks().size(); i++)
            showDeck(cardtable, itemtable, account.getDecks().get(i));
    }

    public void printError(CollectionErrorType error) {
        if (error == DECK_CREATED || error == REMOVED_SUCCESSFULLY || error == DECK_IS_VALID)
            new Alert(Alert.AlertType.INFORMATION, error.getMessage()).showAndWait();
        else
            new Alert(Alert.AlertType.WARNING, error.getMessage()).showAndWait();
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
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
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
                                    controller.add(letter, card.getCardID());
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

    private void addDeleteButtonToTable(TableView table) {
        TableColumn actionCol = new TableColumn("Delete");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<Card, String>, TableCell<Card, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Card, String> param) {
                final TableCell<Card, String> cell = new TableCell<>() {
                    final Button btn = new Button("Delete");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Card card = getTableView().getItems().get(getIndex());
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Are You Sure?");
                                alert.showAndWait();
                                String deckName = searchTableForDeckName(getTableView().getItems());
                                controller.remove(deckName, card.getCardID());
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }

                    private String searchTableForDeckName(List<Card> items) {
                        for (Card key : items) {
                            if (key != null && account.findDeckByName(key.getName()) != null) {
                                return key.getName();
                            }
                        }
                        return null;
                    }
                };
                return cell;
            }
        };
        actionCol.setCellFactory(cellFactory);
        table.getColumns().add(actionCol);
    }

}