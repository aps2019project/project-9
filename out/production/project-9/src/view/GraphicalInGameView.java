package view;

import controller.AccountController;
import controller.InGameController;
import controller.MainMenuController;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.Cell;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.cellaffects.CellAffect;
import model.cellaffects.HollyCellAffect;
import model.cellaffects.PoisonCellAffect;
import model.enumerations.InGameErrorType;
import model.items.Collectible;
import model.items.Item;
import model.specialPower.ComboSpecialPower;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class GraphicalInGameView {
    private static InGameController inGameController;
    private static TextArea descLabel;
    private static Parent parent;
    private static HashMap<String, String> pathes = new HashMap<>();
    private static MediaPlayer insert;
    private static MediaPlayer attack;
    private static MediaPlayer move;
    private static Group group;
    private static boolean isCombo = false;
    private static ArrayList<Minion> comboCards = new ArrayList<>();
    private static Stage stage;
    private static Account loggedAccount;
    private static MediaPlayer backGroundMusic;

    public void showGame(Stage stage, Battle battle, Account account) throws IOException {
        /*//TODO
        loggedAccount = account;
        AccountMenu.closeMainStage();
        AccountMenu.stopMusic();
        //*/
        GraphicalInGameView.stage = stage;
        inGameController = new InGameController(battle);
        Group group = new Group();
        GraphicalInGameView.group = group;
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/FXML/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        GraphicalInGameView.parent = parent;
        group.getChildren().add(parent);
        descLabel = ((TextArea) parent.lookup("#description"));
        Scene scene = new Scene(group);
        setCursor(scene);
        stage.getIcons().add(new Image("src\\res\\icon.jpg"));
        setBtns();


        //
        setManas(battle.getFirstPlayer());
        setManas(battle.getSecondPlayer());
        updateHand();
        updatePlayGround(group);


        MediaView mediaView = getBackGroundMusic();
        group.getChildren().add(mediaView);
        stage.setTitle("Duelyst");
        stage.setScene(scene);
        stage.show();
    }

    public static void spellCast(Cell target) {//animation view
        ImageView imageView = new ImageView(new Image("file:src/res/inGameResource/spellAction.gif"));
        /*Pane pane = getCellPane(target.getX(), target.getY());
        pane.getChildren().add(new ImageView(new Image("file:src/res/inGameResource/spellAction.gif")));*/
        group.getChildren().add(imageView);
        TranslateTransition transition = new TranslateTransition(Duration.millis(2000),imageView);
        int u = target.getX()*9 + target.getY();
        int[] laout = GraphicalViewResource.positions.get(u);
        laout[0]-=50;
        laout[1]-=50;
        transition.setFromY(laout[1] - 100);
        transition.setFromX(laout[0]);
        transition.setToX(laout[0]);
        transition.setToY(laout[1]);
        transition.play();
        transition.setOnFinished(actionEvent -> group.getChildren().remove(imageView));
    }

    public static void attackTo(Minion minion, Cell target) {//animation view
        //TODO
    }

    public static void moveTo(Cell first, Cell second) {//animation view
        Pane firstCell = getCellPane(first.getX(), first.getY());
        ImageView imageView = new ImageView(new Image(pathes.get(second.getMinionOnIt().getName())));
        removeImage(pathes.get(second.getMinionOnIt().getName()), firstCell);
        removeImage(pathes.get(second.getMinionOnIt().getName()), getCellPane(second.getX(), second.getY()));
        TranslateTransition transition = new TranslateTransition(Duration.millis(2000), imageView);
        int x = first.getX();
        int y = first.getY();
        int u = second.getX() * 9 + second.getY();
        int u1 = x * 9 + y;
        group.getChildren().add(imageView);
        transition.setFromX(GraphicalViewResource.positions.get(u1)[0]);
        transition.setFromY(GraphicalViewResource.positions.get(u1)[1]);
        transition.setToX(GraphicalViewResource.positions.get(u)[0]);
        transition.setToY(GraphicalViewResource.positions.get(u)[1]);
        transition.play();
        transition.setOnFinished(actionEvent -> {
            group.getChildren().remove(imageView);
            updatePlayGround(group);
        });
    }

    public static void doAiAnimations(String alert) {
        String[] split = alert.split("\n");
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() >= 9 && split[i].substring(0, 9).equals("Minion : ")) {
                String minion = split[i].split(" ")[2];
                int fx = Integer.parseInt(split[i + 1].split(" ")[3]);
                int fy = Integer.parseInt(split[i + 1].split(" ")[4]);
                int sx = Integer.parseInt(split[i + 2].split(" ")[2]);
                int sy = Integer.parseInt(split[i + 2].split(" ")[3]);
                moveTo(inGameController.getBattle().getPlayGround().getCell(sx, sy),
                        inGameController.getBattle().getPlayGround().getCell(fx, fy));
            }
        }
    }

    private static void removeImage(String path, Pane pane) {
        for (Node child : pane.getChildren()) {
            if (child instanceof ImageView &&
                    ((ImageView) child).getImage().getUrl().equals(path)) {
                pane.getChildren().remove(child);
                break;
            }
        }
    }

    private static void setComboBtn() {
        Pane pane = ((Pane) parent.lookup("#combo"));
        ImageView combo = new ImageView(new Image("file:src/res/inGameResource/comboBtn.gif"));
        combo.setFitHeight(20);
        combo.setFitWidth(100);
        combo.setLayoutX(15);
        combo.setLayoutY(10);
        pane.getChildren().add(combo);
        pane.setOnMouseEntered(mouseEvent ->
                combo.setImage(new Image("file:src/res/inGameResource/comboBtn2.gif")));
        pane.setOnMouseExited(mouseEvent ->
                combo.setImage(new Image("file:src/res/inGameResource/comboBtn.gif")));
        pane.setOnMouseClicked(mouseEvent -> {
            if (isCombo) {
                isCombo = false;
                descLabel.setText("Combo Cancelled :(");
            } else {
                descLabel.setText("Click On Combo Minions Then Click Target Cell\nSelected Cards:\n");
                isCombo = true;
                comboCards = new ArrayList<>();
            }
        });
    }

    public static void finished(BattleResult battleResult) {//TODO
        String winner = battleResult.getWinner();
        int prize = battleResult.getPrize();
        Text winnerText = new Text(winner + " wins \nand gets " + prize + " prize");
        winnerText.setFill(Color.BLUE);
        winnerText.setFont(Font.loadFont("file:src/res/inGameResource/BrockScript.ttf", 50));
        winnerText.setX(20);
        winnerText.setY(50);
        Group group = new Group();
        Scene scene = new Scene(group, 400, 400);
        ImageView imageView = new ImageView(new Image("file:src/res/inGameResource/endGame.jpg"));
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        group.getChildren().add(imageView);
        group.getChildren().add(winnerText);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public static void alertAiAction(String action) {
        aiMove = action;
        descLabel.setText(action);
    }

    private static void setBtns() {
        setComboBtn();
        setExitBtn();
        setNextTurnButton();
        setCellsAction();
        Pane specialPower = ((Pane) parent.lookup("#specialPowerPane"));
        specialPower.setOnMouseEntered(mouseEvent -> {
            Label label1 = (Label) parent.lookup("#specialPowerLabel1");
            label1.setTextFill(Color.YELLOW);
            Label label2 = (Label) parent.lookup("#specialPowerLabel2");
            label2.setTextFill(Color.YELLOW);
        });
        specialPower.setOnMouseExited(mouseEvent1 -> {
            ((Label) parent.lookup("#specialPowerLabel1")).setTextFill(Color.WHITE);
            ((Label) parent.lookup("#specialPowerLabel2")).setTextFill(Color.WHITE);
        });
        updateSpecialPower();
    }

    private static void updateCollectibles() {
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (playGround.getCell(i, j).hasCollectableItem()) {
                    ImageView imageView = new ImageView(new Image("file:src/res/inGameResource/collectible.png"));
                    getCellPane(i, j).getChildren().add(imageView);
                }
            }
        }
        ChoiceBox choiceBox = ((ChoiceBox) parent.lookup("#collectibles"));
        Button button = (Button) parent.lookup("#collectibleBtn");
        button.setOnMouseClicked(mouseEvent -> {
            Object selectedItem = choiceBox.getSelectionModel().getSelectedItem();
            inGameController.getBattle().getCurrenPlayer()
                    .setSelectedCollectableItem(((Collectible) selectedItem));
            InGameRequest request = new InGameRequest("use 1 1");
            inGameController.main(request);
            inGameController.getBattle().getCurrenPlayer()
                    .setSelectedCollectableItem(null);
            updateCollectibles();
            updatePlayGround(group);
            setManas(inGameController.getBattle().getCurrenPlayer());
            alertAiAction("Collectible Item Used");
        });
        choiceBox.getItems().clear();
        //TODO its just for single player
        Player firstPlayer = inGameController.getBattle().getFirstPlayer();
        for (Collectible collectedItem : firstPlayer.getCollectedItems()) {
            choiceBox.getItems().add(collectedItem);
        }
    }

    private static void updateSpecialPower() {
        Hero hero = inGameController.getBattle().getCurrenPlayer().getHero();
        if (hero.isSpellReady()) {
            ((Label) parent.lookup("#specialPowerLabel2")).setText("Is Ready");
            ((Pane) parent.lookup("#specialPowerPane")).setOnMouseClicked(mouseEvent -> {
                InGameRequest request = new InGameRequest("use special power 1 1");
                inGameController.main(request);
                updatePlayGround(group);
                setManas(inGameController.getBattle().getCurrenPlayer());
                updateSpecialPower();
            });
        } else {
            ((Label) parent.lookup("#specialPowerLabel2")).setText("Turns Remained : " + hero.getTurnsRemained());
            ((Pane) parent.lookup("#specialPowerPane")).setOnMouseClicked(mouseEvent -> {
            });
        }
    }

    private static void setMediaViews(Group group) {
        Media mediaInsert = new Media(new File("src/res/inGameResource/insert.m4a").toURI().toString());
        MediaPlayer mediaPlayerInsert = new MediaPlayer(mediaInsert);
        MediaView mediaViewInsert = new MediaView(mediaPlayerInsert);
        Media mediaAttack = new Media(new File("src/res/inGameResource/attack.m4a").toURI().toString());
        MediaPlayer mediaPlayerAttack = new MediaPlayer(mediaAttack);
        MediaView mediaViewAttack = new MediaView(mediaPlayerAttack);
        Media mediaInsertMove = new Media(new File("src/res/inGameResource/move.m4a").toURI().toString());
        MediaPlayer mediaPlayerInsertMove = new MediaPlayer(mediaInsertMove);
        MediaView mediaViewInsertMove = new MediaView(mediaPlayerInsertMove);
        insert = mediaPlayerInsert;
        attack = mediaPlayerAttack;
        move = mediaPlayerInsertMove;
        group.getChildren().add(mediaViewAttack);
        group.getChildren().add(mediaViewInsert);
        group.getChildren().add(mediaViewInsertMove);
    }

    private static void setCellAffect(Pane pane) {
        CellAffect cellAffect = getCell(pane).getCellAffects().get(0);
        if (cellAffect instanceof HollyCellAffect) {
            pane.getChildren().
                    add(new ImageView(new Image("file:src/res/inGameResource/holly-cellaffect.gif")));
        } else if (cellAffect instanceof PoisonCellAffect) {
            pane.getChildren().
                    add(new ImageView(new Image("file:src/res/inGameResource/poison-cellaffect.gif")));
        } else {
            pane.getChildren().
                    add(new ImageView(new Image("file:src/res/inGameResource/fire-cellaffect.gif")));
        }
    }

    private static void setFlag(Pane pane) {
        pane.getChildren().add(new ImageView(new Image("file:src/res/inGameResource/flag.gif")));
    }


    private static void updatePlayGround(Group group) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Pane pane = getCellPane(i, j);
                pane.getChildren().clear();
                String path = "file:src/res/inGameResource/cellBack1.png";
                ImageView imageView = new ImageView(new Image(path));
                if (inGameController.getBattle().getPlayGround().getCell(i, j).hasCellAffect())
                    setCellAffect(pane);
                if (inGameController.getBattle().getPlayGround().getCell(i, j).getFlag() != null)
                    setFlag(pane);
                pane.setOnDragOver(dragEvent -> {
                    if (dragEvent.getDragboard().hasImage() && isMarked(pane, false)) {
                        dragEvent.acceptTransferModes(TransferMode.ANY);
                    }
                    dragEvent.consume();
                });
                pane.setOnDragEntered(dragEvent -> pane.getChildren().add(imageView));
                pane.setOnDragExited(dragEvent -> pane.getChildren().remove(imageView));
                pane.setOnDragDropped(dragEvent -> {
                    Dragboard db = dragEvent.getDragboard();
                    if (db.hasImage()) {
                        int x = (pane.getId().charAt(pane.getId().length() - 2)) - 48;
                        int y = pane.getId().charAt(pane.getId().length() - 1) - 48;
                        InGameRequest request = new InGameRequest(
                                "insert " + db.getString() + " in " + x + " " + y);
                        //TODO spell inserting
                        /*int flag = 0;
                        Card card = inGameController.getBattle().getCurrenPlayer().getHand().getCardByName(db.getString());
                        if (card instanceof Spell) {
                            flag = 1;
                            //spellCast(inGameController.getBattle().getPlayGround().getCell(x, y));
                        }*/
                        inGameController.main(request);
                        setMediaViews(group);
                        insert.play();
                        updateHand();
                        setManas(inGameController.getBattle().getCurrenPlayer());
                        updatePlayGround(group);
                        /*//TODO
                        if (flag == 1){
                            spellCast(inGameController.getBattle().getPlayGround().getCell(x,y));
                        }*/
                        dragEvent.setDropCompleted(true);
                    }
                    dragEvent.setDropCompleted(false);
                });
                pane.setOnMouseClicked(mouseEvent -> {
                    Cell cell = getCell(pane);
                    if (isMarked(pane, false)) {
                        Cell cellFirst = ((Minion) inGameController.getBattle()
                                .getCurrenPlayer().getSelectedCard()).getCell();
                        InGameRequest request = new
                                InGameRequest("move to " + cell.getX() + " " + cell.getY());
                        inGameController.main(request);
                        //TODO
                        updatePlayGround(group);
                        if (inGameController.getBattle().getCurrenPlayer().getSelectedCard() == null) {
                            setMediaViews(group);
                            move.play();
                            moveTo(cellFirst, cell);
                        }
                    }
                });
            }
        }
        updateCollectibles();
        updatePlayGround(inGameController.getBattle().getSecondPlayer());
        updatePlayGround(inGameController.getBattle().getFirstPlayer());
    }

    public static void showError(InGameErrorType errorType) {
        if (errorType == InGameErrorType.CAN_NOT_MOVE) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't Move");
            alert.setContentText("The Player You Select Can't Move At This Turn");
            alert.show();
        } else if (errorType == InGameErrorType.HERO_NOT_HAVE_SPELL) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Special Power");
            alert.setContentText("Your Hero Doesn't Have Special Power :(");
            alert.show();
        }
    }

    public static void notEnoughMana() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Not Enough Mana");
        alert.setContentText("You don't Have Enough Mana :(");
        alert.show();
    }

    public static void cantAttack(String battleID) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Can't Attack");
        alert.setContentText("Card with " + battleID + " can't attack");
        alert.show();
    }

    private static void updatePlayGround(Player player) {
        for (Minion minion : player.getMinionsInPlayGround()) {
            Cell cell = minion.getCell();
            Pane pane = getCellPane(cell.getX(), cell.getY());
            ImageView imageView = getImageView(minion);
            setImageRotateForPlayGround(imageView);
            //TODO for combo minions :
            if (minion.getSpecialPower() instanceof ComboSpecialPower && !isComboMarked(pane))
                setComboCell(minion);
            pane.getChildren().add(imageView);
            pane.setOnMouseClicked(mouseEvent -> {
                if (minion.getSpecialPower() instanceof ComboSpecialPower)
                    descLabel.setText("For Combo Attack Press (C)\n" + minion.toString());
                else
                    descLabel.setText(minion.toString());
                if (inGameController.getBattle().getCurrenPlayer().equals(player)) {
                    if (!isCombo) {
                        ArrayList<Cell> possibleForMove = getPossibleCellsForMove(minion);
                        if (possibleForMove.size() > 0) {
                            if (isMarked(getCellPane(possibleForMove.get(0).getX()
                                    , possibleForMove.get(0).getY()), false)) {
                                deleteMarkCells(false);
                                player.setSelectedCard(null);
                            } else {
                                markCells(possibleForMove, false);
                                player.setSelectedCard(minion);
                            }
                        }
                        ArrayList<Cell> possibles = getPossibleCellsForAttack(minion);
                        if (possibles.size() > 0) {
                            if (isMarked(getCellPane(possibles.get(0).getX(), possibles.get(0).getY()), true)) {
                                deleteMarkCells(true);
                                player.setSelectedCard(null);
                            } else {
                                markCells(possibles, true);
                                player.setSelectedCard(minion);
                            }
                        }
                    } else {
                        //for combo
                        if (minion.getSpecialPower() instanceof ComboSpecialPower) {
                            if (!comboCards.contains(minion))
                                comboCards.add(minion);
                            descLabel.setText(getComboSelectedCards());
                            deleteMarkCells(false);
                            markPossibleComboCells(player);
                        }
                    }
                } else if (isMarked(pane, true)) {
                    setMediaViews(group);
                    attack.play();
                    InGameRequest request = new InGameRequest("attack " + minion.getBattleID());
                    inGameController.main(request);
                    updatePlayGround(group);
                } else if (isMarked(pane, false)) {
                    if (isCombo) {
                        comboAttack(minion);
                    }
                }
            });
        }
    }

    private static void comboAttack(Minion target) {
        String request = "attack combo " + target.getBattleID();
        for (Minion comboCard : comboCards) {
            request += " " + comboCard.getBattleID();
        }
        inGameController.main(new InGameRequest(request));
        isCombo = false;
        deleteMarkCells(false);
        comboCards = new ArrayList<>();
        setMediaViews(group);
        attack.play();
    }

    private static void markPossibleComboCells(Player player) {
        String path = "file:src/res/inGameResource/markCell.gif";
        for (int i = 0; i < 5; i++) {
            m:
            for (int j = 0; j < 9; j++) {
                Cell cell = inGameController.getBattle().getPlayGround().getCell(i, j);
                if (cell.hasCardOnIt() && !cell.getMinionOnIt().getPlayer().equals(player)) {
                    for (Minion comboCard : comboCards) {
                        if (!comboCard.isValidCell(cell))
                            continue m;
                    }
                    if (!isMarked(getCellPane(i, j), false))
                        getCellPane(i, j).getChildren().add(new ImageView(new Image(path)));
                }
            }
        }
    }

    private static String getComboSelectedCards() {
        String result = "Select Combo Cards Then Select Target\nSelected Cards:\n";
        for (Minion comboCard : comboCards) {
            result += comboCard.getBattleID() + "\n";
        }
        return result;
    }

    private static boolean isComboMarked(Pane pane) {
        String path = "file:src/res/inGameResource/comboCell.gif";
        for (Node child : pane.getChildren()) {
            if (child instanceof ImageView && ((ImageView) child).getImage().getUrl().equals(path))
                return true;
        }
        return false;
    }

    private static void setComboCell(Minion minion) {
        String path = "file:src/res/inGameResource/comboCell.gif";
        /*for (Minion minion1 : minion.getPlayer().getMinionsInPlayGround()) {
            if (minion1.getSpecialPower() instanceof ComboSpecialPower) {
                getCellPane(minion1.getCell().getX(), minion1.getCell().getY())
                        .getChildren().add(new ImageView(new Image(path)));
            }
        }*/
        getCellPane(minion.getCell().getX(), minion.getCell().getY())
                .getChildren().add(new ImageView(new Image(path)));
    }

    private static void deleteComboCells() {
        String path = "file:src/res/inGameResource/comboCell.gif";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Pane pane = getCellPane(i, j);
                for (Node child : pane.getChildren()) {
                    if (child instanceof ImageView && ((ImageView) child).getImage().getUrl().equals(path)) {
                        pane.getChildren().remove(child);
                        break;
                    }
                }
            }
        }
    }

    public static ArrayList<Cell> getPossibleCellsForMove(Minion minion) {
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (minion.isValidCellForMove(playGround.getCell(i, j)))
                    result.add(playGround.getCell(i, j));
            }
        }
        return result;
    }

    private static Cell getCell(Pane pane) {
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        int x = pane.getId().charAt(pane.getId().length() - 2) - 48;
        int y = pane.getId().charAt(pane.getId().length() - 1) - 48;
        return playGround.getCell(x, y);
    }

    public static ArrayList<Cell> getPossibleCellsForAttack(Minion minion) {
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        ArrayList<Cell> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (minion.isValidCell(playGround.getCell(i, j))
                        && playGround.getCell(i, j).hasCardOnIt()
                        && !playGround.getCell(i, j).getMinionOnIt().getPlayer().equals(minion.getPlayer()))
                    result.add(playGround.getCell(i, j));
            }
        }
        return result;
    }

    private static boolean isMarked(Pane pane, boolean attack) {
        String mark = "file:src/res/inGameResource/markCell.gif";
        if (attack)
            mark = "file:src/res/inGameResource/markAttack.gif";
        for (Node child : pane.getChildren()) {
            if (child instanceof ImageView && ((ImageView) child).getImage().getUrl().equals(mark)) {
                return true;
            }
        }
        return false;
    }

    private static Pane getCellPane(int x, int y) {
        return ((Pane) parent.lookup("#cell" + x + y));
    }

    private static void showCollectiblesInCells(ArrayList<Collectible> items) {

    }

    public static ArrayList<Cell> getPossibleCells(Card card) {
        if (card instanceof Minion) {
            //return ((Minion) card).getPlayer().getCellsToInsertMinion();
            ArrayList<Cell> result = new ArrayList<>();
            PlayGround playGround = inGameController.getBattle().getPlayGround();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 9; j++) {
                    if (((Minion) card).getPlayer().getCellsToInsertMinion().contains(playGround.getCell(i, j))
                            && !playGround.getCell(i, j).hasCardOnIt())
                        result.add(playGround.getCell(i, j));
                }
            }
            return result;
        } else if (card instanceof Spell) {
            return getValidCellsForSpell((Spell) card);
        } else
            return null;
    }

    private static void updateHand() {
        Player player = inGameController.getBattle().getCurrenPlayer();
        Hand hand = player.getHand();
        ArrayList<Card> cards = hand.getCards();
        Card next = hand.getNext();
        int count = 0;
        for (int i = 0; i < 5; i++) {
            if (i < cards.size()) {
                Card card = cards.get(i);
                ImageView imageView = getImageView(card);
                Pane pane = ((Pane) parent.lookup("#hand" + count++));
                pane.getChildren().clear();
                pane.getChildren().add(imageView);
                pane.setOnMouseClicked(mouseEvent -> descLabel.setText(card.toString()));
                pane.setOnDragDone(dragEvent -> deleteMarkCells(false));
                pane.setOnDragDetected(mouseEvent -> {
                    descLabel.setText(card.toString());
                    markCells(getPossibleCells(card), false);
                    Dragboard db = pane.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putImage(imageView.getImage());
                    clipboardContent.putUrl(imageView.getImage().getUrl());
                    clipboardContent.putString(card.getName());//TODO
                    db.setContent(clipboardContent);
                    mouseEvent.consume();
                });
            } else {
                Pane pane = ((Pane) parent.lookup("#hand" + count++));
                pane.getChildren().clear();
                pane.setOnMouseClicked(mouseEvent -> {
                });
                pane.setOnDragDone(dragEvent -> {
                });
                pane.setOnDragDetected(mouseEvent -> {
                });
            }
        }

        Pane nextCard = ((Pane) parent.lookup("#nextCard"));
        nextCard.getChildren().clear();
        if (next != null) {
            ImageView nextImageView = getImageView(next);
            nextImageView.setOnMouseClicked(mouseEvent -> descLabel.setText(next.toString()));
            nextCard.getChildren().add(nextImageView);
        }
    }

    private static ArrayList<Cell> getValidCellsForSpell(Spell spell) {
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (spell.isValidTarget(playGround.getCell(i, j))) {
                    cells.add(playGround.getCell(i, j));
                }
            }
        }
        return cells;
    }

    private static void markCells(ArrayList<Cell> cells, boolean attack) {
        String path = "file:src/res/inGameResource/markCell.gif";
        if (attack)
            path = "file:src/res/inGameResource/markAttack.gif";
        for (Cell cell : cells) {
            Pane pane = getCellPane(cell.getX(), cell.getY());
            ImageView imageView = new ImageView(new Image(path));
            pane.getChildren().add(imageView);
        }
    }

    private static void deleteMarkCells(boolean attack) {
        String path = "file:src/res/inGameResource/markCell.gif";
        if (attack)
            path = "file:src/res/inGameResource/markAttack.gif";
        PlayGround playGround = inGameController.getBattle().getPlayGround();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Pane pane = ((Pane) parent.lookup("#cell" + i + j));
                for (Node child : pane.getChildren()) {
                    if (child instanceof ImageView) {
                        if (((ImageView) child).getImage().getUrl().equals(path)) {
                            pane.getChildren().remove(child);
                            break;
                        }
                    }
                }
            }
        }
    }

    private static ImageView getImageView(Card card) {
        if (pathes.containsKey(card.getName()))
            return new ImageView(new Image(pathes.get(card.getName())));
        ImageView imageView;
        if (card instanceof Spell) {
            pathes.put(card.getName(), "file:src/res/inGameResource/spell.png");
            imageView = new ImageView(new Image("file:src/res/inGameResource/spell.png"));
        } else if (card instanceof Hero) {
            Random r = new Random();
            int index = r.nextInt(5) + 1;
            pathes.put(card.getName(), "file:src/res/heroes/" + index + ".gif");
            imageView = new ImageView(new Image("file:src/res/heroes/" + index + ".gif"));
        } else {
            Random r = new Random();
            int index = r.nextInt(14) + 1;
            pathes.put(card.getName(), "file:src/res/minions/" + index + ".gif");
            imageView = new ImageView(new Image("file:src/res/minions/" + index + ".gif"));
        }
        return imageView;
    }

    private static ImageView getImageView(Item item) {
        return null;
    }

    private static void setCellsAction() {
        //hand action - >
        for (int i = 0; i < 5; i++) {
            Pane pane = ((Pane) parent.lookup("#hand" + i));
            pane.setOnMouseEntered(mouseEvent -> {
                int x = pane.getId().charAt(pane.getId().length() - 1) - 48;
                x++;
                ((ImageView) parent.lookup("#handImage" + x)).setImage(
                        new Image("file:src/res/inGameResource/handCard2.png"));
            });
            pane.setOnMouseExited(mouseEvent -> {
                int x = pane.getId().charAt(pane.getId().length() - 1) - 48;
                x++;
                ((ImageView) parent.lookup("#handImage" + x)).setImage(
                        new Image("file:src/res/inGameResource/hand-cards.png"));
            });
        }
        //
        String path = "file:src/res/inGameResource/cellBack1.png";
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                ImageView imageView = new ImageView(new Image(path));
                Pane pane = ((Pane) parent.lookup("#cell" + i + j));
                pane.setOnMouseEntered(mouseEvent -> {
                    pane.getChildren().add(imageView);
                });
                pane.setOnMouseExited(mouseEvent -> pane.getChildren().remove(imageView));
            }
        }
    }

    private static void setManas(Player player) {
        int mana = player.getMana();
        boolean isFirst = false;
        if (player.equals(inGameController.getBattle().getFirstPlayer()))
            isFirst = true;
        String num = (isFirst) ? ("1") : ("2");
        String path = "file:src/res/inGameResource/mana.png";
        for (int i = 1; i <= 9; i++) {
            if (i <= mana) {
                Pane pane2 = ((Pane) parent.lookup("#mana" + num + (i)));
                pane2.getChildren().add(new ImageView(new Image(path)));
            } else {
                Pane pane2 = ((Pane) parent.lookup("#mana" + num + (i)));
                pane2.getChildren().clear();
            }
        }
    }

    private static void setExitBtn() {
        Pane pane = ((Pane) parent.lookup("#closePane"));
        ImageView imageView = ((ImageView) pane.getChildren().get(0));
        pane.setOnMouseEntered(mouseEvent -> {
            imageView.setImage(new Image("file:src/res/inGameResource/close_button2.png"));
        });
        pane.setOnMouseExited(mouseEvent -> {
            imageView.setImage(new Image("file:src/res/inGameResource/button_close.png"));
        });
        pane.setOnMouseClicked(mouseEvent -> {
            Stage newStage = new Stage();
            Group group = new Group();
            Scene scene = new Scene(group, 300, 300);
            newStage.setTitle("Exit Request");
            newStage.setScene(scene);
            Button button = new Button("Exit Without Save");
            Button button1 = new Button("Resume");
            button.setLayoutX(50);
            button1.setLayoutX(200);
            button.setLayoutY(20);
            button1.setLayoutY(20);
            group.getChildren().add(button);
            group.getChildren().add(button1);
            newStage.show();
            button.setOnMouseClicked(mouseEvent1 -> {
                AccountMenu.getInstance().accountMenuShow(new Stage(), new AccountController());
                stage.close();
                newStage.close();
                backGroundMusic.stop();
            });
            button1.setOnMouseClicked(mouseEvent1 -> {
                newStage.close();
            });
        });
    }

    private static void setCursor(Scene scene) {
        scene.setCursor(new ImageCursor(new Image("src/res/inGameResource/cursor.png")));
    }

    static String aiMove;

    private static void setNextTurnButton() {
        Image image1 = new Image("src/res/inGameResource/nextTurn.png");
        Image image2 = new Image("file:src\\res\\inGameResource\\nextTurn2.png");
        Pane pane = ((Pane) parent.lookup("#nextTurnPane"));
        ImageView imageView = ((ImageView) parent.lookup("#nextTurnButtonBack"));
        Label label = ((Label) parent.lookup("#nextTurnBtnLabel"));
        pane.setOnMouseEntered(mouseEvent -> {
            imageView.setImage(image2);
            label.setTextFill(Color.BLACK);
        });
        pane.setOnMouseExited(mouseEvent -> {
            imageView.setImage(image1);
            label.setTextFill(Color.web("#2884dd"));
        });
        pane.setOnMouseClicked(mouseEvent -> {
            InGameRequest request = new InGameRequest("end turn");
            inGameController.main(request);
            updatePlayGround(group);
            //
            doAiAnimations(aiMove);
            //
            updateHand();
            setManas(inGameController.getBattle().getFirstPlayer());
            setManas(inGameController.getBattle().getSecondPlayer());
            updateSpecialPower();
        });
    }

    private static void setImageRotateForPlayGround(ImageView imageView) {
        imageView.setRotate(-67.4);
        imageView.setRotationAxis(new Point3D(-13, -9, -11));
    }


    private MediaView getBackGroundMusic() {
        Media media = new Media(new File("src/res/inGameResource/music_playmode.m4a").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        backGroundMusic = mediaPlayer;
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        return new MediaView(mediaPlayer);
    }
}
