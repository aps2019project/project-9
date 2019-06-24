package controller;

import model.Player;
import view.GraveYardView;

public class GraveYardController {
    private Player player;
    private GraveYardView view;

    public GraveYardController(Player player) {
        this.player = player;
        view = new GraveYardView(player);
    }

    public void main() {
        view.start();
    }
}
