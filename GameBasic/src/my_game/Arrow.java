package my_game;

import base.Game;
import base.GameCanvas;
import shapes.Image;

import ui_elements.ScreenPoint;

public class Arrow extends GameObject {
    private Image arrowImage;
    private double angle; // Angle to target location (degrees)
    private ScreenPoint targetLocation;
    private Character target;
    private int speed = 30; // Pixels per frame
    private int damage;
    private boolean hitTarget = false;

    // Image path (using a single image that rotates according to target location)
    private final String imagePath = "resources/objects/archer/arrow.png";

    // Create timeout to the arrow in case of a miss
    private long creationTime = System.currentTimeMillis();
    private static final long MAX_LIFETIME = 3000;

    public Arrow(String id, ScreenPoint startLocation, Character target, int damage) {
        super(id, startLocation);
        this.targetLocation = target.getCenterLocation();
        this.target = target;
        this.damage = damage;

        // Calculate angle to target
        this.angle = Math.toDegrees(Math.atan2(targetLocation.y - location.y, 
                                               targetLocation.x - location.x));

       // Create the arrow's graphical representation
       this.arrowImage = new Image(id, imagePath, 16, 16, startLocation.x, startLocation.y);
       this.arrowImage.setRotation((int) angle); // Rotate the image to point toward the target

    }

    public void setHitTarget() {
        hitTarget = true;
    }

    public boolean getHitTarget() {
        return hitTarget;
    }

    @Override
    public void addToCanvas(){
        GameCanvas canvas = Game.UI().canvas();
        arrowImage.setzOrder(3);
        canvas.addShape(arrowImage);
        canvas.revalidate();
        canvas.repaint();
    }

    @Override
    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(id);
        canvas.revalidate();
        canvas.repaint();
    }

    @Override
    public void gameStep(){
        if (!active) return;

        // Calculate movement in x and y directions
        double radians = Math.toRadians(angle);
        int dx = (int) (speed * Math.cos(radians));
        int dy = (int) (speed * Math.sin(radians));

        // Update position
        location.x += dx;
        location.y += dy;
        arrowImage.move(dx, dy);
        
        // Check if arrow hits target
        if (Math.abs(location.x - targetLocation.x) < speed && 
            Math.abs(location.y - targetLocation.y) < speed) {
            setHitTarget();
            // Game.audioPlayer().play("resources/audio/arrow_hit.wav", 1);
            target.reduceHealth(damage);
            deactivate();
            return;
        }

        // Check for timeout
        if (System.currentTimeMillis() - creationTime > MAX_LIFETIME) {
            deactivate();
            return;
        }
    }

}
