package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Account;
import model.enumerations.BuffName;
import model.enumerations.MinionAttackType;
import model.enumerations.SpellTargetType;

import java.io.IOException;
import java.net.URL;

public class CustomCardMenu {
    private Account loggedAccount;
    private Parent parent;

    public CustomCardMenu(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    public void start() throws IOException {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Custom Card Menu");
        stage.getIcons().add(new Image("file:src/res/icon.jpg"));
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/CustomCardView.fxml"));
        Parent parent = fxmlLoader.load();
        this.parent = parent;
        setElements();
        root.getChildren().add(parent);
        stage.show();
    }

    private void setElements() {
        setAddBuffButton();
        setCost();
        setChoiceBoxes();
        ChoiceBox type = (ChoiceBox) parent.lookup("#type");
        type.getItems().add("Spell");
        type.getItems().add("Minion");
        type.getItems().add("Hero");
        ChoiceBox target = ((ChoiceBox) parent.lookup("#target"));
        ChoiceBox attackType = ((ChoiceBox) parent.lookup("#attackType"));
        type.setOnAction(actionEvent -> {
            if (type.getSelectionModel().getSelectedItem().equals("Spell")) {
                target.setDisable(false);
                parent.lookup("#addBuff").setDisable(false);
                parent.lookup("#buffList").setDisable(false);
                parent.lookup("#cost").setDisable(false);
                parent.lookup("#AP").setDisable(true);
                parent.lookup("#HP").setDisable(true);
                attackType.setDisable(true);
                parent.lookup("#range").setDisable(true);
                parent.lookup("#specialPower").setDisable(true);
                parent.lookup("#activation").setDisable(true);
                parent.lookup("#coolDown").setDisable(true);
            } else {
                target.setDisable(true);
                parent.lookup("#addBuff").setDisable(true);
                parent.lookup("#buffList").setDisable(true);
                parent.lookup("#cost").setDisable(true);
                parent.lookup("#AP").setDisable(false);
                parent.lookup("#HP").setDisable(false);
                attackType.setDisable(false);
                parent.lookup("#range").setDisable(false);
                parent.lookup("#specialPower").setDisable(false);
                if (type.getSelectionModel().getSelectedItem().equals("Minion"))
                    parent.lookup("#activation").setDisable(false);
                else
                    parent.lookup("#activation").setDisable(true);
                if (type.getSelectionModel().getSelectedItem().equals("Hero"))
                    parent.lookup("#coolDown").setDisable(false);
                else
                    parent.lookup("#coolDown").setDisable(true);
            }
        });
    }

    private void setChoiceBoxes() {
        ChoiceBox target = ((ChoiceBox) parent.lookup("#target"));
        ChoiceBox attackType = ((ChoiceBox) parent.lookup("#attackType"));
        target.getItems().addAll(SpellTargetType.values());
        attackType.getItems().addAll(MinionAttackType.values());
    }

    private void setCost() {
        (parent.lookup("#cost")).setOnMouseClicked(mouseEvent -> {
            parent.lookup("#create").setDisable(false);
        });
    }

    private void setAddBuffButton() {
        Button buff = (Button) parent.lookup("#addBuff");
        ListView listView = (ListView) parent.lookup("#buffList");
        buff.setOnMouseClicked(mouseEvent -> {
            buffCreateMenu(listView);
        });
    }

    private void buffCreateMenu(ListView listView) {
        Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 300, 200);
        TextField name = new TextField();
        ChoiceBox<BuffName> Type = new ChoiceBox<>();

        stage.setScene(scene);
        stage.show();
    }


}
