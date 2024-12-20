package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;

public class Character {
    private String id;
    private ScreenPoint location;
    private AnimatedImage animatedImage;

    // Enum for directions or actions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection;
    
    public Character(ScreenPoint startLocation, String id, String spriteSheetPath, 
                    int frameWidth, int frameHeight, int totalFrames) {
        this.id = id;
        this.location = startLocation;
        this.currentDirection = Direction.DOWN; // Default facing direction

        // Initialize AnimatedImage instance
        this.animatedImage = new AnimatedImage(id, spriteSheetPath, frameWidth, frameHeight, totalFrames);
        this.animatedImage.setPosition(location.x, location.y);
        this.animatedImage.setzOrder(3); // Default z-order
        this.animatedImage.setDraggable(false);
    }

    public String getId() {
        return id;
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public void setLocation(int x, int y) {
        this.location.x = x;
        this.location.y = y;
        animatedImage.setPosition(x, y);
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void move(int dx, int dy) {
        location.x += dx;
        location.y += dy;
        animatedImage.move(dx, dy);
        animatedImage.nextFrame(); // Update animation frame when moving

        // Update direction based on movement
        if (dx > 0) setDirection(Direction.RIGHT);
        else if (dx < 0) setDirection(Direction.LEFT);
        else if (dy > 0) setDirection(Direction.DOWN);
        else if (dy < 0) setDirection(Direction.UP);
    }

    // Method for periodic calls
    public void periodicAction() {
        animatedImage.nextFrame();
    }

    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();        

        // Set z-order for animatedImage
        // TODO: dynamicaly update zOrder
        animatedImage.setzOrder(3);

        // Add animated image to the canvas
        canvas.addShape(animatedImage);
        canvas.revalidate();
        canvas.repaint();
    }
}
