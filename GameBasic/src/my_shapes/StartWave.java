package my_shapes;

import my_game.GameControl;

public class StartWave extends ShapeButton {
    private GameControl gameControl;
    private final static String IMAGE_SRC = "resources/objects/buttons/startWave.png";

    public StartWave(String id, int posX, int posY, GameControl gameControl) {
        super(id, posX, posY, IMAGE_SRC, "Start Wave");
        this.gameControl = gameControl;
    }

    @Override
    protected void onClick() {
        // Signal GameControl that this button was clicked.
        gameControl.startWaveClicked();
    }
}
