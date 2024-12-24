package my_game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import shapes.Shape;

import java.io.File;
import java.io.IOException;

import ui_elements.ScreenPoint;

public class Arrow extends Shape {
    private BufferedImage arrowImage;
    private double angle; // Angle in degrees
    private ScreenPoint position; // Current position
    private ScreenPoint target;   // Target position
    private int speed = 30; // Pixels per frame
    private boolean active;
    private boolean hitTarget = false;

    private final String imagePath = "resources/objects/archer/arrow.png";

    public Arrow(String id, ScreenPoint archerPosition, ScreenPoint target) {
        super(id);
        this.target = target;
        this.active = true;

        try {
            this.arrowImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load arrow image: " + imagePath);
        }

        // Set initial position based on the archer's position and bow offset
        this.position = new ScreenPoint(archerPosition.x, archerPosition.y);

        // Calculate angle to target
        this.angle = Math.toDegrees(Math.atan2(target.y - position.y, target.x - position.x));
    }

    @Override
    public void draw(Graphics2D g) {
        if (!active) return; // Do not draw if inactive

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(position.x, position.y);
        g2d.rotate(Math.toRadians(angle));
        g2d.drawImage(arrowImage, -arrowImage.getWidth() / 2, -arrowImage.getHeight() / 2, null);
        g2d.dispose();
    }

    private long creationTime = System.currentTimeMillis(); // Record the arrow's creation time
    private static final long MAX_LIFETIME = 3000;

    public void move() {
        if (!active) return;

        // Check if the arrow's lifetime has exceeded the maximum
        if (System.currentTimeMillis() - creationTime > MAX_LIFETIME) {
            deactivate();
            return;
        }
        
        // Calculate movement in x and y directions
        double radians = Math.toRadians(angle);
        position.x += (int) (speed * Math.cos(radians));
        position.y += (int) (speed * Math.sin(radians));

        // Stop movement if the arrow reaches the target
        if (Math.abs(position.x - target.x) < speed && Math.abs(position.y - target.y) < speed) {
            deactivate();
        }
    }

    private void deactivate() {
        active = false;
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
    public void move(int dx, int dy) {
        this.position.x += dx;
        this.position.y += dy;
    }

    @Override
    public void moveToLocation(int x, int y) {
        this.position = new ScreenPoint(x, y);
    }

    @Override
    public boolean isInArea(int x, int y) {
        return (x >= position.x - arrowImage.getWidth() / 2 && x <= position.x + arrowImage.getWidth() / 2 &&
                y >= position.y - arrowImage.getHeight() / 2 && y <= position.y + arrowImage.getHeight() / 2);
    }
}
