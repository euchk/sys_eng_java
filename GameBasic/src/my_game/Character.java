package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;

public class Character {
    private String id;
    private ScreenPoint location;
    private ScreenPoint originalLocation;
    private AnimatedImage animatedImage;
    private boolean isMoving;

    // Enum for directions or actions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection;
    
    public Character(ScreenPoint startLocation, String id, String spriteSheetPath, 
                    int frameWidth, int frameHeight, int totalFrames) {
        this.id = id;
        this.location = startLocation;
        this.originalLocation = startLocation;
        this.currentDirection = Direction.DOWN; // Default facing direction

        // Initialize AnimatedImage instance
        this.animatedImage = new AnimatedImage(id, spriteSheetPath, frameWidth, frameHeight, totalFrames);
        this.animatedImage.setLocation(location.x, location.y);
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
        animatedImage.setLocation(x, y);
    }

    public void setDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public void move(int dx, int dy) {
        if (!isMoving) return;

        location.x += dx;
        location.y += dy;
        animatedImage.move(dx, dy);

        // Update direction based on movement
        if (dx > 0) setDirection(Direction.RIGHT);
        else if (dx < 0) setDirection(Direction.LEFT);
        else if (dy > 0) setDirection(Direction.DOWN);
        else if (dy < 0) setDirection(Direction.UP);
    }

    // Method for periodic calls
    public void periodicUpdate() {
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

    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(animatedImage.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    public void updatePositionProportionately(int originalWidth, int originalHeight) {
        // GameCanvas canvas = Game.UI().canvas();
    
        // // Calculate scale factors
        // double scaleX = (double) canvas.getCanvasWidth() / originalWidth;
        // double scaleY = (double) canvas.getCanvasHeight() / originalHeight;
    
        // // Update character's position proportionately
        // location.x = (int) (originalLocation.x * scaleX);
        // location.y = (int) (originalLocation.y * scaleY);
    
        // // Move shape to new location
        // animatedImage.setLocation(location.x, location.y);
        ;
    }
    
}
