package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Collection;
import model.Shop;
import model.cards.Card;
import model.items.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ShopMenu {

    private boolean isShowShop = true;

    public void start(Stage stage, Collection collection) {
        try {
            stage.setMaximized(true);

            Group root = new Group();
            setBackGround(root);
            TableView tableView = ShopTable();
            setSearchButtonAndTextField(root);
            setHelpButton(root);
            setExitButton(root);
            setShowCollectionButton(root, tableView, collection);

            root.getChildren().add(tableView);
            Scene scene = new Scene(root, stage.getMaxHeight(), stage.getMaxWidth());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setHelpButton(Group group) {
        Button b = new Button("HELP");
        b.setScaleX(1.5);
        b.setScaleY(1.5);
        b.setLayoutX(1400);
        b.setLayoutY(700);
        b.setOnMouseClicked(mouseEvent -> new Alert(Alert.AlertType.INFORMATION, "show Collection " +
                "---> show your collection\n" +
                "search --> find card/item in shop/collection\n" +
                "buy --> buy card/item\n" +
                "sell --> buy card/item\n").showAndWait());
        group.getChildren().add(b);
    }

    private void setExitButton(Group group) {
        Button b = new Button("Back to Menu");
        b.setScaleX(1.5);
        b.setScaleY(1.5);
        b.setLayoutX(1380);
        b.setLayoutY(750);
        b.setOnMouseClicked(mouseEvent -> {
            //TODO

        });
        group.getChildren().add(b);
    }

    private void setSearchButtonAndTextField(Group group) {
        Button searchButton = new Button("Search");
        searchButton.setTextFill(Color.rgb(46, 30, 142));
        searchButton.setLayoutX(1150);
        searchButton.setLayoutY(120);
        searchButton.setScaleX(2);
        searchButton.setScaleY(1.5);

        searchButton.setOnMouseClicked(mouseEvent -> {
            //TODO
            if (isShowShop) {

            } else {

            }
        });

        TextField textField = new TextField();
        textField.setPromptText("Enter Name of Card/Item");
        textField.setScaleX(1.7);
        textField.setScaleY(1.5);
        textField.setLayoutX(1100);
        textField.setLayoutY(70);
        group.getChildren().addAll(searchButton, textField);
    }

    private void setBackGround(Group group) throws FileNotFoundException {
        ImageView imageView = new ImageView(new Image(new FileInputStream("src/res/shop.jpg")));
        imageView.setFitHeight(810);
        imageView.setFitWidth(1600);
        group.getChildren().add(imageView);
    }

    private TableView ShopTable() {
        TableView tableView = new TableView();

        setTableColumns(tableView);

        ObservableList<Item> item = FXCollections.observableArrayList(Shop.getInstance().getItems());
        ObservableList<Card> cards = FXCollections.observableArrayList(Shop.getInstance().getCards());
        tableView.getItems().addAll(cards);
        tableView.getItems().addAll(item);

        tableView.setScaleX(1.6);
        tableView.setScaleY(2);
        tableView.setLayoutY(200);
        tableView.setLayoutX(145);
        return tableView;
    }

    private void addBuyButtonToTable(TableView table) {
        TableColumn<Card, Void> colBtn = new TableColumn("Buy");

        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Buy");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //TODO
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    private void addSellButtonToTable(TableView table) {
        TableColumn<Card, Void> colBtn = new TableColumn("Sell");

        Callback<TableColumn<Card, Void>, TableCell<Card, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Card, Void> call(final TableColumn<Card, Void> param) {
                final TableCell<Card, Void> cell = new TableCell<>() {

                    private final Button btn = new Button("Sell");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            //TODO
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };
        colBtn.setCellFactory(cellFactory);
        table.getColumns().add(colBtn);
    }

    private void setTableColumns(TableView tableView) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Card> column2 = new TableColumn<>("card Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("cardType"));

        TableColumn<String, Card> column3 = new TableColumn<>("price");
        column3.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<String, Card> column4 = new TableColumn<>("MP");
        column4.setCellValueFactory(new PropertyValueFactory<>("MP"));

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));
        column5.setMaxWidth(300);

        TableColumn<String, Card> column6 = new TableColumn<>("Description");
        column6.setCellValueFactory(new PropertyValueFactory<>("desc"));
        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
        addBuyButtonToTable(tableView);
    }

    private void setShowCollectionButton(Group group, TableView tableView, Collection collection) {
        Button showCollection = new Button("Show collection");
        showCollection.setLayoutX(1135);
        showCollection.setLayoutY(200);
        showCollection.setScaleX(2);
        showCollection.setScaleY(1.5);

        showCollection.setOnMouseClicked(mouseEvent -> {
            isShowShop = false;
            showCollectionTable(tableView, collection);
        });
        group.getChildren().add(showCollection);
    }

    private void showCollectionTable(TableView tableView, Collection collection) {
        tableView.getColumns().remove(tableView.getColumns().get(5));
        addSellButtonToTable(tableView);
        ObservableList<Item> item = FXCollections.observableArrayList(collection.getItems());
        ObservableList<Card> cards = FXCollections.observableArrayList(collection.getCards());
        tableView.setItems(cards);
        tableView.getItems().addAll(item);

    }

}
