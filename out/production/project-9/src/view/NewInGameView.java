package view;

import controller.InGameController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.*;
import model.cards.Card;
import model.cards.Hero;
import model.cards.Minion;
import model.cards.Spell;
import model.enumerations.InGameErrorType;
import model.items.Collectible;
import model.items.Item;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class NewInGameView {
    private static InGameController inGameController;
    private static TextArea descLabel;
    private static Parent parent;
    private static HashMap<String, String> pathes = new HashMap<>();
    private static MediaPlayer insert;
    private static MediaPlayer attack;
    private static MediaPlayer move;
    private static Group group;

    public void showGame(Stage stage, Battle battle) throws IOException {
        inGameController = new InGameController(battle);
        Group group = new Group();
        NewInGameView.group = group;
        FXMLLoader fxmlLoader = new FXMLLoader(new URL("file:src/res/GameView.fxml"));
        Parent parent = fxmlLoader.load();
        NewInGameView.parent = parent;
        group.getChildren().add(parent);
        descLabel = ((TextArea) parent.lookup("#description"));
        Scene scene = new Scene(group);
        setCursor(scene);
        setExitBtn();
        stage.getIcons().add(new Image("src\\res\\icon.jpg"));
        setNextTurnButton();
        setCellsAction();
        //
        //TODO
        battle.startBattle();
        setManas(battle.getFirstPlayer());
        setManas(battle.getSecondPlayer());
        updateHand();
        updatePlayGround(group);
        //

        //
        MediaView mediaView = getBackGroundMusic();
        group.getChildren().add(mediaView);
        stage.setTitle("Duelyst");
        stage.setScene(scene);
        stage.show();
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
    }

    private static void updatePlayGround(Group group) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                Pane pane = getCellPane(i, j);
                pane.getChildren().clear();
                String path = "file:src/res/inGameResource/cellBack1.png";
                ImageView imageView = new ImageView(new Image(path));
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
                        int firstMana = inGameController.getBattle().getCurrenPlayer().getMana();
                        InGameRequest request = new InGameRequest(
                                "insert " + db.getString() + " in " + x + " " + y);
                        inGameController.main(request);
                        //if (inGameController.getBattle().getCurrenPlayer().getMana() != firstMana) {
                        setMediaViews(group);
                        insert.play();
                        updateHand();
                        setManas(inGameController.getBattle().getCurrenPlayer());
                        updatePlayGround(inGameController.getBattle().getCurrenPlayer());
                        dragEvent.setDropCompleted(true);
                        //}
                    }
                    dragEvent.setDropCompleted(false);
                });
                pane.setOnMouseClicked(mouseEvent -> {
                    Cell cell = getCell(pane);
                    if (isMarked(pane, false)) {
                        InGameRequest request = new
                                InGameRequest("move to " + cell.getX() + " " + cell.getY());
                        inGameController.main(request);
                        setMediaViews(group);
                        move.play();
                        updatePlayGround(group);
                    }
                });
            }
        }
        updatePlayGround(inGameController.getBattle().getSecondPlayer());
        updatePlayGround(inGameController.getBattle().getFirstPlayer());
    }

    public static void showError(InGameErrorType errorType) {
        if (errorType == InGameErrorType.CAN_NOT_MOVE) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Can't Move");
            alert.setContentText("The Player You Select Can't Move At This Turn");
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
            pane.getChildren().clear();
            ImageView imageView = getImageView(minion);
            setImageRotateForPlayGround(imageView);
            pane.getChildren().add(imageView);
            pane.setOnMouseClicked(mouseEvent -> {
                descLabel.setText(minion.toString());
                if (inGameController.getBattle().getCurrenPlayer().equals(player)) {
                    ArrayList<Cell> possibleForMove = getPossibleCellsForMove(minion);
                    if (possibleForMove.size() > 0) {
                        if (isMarked(getCellPane(possibleForMove.get(0).getX()
                                , possibleForMove.get(0).getY()), false)) {
                            deleteMarkCells(possibleForMove, false);
                            player.setSelectedCard(null);
                        } else {
                            markCells(possibleForMove, false);
                            player.setSelectedCard(minion);
                        }
                    }
                    ArrayList<Cell> possibles = getPossibleCellsForAttack(minion);
                    if (possibles.size() > 0) {
                        if (isMarked(getCellPane(possibles.get(0).getX(), possibles.get(0).getY()), true)) {
                            deleteMarkCells(possibles, true);
                            player.setSelectedCard(null);
                        } else {
                            markCells(possibles, true);
                            player.setSelectedCard(minion);
                        }
                    }
                } else if (isMarked(pane, true)) {
                    setMediaViews(group);
                    attack.play();
                    InGameRequest request = new InGameRequest("attack " + minion.getBattleID());
                    inGameController.main(request);
                    updatePlayGround(group);
                }
            });

        }
    }

    private static ArrayList<Cell> getPossibleCellsForMove(Minion minion) {
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

    private static ArrayList<Cell> getPossibleCellsForAttack(Minion minion) {
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

    private static ArrayList<Cell> getPossibleCells(Card card) {
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
        for (Card card : cards) {
            ImageView imageView = getImageView(card);
            Pane pane = ((Pane) parent.lookup("#hand" + count++));
            pane.getChildren().clear();
            pane.getChildren().add(imageView);
            pane.setOnMouseClicked(mouseEvent -> descLabel.setText(card.toString()));
            pane.setOnDragDone(dragEvent -> deleteMarkCells(getPossibleCells(card), false));
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
        }
        Pane nextCard = ((Pane) parent.lookup("#nextCard"));
        nextCard.getChildren().clear();
        ImageView nextImageView = getImageView(next);
        nextImageView.setOnMouseClicked(mouseEvent -> descLabel.setText(next.toString()));
        nextCard.getChildren().add(nextImageView);
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

    private static void deleteMarkCells(ArrayList<Cell> cells, boolean attack) {
        String path = "file:src/res/inGameResource/markCell.gif";
        if (attack)
            path = "file:src/res/inGameResource/markAttack.gif";
        for (Cell cell : cells) {
            int x = cell.getX();
            int y = cell.getY();
            Pane pane = ((Pane) parent.lookup("#cell" + x + y));
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

    }

    private static void setCursor(Scene scene) {
        scene.setCursor(new ImageCursor(new Image("src/res/inGameResource/cursor.png")));
    }

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
            updateHand();
            setManas(inGameController.getBattle().getFirstPlayer());
            setManas(inGameController.getBattle().getSecondPlayer());
        });
    }

    private static void setImageRotateForPlayGround(ImageView imageView) {
        imageView.setRotate(-67.4);
        imageView.setRotationAxis(new Point3D(-13, -9, -11));
    }


    private MediaView getBackGroundMusic() {
        Media media = new Media(new File("src/res/inGameResource/music_playmode.m4a").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        return new MediaView(mediaPlayer);
    }
}
