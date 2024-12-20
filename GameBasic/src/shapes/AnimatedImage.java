package shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AnimatedImage extends Shape {
    // Enum for directions and actions

    
    public enum AnimationRow {
        U_IDLE(0), D_IDLE(1), L_IDLE(2), R_IDLE(3), 
        U_ATTACK(4), D_ATTACK(5), L_ATTACK(6), R_ATTACK(7);

        private final int rowIndex;

        AnimationRow(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getRowIndex() {
            return rowIndex;
        }
    }

    private BufferedImage spriteSheet;
    private String[] spriteSheetPaths;
    private int frameWidth, frameHeight;
    private int totalFrames;
    private int currentFrame = 0;
    private AnimationRow animationRow;
    private int posX, posY;

    // public AnimatedImage(String id, String spriteSheetPath, int frameWidth, 
    public AnimatedImage(String id, String[] spriteSheetPaths, int frameWidth, 
                        int frameHeight, int totalFrames, AnimationRow animationRow) {
        super(id);
        
        this.spriteSheetPaths = spriteSheetPaths;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.posX = 0;
        this.posY = 0;
        this.animationRow = animationRow;
        loadSpriteSheet();    
        
    }

    private void loadSpriteSheet() {
        try {
            // Load the first sprite sheet as default
            spriteSheet = ImageIO.read(new File(spriteSheetPaths[animationRow.getRowIndex()]));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load sprite sheet: " + spriteSheetPaths[0]);
        }
    }

    public void setAnimationRow(AnimationRow animationRow) {
        if (this.animationRow == animationRow) return;
            this.animationRow = animationRow;
            this.currentFrame = 0; // Reset frame on sprite sheet change
            loadSpriteSheet();
        }

    public void setLocation(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void nextFrame() {
        currentFrame++;
        if (currentFrame >= totalFrames) {
            currentFrame = 0; // Loop animation
        }
    }

    @Override
    public void draw(Graphics2D g) {
        int srcX = currentFrame * frameWidth;
        int srcY = 0; // Using 1-d sprites

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
