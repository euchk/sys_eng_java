package my_game;

import base.Game;
import base.GameCanvas;
import my_shapes.AnimatedImage;
import ui_elements.ScreenPoint;

public class StaticAnimatedObject extends GameObject {
    
    private AnimatedImage animatedImage;

    public StaticAnimatedObject(String id, ScreenPoint location, String spriteSheetPath, int totalFrames, int frameWidth, int frameHeight, boolean isMirrored) {
        super(id, location);
        this.animatedImage = new AnimatedImage(id + "_img", frameWidth, frameHeight, isMirrored);
        this.animatedImage.setSpriteSheet(spriteSheetPath, totalFrames);
        this.animatedImage.moveToLocation(location.x, location.y);
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
        animatedImage.setzOrder(location.y);
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
