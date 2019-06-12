package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Collection;
import model.cards.Card;

public class GraveYard {

    private Collection collection;

    public void start(Stage stage, Collection collection) {
        this.collection = collection;
        stage.setMaximized(true);
        Image image = new Image("src/res/grave_yard.jpg");
        ImageView imageView = new ImageView(image);
        Group root = new Group(imageView);

        /*Media media = new Media(new File("src\\res\\music\\backgroundmusic.mp3").toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.*/

        setTableView(root);
        setBackToGameButton(root);

        imageView.setFitHeight(810);
        imageView.setFitWidth(1600);
        Scene scene = new Scene(root, stage.getMaxHeight(), stage.getMaxWidth());
        stage.setScene(scene);
        scene.getStylesheets().add("src/res/CSS/shopTable.css");
        int a = 0;
        stage.show();

    }

    private void setTableView(Group group) {
        TableView tableView = new TableView();
        TableColumn<String, Card> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<String, Card> column2 = new TableColumn<>("Type");
        column2.setCellValueFactory(new PropertyValueFactory<>("cardType"));

        TableColumn<String, Card> column3 = new TableColumn<>("Description");
        column3.setCellValueFactory(new PropertyValueFactory<>("desc"));
        column3.setMaxWidth(300);

        tableView.getColumns().addAll(column1, column2, column3);
        ObservableList<Card> cards = FXCollections.observableArrayList(collection.getCards());
        tableView.setItems(cards);

        tableView.setMinWidth(482);
        tableView.setScaleX(1.5);
        tableView.setScaleY(1.5);
        tableView.setLayoutX(550);
        tableView.setLayoutY(150);
        group.getChildren().add(tableView);
    }

    private void setBackToGameButton(Group group) {
        Button b = new Button("Back To Game");
        b.setOnMouseClicked(mouseEvent -> {
            //TODO
        });
        b.setScaleX(1.5);
        b.setScaleY(1.5);
        b.setLayoutX(1300);
        b.setLayoutY(700);
        group.getChildren().add(b);
    }

}
