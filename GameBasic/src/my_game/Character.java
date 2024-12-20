package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;
import shapes.AnimatedImage.AnimationRow;

public class Character {
    private String id;
    private ScreenPoint location;
    private ScreenPoint originalLocation;
    private AnimatedImage animatedImage;
    private Direction direction;
    private boolean isIdle = true;
    private boolean isMoving;

    // Enum for directions or actions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    
    public Character(ScreenPoint startLocation, String id, String[] spriteSheetPaths, 
                    int frameWidth, int frameHeight, int totalFrames, 
                    Direction direction) {
        this.id = id;
        this.location = startLocation;
        this.originalLocation = startLocation;
        this.direction = direction;

        // Initialize AnimatedImage instance
        this.animatedImage = new AnimatedImage(id, spriteSheetPaths, frameWidth, 
                                            frameHeight, totalFrames, mapDirection(direction, isIdle));
        this.animatedImage.setLocation(location.x, location.y);
        this.animatedImage.setzOrder(3); // Default z-order
        this.animatedImage.setDraggable(true);
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
        this.direction = direction;
    }

    // Mapping direction from Character to AnimatedImage
    private AnimationRow mapDirection(Direction direction, boolean isIdle) {
        switch (direction) {
            case UP:
                return isIdle ? AnimationRow.U_IDLE : AnimationRow.U_ATTACK;
            case DOWN:
                return isIdle ? AnimationRow.D_IDLE : AnimationRow.D_ATTACK;
            case LEFT:
                return isIdle ? AnimationRow.L_IDLE : AnimationRow.L_ATTACK;
            case RIGHT:
                return isIdle ? AnimationRow.R_IDLE : AnimationRow.R_ATTACK;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
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
        animatedImage.setAnimationRow(mapDirection(direction, isIdle));
        animatedImage.nextFrame();
    }

    public void addToCanvas() {
        if (animatedImage == null) {
            System.err.println("AnimatedImage not initialized!");
            return;
        }
        
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
