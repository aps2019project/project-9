package view;

import client.Client;
import client.ClientRequest;
import client.RequestType;
import com.google.gson.reflect.TypeToken;
import controller.ShopController;
import data.JsonProcess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.CardOrItem;
import model.Collection;
import model.cards.Card;
import model.enumerations.ShopErrorType;
import model.items.Item;

import java.net.URL;
import java.util.ArrayList;

import static model.enumerations.ShopErrorType.BOUGHT_SUCCESSFUL;

public class Shop {

    private boolean isShowShop = true;
    private static Shop instance = new Shop();
    private ShopController controller;
    private Parent parent;
    private String account;

    private Shop() {
    }

    public static Shop getInstance() {
        return instance;
    }

    public void start(Stage stage, String userName, ShopController controller) {
        this.account = userName;
        try {
            this.controller = controller;
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\Shop.fxml"));
            Parent parent = loader.load();
            this.parent = parent;
            TableView cardTable = (TableView) parent.lookup("#cardTable");
            TableView itemTable = (TableView) parent.lookup("#itemTable");
            setCardTableColumns(cardTable);
            setItemTableColumns(itemTable);

            setSearchButtonAndTextField(parent, cardTable, itemTable);
            setHelpButton();
            setExitButton(stage);
            setMoney(parent);
            setShowShopButton(cardTable, itemTable);
            setShowCollectionButton(cardTable, itemTable);

            Scene scene = new Scene(parent, 1003, 562);
            stage.setScene(scene);
            scene.getStylesheets().add("CSS/shopButton.css");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMoney(Parent root) {
        Text textField = (Text) root.lookup("#money");
        textField.setText(Integer.toString(Client.getAccount(controller.getLoggedInAccount()).getMoney()));
    }

    private void setShowShopButton(TableView cardTable, TableView itemTable) {
        Button b = (Button) parent.lookup("#showShop");
        b.setOnMouseClicked(mouseEvent -> {
            isShowShop = true;
            showShopTable(cardTable, itemTable);
            setMoney(parent);
        });
        setActionsForButton(b, true);
    }

    private void setShowCollectionButton(TableView cardTable, TableView itemTable) {
        Button showCollection = (Button) parent.lookup("#showCollection");
        showCollection.setOnMouseClicked(mouseEvent -> {
            isShowShop = false;
            showCollectionTable(cardTable, itemTable, Client.getAccount(account).getCollection());
            setMoney(parent);
        });
        setActionsForButton(showCollection, true);
    }

    private void setHelpButton() {
        Button help = (Button) parent.lookup("#help");
        help.setOnMouseClicked(mouseEvent -> new Alert(Alert.AlertType.INFORMATION, "show Collection " +
                "---> show your collection\n" +
                "search --> find card/item in shop/collection\n" +
                "buy --> buy card/item\n" +
                "sell --> buy card/item\n").showAndWait());
        setActionsForButton(help, false);

    }

    private void setExitButton(Stage stage) {
        Button back = (Button) parent.lookup("#back");
        back.setOnMouseClicked(mouseEvent -> stage.close());
        setActionsForButton(back, false);
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
        setActionsForButton(search, false);
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
        Client.sendRequest(new ClientRequest(Client.getAuthToken(), RequestType.SHOP_CARDS));
        String cardJson = Client.getResponse();
        Client.sendRequest(new ClientRequest(Client.getAuthToken(), RequestType.SHOP_ITEMS));
        String itemJson = Client.getResponse();
        ArrayList<Card> cards = JsonProcess.getGson().fromJson(cardJson, new TypeToken<ArrayList<Card>>() {
        }.getType());
        ArrayList<Item> items = JsonProcess.getGson().fromJson(itemJson, new TypeToken<ArrayList<Item>>() {
        }.getType());
        ObservableList<Item> item = FXCollections.observableArrayList(items);
        ObservableList<Card> card = FXCollections.observableArrayList(cards);
        cardTable.setItems(card);
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

    private void setItemTableColumns(TableView tableView) {
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
        Callback<TableColumn<CardOrItem, String>, TableCell<CardOrItem, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<CardOrItem, String> param) {
                final TableCell<CardOrItem, String> cell = new TableCell<>() {
                    final Button btn = new Button("Sell");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                System.out.println(getTableView().getItems().get(getIndex()).getCardID());
                                controller.sell(getTableView().getItems().get(getIndex()).getCardID());
                                setMoney(parent);
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

    private void addBuyButtonToTable(TableView table) {
        TableColumn actionCol = new TableColumn("Buy");
        actionCol.setCellValueFactory(new PropertyValueFactory<>(""));
        Callback<TableColumn<CardOrItem, String>, TableCell<CardOrItem, String>> cellFactory = new Callback<>() {
            @Override
            public TableCell call(final TableColumn<CardOrItem, String> param) {
                final TableCell<CardOrItem, String> cell = new TableCell<>() {
                    final Button btn = new Button("Buy");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                controller.buy(getTableView().getItems().get(getIndex()).getName());
                                setMoney(parent);
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

    public void printError(ShopErrorType error) {
        if (error == BOUGHT_SUCCESSFUL) {
            new Alert(Alert.AlertType.INFORMATION, error.getMessage()).showAndWait();
        } else
            new Alert(Alert.AlertType.WARNING, error.getMessage()).showAndWait();
    }

    private void setActionsForButton(Button button, boolean ifCollection) {
        String s = button.getStyle();
        double x = button.getLayoutX();
        double y = button.getLayoutY();
        Paint p = button.getTextFill();
        Font f = button.getFont();
        button.setOnMouseEntered(m -> {
            button.setTextFill(Color.rgb(255, 0, 34));
            if (!ifCollection) {
                button.setLayoutY(button.getLayoutY() - 15);
                button.setFont(new Font(34));
            }
            if (button.getText().equals("Back To Menu")) {
                button.setText("Back");
                button.setLayoutX(x + 15);
            }
            button.setStyle(
                    "-fx-background-color: transparent;");
        });
        button.setOnMouseExited(l -> {
            button.setFont(f);
            button.setTextFill(p);
            button.setLayoutX(x);
            button.setLayoutY(y);
            button.setStyle(s);
            if (button.getText().equals("Back")) {
                button.setText("Back To Menu");
            }
        });
    }
}
