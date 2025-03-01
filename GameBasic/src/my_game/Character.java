package my_game;

import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import my_base.MyContent;
import my_shapes.AnimatedImage;
import shapes.HealthBar;


public abstract class Character extends GameObject {

    // Enum for directions or actions
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public enum Action {
        IDLE, ATTACK
    }

    private int frameWidth, frameHeight;
    protected AnimatedImage animatedImage;
    protected Direction direction;
    protected Action action;
    private boolean isMirrored;
    private HealthBar healthBar;
    private boolean isKilled = false;

    protected int speed;
    protected int coins; // Amount of coins to be received if killed/Sold

    protected MyContent content = (MyContent) Game.Content();

    
    public Character(String id, ScreenPoint startLocation, 
                    int frameWidth, int frameHeight, 
                    Direction direction, Action action) {
        super(id, startLocation);               
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.action = action;
        this.direction = direction;
        setIsMirrored();
        
        // Initialize AnimatedImage
        this.animatedImage = new AnimatedImage(id + "_img", frameWidth, frameHeight, isMirrored);
        this.animatedImage.moveToLocation(startLocation.x, startLocation.y);
        // Initialize health bar
        this.healthBar = new HealthBar(id + "_health", startLocation.x, startLocation.y - 10, frameWidth, 5);
    }

    public int getWidth() {
        return frameWidth;
    }

    public int getHeight() {
        return frameHeight;
    }

    public int getSpeed() {
        return speed;
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getCoins() {
        return coins;
    }

    protected void setCoins(int coins) {
        this.coins = coins;
    }
    
    public ScreenPoint getCenterLocation(){
        return new ScreenPoint(location.x + getWidth() / 2, location.y + getHeight() / 2);
    }

    public void setLocation(int x, int y) {
        this.location.x = x;
        this.location.y = y;
        animatedImage.moveToLocation(x, y); // Move the image along with the character
        healthBar.moveToLocation(x, y - 10); // Move the health bar along with the character
    }

    public void setShowHealthBar(boolean showHealthBar) {
        this.healthBar.setIsVisible(showHealthBar);
    }
    
    public int getHealth() {
        return healthBar.getCurrentHealth();
    }

    public int getMaxHealth(){
        return healthBar.getMaxHealth();
    }

    public void reduceHealth(int damage) {
        healthBar.reduceHealth(damage);
        if (getHealth() <= 0) {
            setIsKilled();
            deactivate();
        }
    }

    public void setMaxHealth(int maxHealth){
        healthBar.setMaxHealth(maxHealth);
    }

    protected void setIsKilled() {
        this.isKilled = true;
    }
    
    public boolean getIsKilled() {
        return isKilled;
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        setIsMirrored(); // Updating isMirrored for character
        animatedImage.setIsMirrored(isMirrored); // Updating isMirrored for animatedImage
        updateAnimation();
    }

    public Direction getDirection() {
        return direction;
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
    }
    
    @Override
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        animatedImage.setzOrder(10);
        canvas.addShape(animatedImage);
        healthBar.addToCanvas();
        canvas.revalidate();
        canvas.repaint();
    }

    @Override
    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(animatedImage.getId());
        healthBar.removeFromCanvas();
        canvas.revalidate();
        canvas.repaint();
    }

    // Subclasses must implement mappings
    public abstract void gameStep();
    public abstract void updateAnimation();
    
}
