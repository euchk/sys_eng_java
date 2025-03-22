package shapes;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;

/**
 * Represents an animated sprite using a 2D sprite sheet.
 *
 * This class allows animating a sequence of frames from a single row of the sprite sheet.
 * The sprite sheet is expected to be organized as a uniform grid of frames, with each frame
 * having the same width and height.
 * 
 *
 * Only one row can be animated at a time. This is useful when each row in the sheet represents
 * a different animation state, such as idle, walk, jump, etc.
 *
 * It is recommended to use a sprite with a transparent background.
 *
 * Key Concepts:
 * - rows: The number of horizontal rows in the sprite sheet grid
 * - columns: The number of vertical columns in each row
 * - frameWidth, frameHeight: The size (in pixels) of each frame in the grid
 * - row: The index (0-based) of the row being animated
 * - frameCount: How many frames in that row should be used for animation
 * - currentFrame: The index (0-based) of the current frame in the animation loop
 *
 * Example:
 * 
 * Sprite Sheet Layout Example (3 rows × 10 columns):
 *
 *   Row 0: [0][1][2][3][4][5][6][7][8][9]       → idle animation (10 frames)
 *   Row 1: [10][11][12][13][14]                 → walk animation (5 frames)
 *   Row 2: [20][21][22][23]                     → attack animation (4 frames)
 * 
 * To animate the the first row use:
 *
 *   animatedSprite.setSpriteSheet("sprite.png", 3, 10);
 *   animatedSprite.setAnimationRow(0, 10);
 *
 * To flip the sprite horizontally use:
 *   animatedSprite.setIsMirrored(true);
 */


public class AnimatedImage extends Shape {
    private BufferedImage spriteSheet;

    private int frameWidth, frameHeight;
    private int posX, posY;
    private boolean isMirrored;

    private int rows, columns;     // Full layout of the sprite sheet
    private int row = 0;           // The row to animate (0-indexed)
    private int frameCount = 1;    // Number of frames in the selected row
    private int currentFrame = 0;

    public AnimatedImage(String id, int frameWidth, int frameHeight, boolean isMirrored) {
        super(id);
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.isMirrored = isMirrored;
        this.setDraggable(false);
    }

    /**
     * Loads the sprite sheet.
     *
     * @param spritePath Path to the sprite sheet image
     * @param rows       Number of rows in the sprite sheet
     * @param columns    Number of columns in the sprite sheet
     */
    public void setSpriteSheet(String spritePath, int rows, int columns) {
        try {
            this.spriteSheet = ImageIO.read(new File(spritePath));
            this.rows = rows;
            this.columns = columns;
            this.currentFrame = 0;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load sprite sheet: " + spritePath);
        }
    }

    /**
     * Sets which row to animate and how many frames are in that row.
     *
     * @param row        Row index to animate (0-based)
     * @param frameCount Number of frames in that row
     */
    public void setAnimationRow(int row, int frameCount) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException(
                "Invalid row: " + row + ". Sprite sheet has " + rows + " rows."
            );
        }
    
        if (frameCount <= 0 || frameCount > columns) {
            throw new IllegalArgumentException(
                "Invalid frameCount: " + frameCount + ". Must be > 0 and ≤ " + columns + " (columns)."
            );
        }

        this.row = row;
        this.frameCount = frameCount;
        this.currentFrame = 0;
    }

    public void nextFrame() {
        if (frameCount > 0) {
            currentFrame = (currentFrame + 1) % frameCount; // Loop animation
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (spriteSheet == null) return;

        int srcX = currentFrame * frameWidth;
        int srcY = row * frameHeight;

        if (!isMirrored) { // Draw the current frame
            g.drawImage(spriteSheet,
                    posX, posY, posX + frameWidth, posY + frameHeight,
                    srcX, srcY, srcX + frameWidth, srcY + frameHeight,
                    null);
            
        } else { // Draw and flip horizonatly
            g.drawImage(spriteSheet,
                    posX + frameWidth, posY, posX, posY + frameHeight,
                    srcX, srcY, srcX + frameWidth, srcY + frameHeight,
                    null);
        }
    }

    public void setIsMirrored(boolean isMirrored) {
        this.isMirrored = isMirrored;
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
        return (x >= posX && x <= posX + frameWidth) &&
               (y >= posY && y <= posY + frameHeight);
    }
}
