package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import shapes.AnimatedImage;
import ui_elements.ScreenPoint;

public class Warrior implements ShapeListener {

    private final String imageID = "warrior";
    private final String spritePath = "resources/sprites/sprite_sheet1.png";
    private final int frameWidth = 128;
    private final int frameHeight = 128;
    private final int totalRows = 10;
    private final int totalCols = 10;

    // Frame counts for each row (indexed by row)
    private final int[] frameCounts = {4, 3, 4, 3, 3, 6, 10, 8, 2, 8};

    private int currentRow = 0;
    private int frameCount = frameCounts[currentRow];

    private AnimatedImage sprite;
    private ScreenPoint location;

    private boolean isDisabled = false; // Stops animation when true

    public Warrior(ScreenPoint startLocation) {
        setLocation(startLocation);
    }

    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        sprite = new AnimatedImage(imageID, frameWidth, frameHeight, false);
        sprite.setSpriteSheet(spritePath, totalRows, totalCols);
        sprite.setAnimationRow(currentRow, frameCount);
        sprite.moveToLocation(location.x, location.y);
        sprite.setShapeListener(this);
        canvas.addShape(sprite);
    }

    public void nextFrame() {
        if (!isDisabled) { // Don't animate when disabled
            sprite.nextFrame();
        }
    }

    public void switchToNextRow() {
        currentRow = (currentRow + 1) % totalRows;
        frameCount = frameCounts[currentRow];
        sprite.setAnimationRow(currentRow, frameCount);
    }

    public void setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void moveLocation(int dx, int dy) {
        location.x += dx;
        location.y += dy;
        sprite.move(dx, dy);
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(ScreenPoint location) {
        this.location = location;
    }

    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        switchToNextRow();
    }

    @Override public void shapeMoved(String shapeID, int dx, int dy) {}
    @Override public void shapeStartDrag(String shapeID) {}
    @Override public void shapeEndDrag(String shapeID) {}
    @Override public void shapeRightClicked(String shapeID, int x, int y) {}
    @Override public void mouseEnterShape(String shapeID, int x, int y) {}
    @Override public void mouseExitShape(String shapeID, int x, int y) {}

}
