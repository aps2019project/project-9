package controller;

import javafx.stage.Stage;
import model.Player;
import view.GraveYardView;

public class GraveYardController {
    private GraveYardView view;

    public GraveYardController(Player player) {
        view = new GraveYardView(player);
    }

    public void start() {
        Stage stage = new Stage();
        view.start(stage);
    }
}
