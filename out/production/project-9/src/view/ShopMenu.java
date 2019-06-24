package view;

import controller.ShopController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Collection;
import model.Deck;
import model.Shop;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.items.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static model.enumerations.ShopErrorType.BOUGHT_SUCCESSFUL;

public class ShopMenu {

    private boolean isShowShop = true;
    private static ShopMenu instance = new ShopMenu();
    private ShopController controller;

    private ShopMenu() {
    }

    public static ShopMenu getInstance() {
        return instance;
    }

    public void start(Stage stage, Collection collection, ShopController controller) {
        try {
            this.controller = controller;
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\Shop.fxml"));
            Parent parent = loader.load();

            TableView cardTable = (TableView) parent.lookup("#cardTable");
            TableView itemTable = (TableView) parent.lookup("#itemTable");
            setCardTableColumns(cardTable);
            setitemTableColumns(itemTable);

            setSearchButtonAndTextField(parent, cardTable, itemTable);
            setHelpButton(parent);
            setExitButton(parent, stage);
            setMoney(parent);
            setShowShopButton(parent, cardTable, itemTable);
            setShowCollectionButton(parent, cardTable, itemTable, collection);

            Scene scene = new Scene(parent, 1003, 562);
            stage.setScene(scene);
            scene.getStylesheets().add("src/res/CSS/shopTable.css");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMoney(Parent root) {
        Text textField = (Text) root.lookup("#money");
        textField.setText(Integer.toString(controller.getLoggedInAccount().getMoney()));
    }

    private void setShowShopButton(Parent parent, TableView cardTable, TableView itemTable) {
        Button b = (Button) parent.lookup("#showShop");
        b.setOnMouseClicked(mouseEvent -> {
            isShowShop = true;
            showShopTable(cardTable, itemTable);
        });
    }

    private void setShowCollectionButton(Parent parent, TableView cardTable, TableView itemTable, Collection collection) {
        Button showCollection = (Button) parent.lookup("#showCollection");
        showCollection.setOnMouseClicked(mouseEvent -> {
            isShowShop = false;
            showCollectionTable(cardTable, itemTable, collection);
        });
    }

    private void setHelpButton(Parent parent) {
        Button help = (Button) parent.lookup("#help");
        help.setOnMouseClicked(mouseEvent -> new Alert(Alert.AlertType.INFORMATION, "show Collection " +
                "---> show your collection\n" +
                "search --> find card/item in shop/collection\n" +
                "buy --> buy card/item\n" +
                "sell --> buy card/item\n").showAndWait());

    }

    private void setExitButton(Parent parent, Stage stage) {
        Button back = (Button) parent.lookup("#back");
        back.setOnMouseClicked(mouseEvent -> MainMenu.getInstance().start(stage, controller.getLoggedInAccount()));
    }

    private void setSearchButtonAndTextField(Parent parent, TableView cardTable, TableView itemTable) {
        Button search = (Button) parent.lookup("#searchB");
        TextField searchTextField = (TextField) parent.lookup("#search");

        search.setOnMouseClicked(mouseEvent -> {
            if (isShowShop) {
                controller.searchShop(searchTextField.getText(), cardTable, itemTable);
            } else {
                controller.searchCollection(searchTextField.getText(), cardTable, itemTable);
            }
        });
    }

    private void showCollectionTable(TableView cardTable, TableView itemTable, Collection collection) {
        addSellButton(cardTable, itemTable);
        ObservableList<Item> item = FXCollections.observableArrayList(collection.getItems());
        ObservableList<Card> cards = FXCollections.observableArrayList(collection.getCards());
        cardTable.setItems(cards);
        itemTable.setItems(item);
    }

    private void addSellButton(TableView cardTable, TableView itemTable) {
        cardTable.getColumns().remove(cardTable.getColumns().get(5));
        addSellButtonToTable(cardTable);

        itemTable.getColumns().remove(itemTable.getColumns().get(3));
        addSellButtonToTable(itemTable);
    }

    private void addBuyButton(TableView cardTable, TableView itemTable) {
        cardTable.getColumns().remove(cardTable.getColumns().get(5));
        addBuyButtonToTable(cardTable);

        itemTable.getColumns().remove(itemTable.getColumns().get(3));
        addBuyButtonToTable(itemTable);
    }

    public void showCardSell(TableView cardTable, TableView itemTable, Card card) {
        cardTable.getItems().clear();
        itemTable.getItems().clear();
        addSellButton(cardTable, itemTable);
        ObservableList<Card> cards = FXCollections.observableArrayList(card);
        cardTable.setItems(cards);
    }

    public void showItemSell(TableView cardTable, TableView itemTable, Item item) {
        cardTable.getItems().clear();
        itemTable.getItems().clear();
        addSellButton(cardTable, itemTable);
        ObservableList<Item> items = FXCollections.observableArrayList(item);
        itemTable.setItems(items);
    }

    public void showCardBuy(TableView cardTable, TableView itemTable, Card card) {
        cardTable.getItems().clear();
        itemTable.getItems().clear();
        addBuyButton(cardTable, itemTable);
        ObservableList<Card> cards = FXCollections.observableArrayList(card);
        cardTable.setItems(cards);
    }

    public void showItemBuy(TableView cardTable, TableView itemTable, Item item) {
        cardTable.getItems().clear();
        itemTable.getItems().clear();
        addBuyButton(cardTable, itemTable);
        ObservableList<Item> cards = FXCollections.observableArrayList(item);
        itemTable.setItems(cards);
    }

    private void showShopTable(TableView cardTable, TableView itemTable) {
        addBuyButton(cardTable, itemTable);
        ObservableList<Item> item = FXCollections.observableArrayList(Shop.getInstance().getItems());
        ObservableList<Card> cards = FXCollections.observableArrayList(Shop.getInstance().getCards());
        cardTable.setItems(cards);
        itemTable.setItems(item);
    }

    private void setCardTableColumns(TableView tableView) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Card> column2 = new TableColumn<>("card Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("cardType"));

        TableColumn<String, Card> column3 = new TableColumn<>("price");
        column3.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<String, Card> column4 = new TableColumn<>("MP");
        column4.setCellValueFactory(new PropertyValueFactory<>("MP"));
        column4.setMaxWidth(30);

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));
        column5.setMaxWidth(300);

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
        addBuyButtonToTable(tableView);
    }

    private void setitemTableColumns(TableView tableView) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<String, Card> column3 = new TableColumn<>("price");
        column3.setCellValueFactory(new PropertyValueFactory<>("cost"));

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));

        tableView.getColumns().addAll(column1, column3, column5);
        addBuyButtonToTable(tableView);
    }

    private void addSellButtonToTable(TableView table) {
        TableColumn actionCol = new TableColumn("Sell");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<Card, String>, TableCell<Card, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Card, String> param) {
                final TableCell<Card, String> cell = new TableCell<>() {
                    final Button btn = new Button("Sell");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event ->
                                    controller.sell(getTableView().getItems().get(getIndex()).getName()));
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

    private void addBuyButtonToTable(TableView table) {
        TableColumn actionCol = new TableColumn("Buy");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<Card, String>, TableCell<Card, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<Card, String> param) {
                final TableCell<Card, String> cell = new TableCell<>() {
                    final Button btn = new Button("Buy");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event ->
                                    controller.buy(getTableView().getItems().get(getIndex()).getName()));
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

    public void printError(ShopErrorType error) {
        if (error == BOUGHT_SUCCESSFUL) {
            new Alert(Alert.AlertType.INFORMATION, error.getMessage()).showAndWait();
        } else
            new Alert(Alert.AlertType.WARNING, error.getMessage()).showAndWait();
    }
}
