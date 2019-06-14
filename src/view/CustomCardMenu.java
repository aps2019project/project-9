package view;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Account;
import model.buffs.*;
import model.cards.Card;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.*;
import model.specialPower.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class CustomCardMenu {
    private static int uniqueID = 2000;
    private Account loggedAccount;
    private Parent parent;
    private ArrayList<Buff> createdBuffs = new ArrayList<>();
    private Stage firstStage;
    private Stage secondStage;
    private Spell specialPower;

    public CustomCardMenu(Account loggedAccount) {
        this.loggedAccount = loggedAccount;
    }

    public void start() throws IOException {
        Stage stage = new Stage();
        firstStage = stage;
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
        setSpecialBtn(((Label) parent.lookup("#specialLabel")));
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
                parent.lookup("#cost").setDisable(false);
                parent.lookup("#AP").setDisable(false);
                parent.lookup("#HP").setDisable(false);
                attackType.setDisable(false);
                parent.lookup("#range").setDisable(false);
                parent.lookup("#createSpecialPower").setDisable(false);
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

    private void setSpecialBtn(Label label) {
        Button button = (Button) parent.lookup("#createSpecialPower");
        ArrayList<Card> cards = loggedAccount.getCollection().getCards();
        ArrayList<Spell> spells = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof Spell)
                spells.add(((Spell) card));
        }
        button.setOnMouseClicked(mouseEvent -> {
            Stage stage = new Stage();
            Group root = new Group();
            Scene scene = new Scene(root, 250, 200);
            ListView<String> spellList = new ListView<>();
            for (Spell spell : spells) {
                spellList.getItems().add(spell.getName());
            }
            spellList.setOnMouseClicked(mouseEvent1 -> {
                specialPower = spells.get(spellList.getSelectionModel().getSelectedIndex());
                label.setText(spellList.getSelectionModel().getSelectedItem());
                stage.close();
            });
            root.getChildren().add(spellList);
            stage.setTitle("Your Spells");
            stage.setScene(scene);
            stage.getIcons().add(new Image("file:src/res/icon.jpg"));
            stage.show();
        });
    }

    private void createCard() {
        Button button = ((Button) parent.lookup("#create"));
        button.setOnMouseClicked(mouseEvent -> {
            String name = ((TextField) parent.lookup("#name")).getText();
            CardType cardType = ((CardType) ((ChoiceBox) parent.lookup("#type"))
                    .getSelectionModel().getSelectedItem());
            SpellTargetType spellTargetType;
            int AP;
            int HP;
            MinionAttackType minionAttackType;
            int range;
            SpecialPowerActivationTime specialPowerActivationTime;
            int coolDown;
            String desc = ((TextArea) parent.lookup("#desc")).getText();
            int MP = Integer.parseInt(((TextField) parent.lookup("#MP")).getText());
            int cost = Integer.parseInt(((TextField) parent.lookup("#cost")).getText());
            Card result;
            if (cardType == CardType.SPELL) {
                spellTargetType = ((SpellTargetType) ((ChoiceBox) parent.lookup("#target"))
                        .getSelectionModel().getSelectedItem());
                result = new Spell(name, cost, MP, spellTargetType,
                        uniqueID++, desc, createdBuffs, null, SpellName.CUSTOM);
            } else if (cardType == CardType.MINION) {
                HP = Integer.parseInt(((TextField) parent.lookup("#HP")).getText());
                AP = Integer.parseInt(((TextField) parent.lookup("#AP")).getText());
                range = Integer.parseInt(((TextField) parent.lookup("#range")).getText());
                minionAttackType = ((MinionAttackType) ((ChoiceBox) parent.lookup("#attackType"))
                        .getSelectionModel().getSelectedItem());

                //result = new Minion(name, cost, MP, HP, AP, minionAttackType, range, )
            }

        });
        firstStage.close();
    }

    /*private SpecialPower getSpecialPower(SpecialPowerActivationTime time) {
        switch (time) {
            case PASSIVE:
                return new PassiveSpecialPower(specialPower.getBuffs(), );
            case ON_SPAWN:
                if (specialPower.getBuffs().size() > 0)
                    return new OnSpawnSpecialPower(specialPower.getBuffs().get(0), );
                else
                    return null;
            case ON_ATTACK:
                return new OnAttackSpecialPower(specialPower.getBuffs(),false,false);
            case ON_DEFEND:
                return new OnDefendSpecialPower();
            case COMBO:
                return new ComboSpecialPower();
            case ON_DEATH:
                return new OnDeathSpecialPower();
        }
    }*/

    private void setChoiceBoxes() {
        ChoiceBox target = ((ChoiceBox) parent.lookup("#target"));
        ChoiceBox attackType = ((ChoiceBox) parent.lookup("#attackType"));
        target.getItems().addAll(SpellTargetType.values());
        attackType.getItems().addAll(MinionAttackType.values());
        ChoiceBox activation = ((ChoiceBox) parent.lookup("#activation"));
        activation.getItems().addAll(SpecialPowerActivationTime.values());
        activation.getSelectionModel().select(1);
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
            try {
                buffCreateMenu(listView, createdBuffs);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void buffCreateMenu(ListView listView, ArrayList<Buff> buffs) throws IOException {
        Stage stage = new Stage();
        secondStage = stage;
        Group root = new Group();
        Scene scene = new Scene(root);
        stage.setTitle("Buff Create Menu");
        stage.getIcons().add(new Image("file:src/res/icon.jpg"));
        stage.setScene(scene);
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/BuffCreateMenu.fxml"));
        Parent parent = fxmlLoader.load();
        CheckBox accept = ((CheckBox) parent.lookup("#accept"));
        accept.setOnAction(actionEvent -> {
            if (accept.isSelected()) {
                parent.lookup("#create").setDisable(false);
            } else
                parent.lookup("#create").setDisable(true);
        });
        setBuffMenuActions(parent);
        createBuff(((Button) parent.lookup("#create")), parent, buffs, listView);
        root.getChildren().add(parent);
        stage.show();
    }

    private void setBuffMenuActions(Parent parent) {
        ChoiceBox type = (ChoiceBox) parent.lookup("#buffType");
        for (BuffName value : BuffName.values()) {
            if (value != BuffName.DELAY)
                type.getItems().add(value);
        }
        type.getSelectionModel().select(1);
        ChoiceBox target = ((ChoiceBox) parent.lookup("#buffTarget"));
        target.getItems().add("Enemy");
        target.getItems().add("Friendly");
        target.getSelectionModel().select(1);
    }

    private void createBuff(Button button, Parent parent, ArrayList<Buff> buffs, ListView listView) {
        button.setOnMouseClicked(mouseEvent -> {
            try {
                String name = ((TextField) parent.lookup("#name")).getText();
                BuffName type = ((BuffName) ((ChoiceBox) parent
                        .lookup("#buffType")).getSelectionModel().getSelectedItem());
                int turnsActive = Integer.parseInt(((TextField) parent.lookup("#turnsActive")).getText());
                boolean isForAllTurns = ((CheckBox) parent.lookup("#permanent")).isSelected();
                boolean isContinious = ((CheckBox) parent.lookup("#continious")).isSelected();
                boolean isForHP = ((CheckBox) parent.lookup("#isForHP")).isSelected();
                boolean isDelay = ((CheckBox) parent.lookup("#isDelay")).isSelected();
                int delay = -1;
                if (isDelay)
                    delay = Integer.parseInt(((TextField) parent.lookup("#delay")).getText());
                int power = Integer.parseInt(((TextField) parent.lookup("#power")).getText());
                Buff buff = getBuff(type, turnsActive, isForAllTurns, isContinious, isForHP, isDelay, delay, power);
                buff.setName(name);
                buffs.add(buff);
                listView.getItems().add(name);
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("INVALID INPUT");
                alert.setContentText("your input is not valid , try again");
            }
            secondStage.close();
        });
    }

    private Buff getBuff(BuffName buffName, int turns, boolean isPer,
                         boolean isCon, boolean isForHP, boolean isDelay
            , int delay, int power) {
        Buff buff = null;
        switch (buffName) {
            case HOLLY:
                buff = new HollyBuff(turns, isPer, isCon, false);
                break;
            case STUN:
                buff = new StunBuff(turns, isPer, isCon);
                break;
            case WEAKNESS:
                buff = new WeaknessBuff(turns, isPer, isCon, power, isForHP, false, null);
                break;
            case POISON:
                buff = new PoisonBuff(turns, isPer, isCon);
                break;
            case DISARM:
                buff = new DisarmBuff(turns, isPer, isCon);
                break;
            case POWER:
                buff = new PowerBuff(turns, isPer, isCon, power, isForHP);
                break;
        }
        if (isDelay) {
            return new DelayBuff(delay, buff);
        } else {
            return buff;
        }
    }


}