package my_game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import base.Game;
import base.GameCanvas;
import shapes.Shape;

import java.io.File;
import java.io.IOException;

import ui_elements.ScreenPoint;

public class Arrow extends Shape {
    private String id;
    private BufferedImage arrowImage;
    private double angle; // Angle to target location (degrees)
    private ScreenPoint currentLocation;
    private ScreenPoint targetLocation;
    private Character target;
    private int speed = 30; // Pixels per frame
    private int damage;
    private boolean active;
    private boolean hitTarget = false;

    // Image path (using a single image that rotates according to target location)
    private final String imagePath = "resources/objects/archer/arrow.png";

    // Create timeout to the arrow in case of a miss
    private long creationTime = System.currentTimeMillis();
    private static final long MAX_LIFETIME = 3000;

    public Arrow(String id, ScreenPoint startLocation, Character target, int damage) {
        super(id);
        this.id = id;
        this.currentLocation = startLocation;
        this.targetLocation = target.getCenterLocation();
        this.target = target;
        this.damage = damage;
        this.active = true;
        
        setDraggable(false);

        // Calculate angle to target
        this.angle = Math.toDegrees(Math.atan2(targetLocation.y - currentLocation.y, 
                                               targetLocation.x - currentLocation.x));

        // Load image of arrow
        try {
            this.arrowImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load arrow image: " + imagePath);
        }        
    }

    public String getId() {
        return id;
    }

    public void addToCanvas(){
        GameCanvas canvas = Game.UI().canvas();
        setzOrder(3);
        canvas.addShape(this);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(id);
        canvas.revalidate();
        canvas.repaint();
    }

    public void gameStep(){
        if (!active) return;

        // Calculate movement in x and y directions
        double radians = Math.toRadians(angle);
        int dx = (int) (speed * Math.cos(radians));
        int dy = (int) (speed * Math.sin(radians));
        move(dx, dy);
        
        // Check if arrow hits target
        if (Math.abs(currentLocation.x - targetLocation.x) < speed && 
            Math.abs(currentLocation.y - targetLocation.y) < speed) {
            setHitTarget();
            target.reduceHealth(damage);
            deactivate();
            return;
        }

        // Check if the arrow's lifetime has exceeded the maximum
        if (System.currentTimeMillis() - creationTime > MAX_LIFETIME) {
            deactivate();
            return;
        }
    }

    private void deactivate() {
        active = false;
        removeFromCanvas();
    }

    public boolean isActive() {
        return active;
    }

    public void setHitTarget() {
        hitTarget = true;
    }

    public boolean getHitTarget() {
        return hitTarget;
    }

    @Override
    public void draw(Graphics2D g) {
        if (!active) return; // Do not draw if inactive

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(currentLocation.x, currentLocation.y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawImage(arrowImage, -arrowImage.getWidth() / 2, -arrowImage.getHeight() / 2, null);
        g2d.dispose();
    }

    @Override
    public void move(int dx, int dy) {
        this.currentLocation.x += dx;
        this.currentLocation.y += dy;
    }

    @Override
    public void moveToLocation(int x, int y) {
        this.currentLocation = new ScreenPoint(x, y);
    }

    @Override
    public boolean isInArea(int x, int y) {
        return (x >= currentLocation.x - arrowImage.getWidth() / 2 && x <= currentLocation.x + arrowImage.getWidth() / 2 &&
                y >= currentLocation.y - arrowImage.getHeight() / 2 && y <= currentLocation.y + arrowImage.getHeight() / 2);
    }
}
