package controller;

import javafx.stage.Stage;
import model.Player;
import view.GraphicalGraveYard;

public class GraveYardController {
    private GraphicalGraveYard graveYard;

    public GraveYardController(Player player) {
        graveYard = new GraphicalGraveYard(player);
    }

    public void start() {
        Stage stage = new Stage();
        graveYard.start(stage);
    }
}
