package shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class AnimatedImage extends Shape {
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
    private int frameWidth, frameHeight;
    private int totalFrames;
    private int currentFrame = 0;
    private AnimationRow currentRow = AnimationRow.U_IDLE;

    private int posX, posY;

    public AnimatedImage(String id, String spriteSheetPath, int frameWidth, 
                        int frameHeight, int totalFrames) {
        super(id);
        try {
            this.spriteSheet = ImageIO.read(new File(spriteSheetPath));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load sprite sheet: " + spriteSheetPath);
        }
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.totalFrames = totalFrames;
        this.posX = 0;
        this.posY = 0;
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

    public void setRow(AnimationRow row) {
        this.currentRow = row;
        this.currentFrame = 0; // Reset animation to first frame
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
        int srcY = currentRow.getRowIndex() * frameHeight;

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
