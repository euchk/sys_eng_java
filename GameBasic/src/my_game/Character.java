package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;

public abstract class Character {

    // Enum for directions or actions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public enum Action {
        IDLE, ATTACK
    }

    private String id;
    private ScreenPoint location;
    private AnimatedImage animatedImage;
    
    protected Direction direction;
    protected Action action;
    
    public Character(String id, ScreenPoint startLocation, 
                    int frameWidth, int frameHeight, 
                    Direction direction, Action action) {
        this.id = id;
        this.location = startLocation;
        this.action = action;
        this.direction = direction;
        this.animatedImage = new AnimatedImage(id, frameWidth, frameHeight);
        this.animatedImage.moveToLocation(startLocation.x, startLocation.y);
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
        animatedImage.moveToLocation(x, y);
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        updateAnimation();
    }

    public void setAction(Action action){
        this.action = action;
        updateAnimation();
    }

    public void setAnimation(String spritePath, int totalFrames) {
        animatedImage.setSpriteSheet(spritePath, totalFrames);
    }

    public void periodicUpdate() {
        animatedImage.nextFrame();
    }
    
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        animatedImage.setzOrder(3);
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

    // Subclasses must implement mappings
    public abstract void updateAnimation();
    
    
    // public void move(int dx, int dy) {
    //     location.x += dx;
    //     location.y += dy;
    //     animatedImage.move(dx, dy);

    //     // Update direction based on movement
    //     if (dx > 0) setDirection(Direction.RIGHT);
    //     else if (dx < 0) setDirection(Direction.LEFT);
    //     else if (dy > 0) setDirection(Direction.DOWN);
    //     else if (dy < 0) setDirection(Direction.UP);
    // }
    
}