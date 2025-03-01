package my_shapes;

import my_game.GameControl;

public class StartWaveButton extends ShapeButton {
    private GameControl gameControl;
    private final static String IMAGE_SRC = "resources/objects/buttons/startWave.png";
    private final static String IMAGE_SRC_DIS = "resources/objects/buttons/startWaveDisabled.png";
    private final static int WIDTH = 70;
    private final static int HEIGHT = 85;


    public StartWaveButton(String id, int posX, int posY, GameControl gameControl) {
        super(id, WIDTH, HEIGHT, posX, posY, IMAGE_SRC, IMAGE_SRC_DIS, "Start Wave");
        this.gameControl = gameControl;
    }

    @Override
    protected void onClick() {
        // Signal GameControl that this button was clicked.
        gameControl.startWaveClicked();
    }
}
