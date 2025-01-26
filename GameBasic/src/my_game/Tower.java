package my_game;

import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;
import ui_elements.ScreenPoint;

public class Tower extends GameObject {

    private static final int FRAME_WIDTH = 70;  // Example frame width
    private static final int FRAME_HEIGHT = 130; // Example frame height
    private static final int TOTAL_FRAMES = 6;  // Total animation frames
    private AnimatedImage animatedImage;


    public Tower(String id, ScreenPoint location, String spritePath) {
        super(id, location);

        // Initialize the animated image
        this.animatedImage = new AnimatedImage(id, FRAME_WIDTH, FRAME_HEIGHT, false);
        this.animatedImage.setSpriteSheet(spritePath, TOTAL_FRAMES);
        this.animatedImage.moveToLocation(location.x, location.y);
    }

    public void moveToLocation(ScreenPoint newLocation) {
        this.location = newLocation;
        animatedImage.moveToLocation(newLocation.x, newLocation.y);
    }

    public void nextFrame() {
        animatedImage.nextFrame();
    }

    @Override
    public void gameStep() {
        nextFrame();
    }

    @Override
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        animatedImage.setzOrder(1); 
        canvas.addShape(animatedImage);
        canvas.revalidate();
        canvas.repaint();
    }

    @Override
    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(animatedImage.getId());
        canvas.revalidate();
        canvas.repaint();
    }

}
