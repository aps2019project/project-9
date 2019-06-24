package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Player;
import model.cards.Card;

import java.net.URL;
import java.util.ArrayList;

public class GraveYardView {

    private Player player;

    public void start() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\graveyard.fxml"));
            Parent root = loader.load();
            TableView tableView = (TableView) root.lookup("#tableview");
            setTableView(tableView);

            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTableView(TableView tableView) {
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Card> column5 = new TableColumn<>("Description");
        column5.setCellValueFactory(new PropertyValueFactory<>("desc"));
        tableView.getColumns().addAll(column1, column5);

        tableView.getItems().addAll(player.getGraveYard().getCards());
        tableView.getItems().addAll(new Card(1,1,null,1,"a","a"));
    }

    public GraveYardView(Player player) {
        this.player = player;
    }


    public void showCards(ArrayList<Card> cards) {
        for (Card card : cards) {
            System.out.println(card.toString());
        }
    }

    public void showCardInfo(Card card) {
        System.out.printf("card name : %s\ndescription : %s", card.getName(), card.getDesc());
    }

    public void help(Player player) {
        System.out.printf("<<-------GraphicalGraveYard for Player : %s--------->>\n", player.getName());
        System.out.println("show info\nshow cards\nexit");
    }
}
