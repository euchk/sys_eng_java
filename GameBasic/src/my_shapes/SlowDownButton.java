package my_shapes;

import my_game.GameControl;

public class SlowDownButton extends ShapeButton {
    
    private GameControl gameControl;
    private final static String IMAGE_SRC = "resources/objects/buttons/slowDown.png";
    private final static int WIDTH = 70;
    private final static int HEIGHT = 85;


    public SlowDownButton(String id, int posX, int posY, GameControl gameControl) {
        super(id, WIDTH, HEIGHT, posX, posY, IMAGE_SRC, "Slow Down");
        this.gameControl = gameControl;
    }

    @Override
    protected boolean onClick() {
        // Signal GameControl that this button was clicked.
        return gameControl.slowDownClicked();
    }
}
