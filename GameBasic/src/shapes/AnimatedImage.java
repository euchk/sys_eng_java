package shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AnimatedImage extends Shape {
    private BufferedImage spriteSheet;
    private String currentSpritePath;
    private int frameWidth, frameHeight;
    private int totalFrames;
    private int currentFrame = 0;
    private int posX, posY;

    public AnimatedImage(String id, int frameWidth, int frameHeight) {
        super(id);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        // Default settings
        this.posX = 0;
        this.posY = 0;        
    }

    public void setSpriteSheet(String spritePath, int totalFrames) {
        if (!spritePath.equals(currentSpritePath)) { // Avoid reloading the same sprite sheet
            try {
                this.spriteSheet = ImageIO.read(new File(spritePath));
                this.currentSpritePath = spritePath;
                this.totalFrames = totalFrames;
                this.currentFrame = 0; // Reset animation
                
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load sprite sheet: " + spritePath);
            }
        }
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void nextFrame() {
        if (totalFrames > 0) {
            currentFrame = (currentFrame + 1) % totalFrames; // Loop animation
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int srcX = currentFrame * frameWidth;
        int srcY = 0; // Using 1-d spritesheets

        g.drawImage(spriteSheet, posX, posY, posX + frameWidth, posY + frameHeight,
                srcX, srcY, srcX + frameWidth, srcY + frameHeight, null);
    }

    @Override
    public void move(int dx, int dy) {
        this.posX += dx;
        this.posY += dy;
    }

    @Override
    public void moveToLocation(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    @Override
    public boolean isInArea(int x, int y) {
        return (x >= posX && x <= posX + frameWidth) && (y >= posY && y <= posY + frameHeight);
    }
}
