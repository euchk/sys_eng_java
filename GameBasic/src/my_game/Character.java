package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import shapes.AnimatedImage;
import shapes.HealthBar;


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
    private int frameWidth, frameHeight;
    protected AnimatedImage animatedImage;
    protected Direction direction;
    protected Action action;
    private boolean isMirrored;
    private HealthBar healthBar;
    
    public Character(String id, ScreenPoint startLocation, 
                    int frameWidth, int frameHeight, 
                    Direction direction, Action action,
                    int maxHealth) {
        this.id = id;
        this.location = startLocation;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.action = action;
        this.direction = direction;
        setIsMirrored();
        // Initialize AnimatedImage
        this.animatedImage = new AnimatedImage(id, frameWidth, frameHeight, isMirrored);
        this.animatedImage.moveToLocation(startLocation.x, startLocation.y);
        // Initialize health bar
        this.healthBar = new HealthBar(id + "_health", startLocation.x, startLocation.y - 10, frameWidth, 5, maxHealth);
    }

    public String getId() {
        return id;
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public int getWidth() {
        return frameWidth;
    }

    public int getHeight() {
        return frameHeight;
    }

    public void setLocation(int x, int y) {
        this.location.x = x;
        this.location.y = y;
        animatedImage.moveToLocation(x, y); // Move the image along with the character
        healthBar.moveToLocation(x, y - 10); // Move the health bar along with the character
    }

    public int getHealth() {
        return healthBar.getCurrentHealth();
    }

    public int getMaxHealth(){
        return healthBar.getMaxHealth();
    }

    public void reduceHealth(int damage) {
        healthBar.reduceHealth(damage);
    }

    public void setHealth(int health){
        healthBar.setHealth(health);
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        setIsMirrored(); // Updating isMirrored for character
        animatedImage.setIsMirrored(isMirrored); // Updating isMirrored for animatedImage
        updateAnimation();
    }

    public void setAction(Action action){
        this.action = action;
        updateAnimation();
    }

    public void setIsMirrored(){
        this.isMirrored = (direction == Direction.RIGHT); // All side sprites are left
    }

    public void setAnimation(String spritePath, int totalFrames) {
        animatedImage.setSpriteSheet(spritePath, totalFrames);
    }

    public void nextFrame(){
        animatedImage.nextFrame();
    }

    public void move(int dx, int dy) {
        location.x += dx;
        location.y += dy;        
        animatedImage.move(dx, dy);
        healthBar.move(dx, dy);

        // Update direction based on movement
        if (dx > 0) setDirection(Direction.RIGHT);
        else if (dx < 0) setDirection(Direction.LEFT);
        else if (dy > 0) setDirection(Direction.DOWN);
        else if (dy < 0) setDirection(Direction.UP);
    }
    
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        animatedImage.setzOrder(3);
        canvas.addShape(animatedImage);
        healthBar.setzOrder(1);
        canvas.addShape(healthBar);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(animatedImage.getId());
        canvas.deleteShape(healthBar.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    // Subclasses must implement mappings
    public abstract void periodicUpdate();
    public abstract void updateAnimation();
    
}
