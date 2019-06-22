package view;

import controller.InGameController;
import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import javafx.stage.Stage;
import model.*;
import model.cards.Card;
import model.cards.Minion;
import model.enumerations.GameMode;
import model.items.Item;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class InGameMethodsAndSource {// a resource for graphical in game view

    public static HashMap<Integer, int[]> positions = new HashMap<>();

    static {
        positions.put(0, new int[]{298, 292});
        positions.put(1, new int[]{336, 274});
        positions.put(2, new int[]{372, 252});
        positions.put(3, new int[]{403, 234});
        positions.put(4, new int[]{439, 217});
        positions.put(5, new int[]{475, 199});
        positions.put(6, new int[]{507, 181});
        positions.put(7, new int[]{543, 165});
        positions.put(8, new int[]{580, 146});
        positions.put(9, new int[]{339, 313});
        positions.put(10, new int[]{371, 294});
        positions.put(11, new int[]{407, 277});
        positions.put(12, new int[]{437, 260});
        positions.put(13, new int[]{475, 244});
        positions.put(14, new int[]{507, 227});
        positions.put(15, new int[]{540, 212});
        positions.put(16, new int[]{579, 193});
        positions.put(17, new int[]{611, 175});
        positions.put(18, new int[]{375, 340});
        positions.put(19, new int[]{412, 326});
        positions.put(20, new int[]{442, 311});
        positions.put(21, new int[]{478, 291});
        positions.put(22, new int[]{513, 274});
        positions.put(23, new int[]{546, 259});
        positions.put(24, new int[]{580, 242});
        positions.put(25, new int[]{620, 223});
        positions.put(26, new int[]{653, 205});
        positions.put(27, new int[]{407, 367});
        positions.put(28, new int[]{444, 350});
        positions.put(29, new int[]{481, 332});
        positions.put(30, new int[]{517, 317});
        positions.put(31, new int[]{551, 300});
        positions.put(32, new int[]{581, 281});
        positions.put(33, new int[]{616, 264});
        positions.put(34, new int[]{648, 244});
        positions.put(35, new int[]{686, 231});
        positions.put(36, new int[]{446, 391});
        positions.put(37, new int[]{481, 375});
        positions.put(38, new int[]{518, 355});
        positions.put(39, new int[]{551, 342});
        positions.put(40, new int[]{585, 326});
        positions.put(41, new int[]{618, 305});
        positions.put(42, new int[]{653, 293});
        positions.put(43, new int[]{687, 271});
        positions.put(44, new int[]{720, 255});
    }

    public static void showReplay(BattleResult battleResult) {
        Battle battle = getBattle(battleResult);
        ArrayList<InGameRequest> inGameRequests = battleResult.getInGameRequests();
        GraphicalInGameView view = new GraphicalInGameView();
        //
        battle.startBattle();
        try {
            view.showGame(new Stage(), battle, null);
            GraphicalInGameView.setIsReplay(true);
            InGameController inGameController = GraphicalInGameView.getInGameController();
            for (InGameRequest request : inGameRequests) {
                ////////start///////
                System.out.println("before request : " );
                for (Card card : battle.getCurrenPlayer().getHand().getCards()) {
                    System.out.print(card.getName() + " ");
                }
                InGameView.showMinions(battle.getCurrenPlayer());
                //TODO
                System.out.println(request + " : ");
                inGameController.main(request);
                //
                view.updateEveryThing();
                //////////end///////
                /*try {
                    Thread.currentThread().sleep(2000);//TODO timing
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Battle getBattle(BattleResult battleResult) {
        Battle battle;
        int mode;
        GameMode gameMode = battleResult.getGameMode();
        if (gameMode == GameMode.HERO_KILL)
            mode = 1;
        else if (gameMode == GameMode.ONE_FLAG)
            mode = 2;
        else
            mode = 3;
        if (battleResult.isSinglePlayer()) {
            if (battleResult.getLevel() == 0) {//second constructor
                battle = new SinglePlayerBattle(mode, battleResult.getDeck().getCopy()
                        , Account.findAccount(battleResult.getFirstPlayer()), battleResult.getNumberOfFlags());
            } else {
                battle = new SinglePlayerBattle(battleResult.getLevel()
                        , Account.findAccount(battleResult.getFirstPlayer()));
            }
            return new MultiPlayerBattle(((SinglePlayerBattle) battle));
        } else {
            return new MultiPlayerBattle(Account.findAccount(battleResult.getFirstPlayer())
                    , (battleResult.getSecondPlayer()), mode, battleResult.getNumberOfFlags());
        }
    }

    public static void showAlertAtTheBeginning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

}
