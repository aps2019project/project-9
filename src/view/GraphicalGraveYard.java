package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Collection;
import model.Player;
import model.cards.Card;

import java.net.URL;

public class GraphicalGraveYard {
    private Stage stage;

    private Player player;

    public GraphicalGraveYard(Player player) {
        this.player = player;
    }

    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(new URL("file:src\\res\\FXML\\graveyard.fxml"));
            Parent root = loader.load();
            TableView tableView = (TableView) root.lookup("#tableview");
            setTableView(tableView);
            Button back = (Button) root.lookup("#back");
            back.setOnMouseClicked(m -> {
                stage.close();
            });
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
    }
}
