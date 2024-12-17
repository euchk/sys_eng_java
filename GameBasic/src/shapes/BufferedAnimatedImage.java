package shapes;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class BufferedAnimatedImage extends JComponent {
    private BufferedImage spriteSheet;  // Spritesheet containing frames
    private int frameCount;             // Total number of frames
    private int currentFrame;           // Current frame index
    private int frameWidth;             // Width of each frame
    private int frameHeight;            // Height of each frame
    private int posX, posY;             // Position on screen

    public BufferedAnimatedImage(String src, int frameWidth, int frameHeight, int posX, int posY, int frameCount) {
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.posX = posX;
        this.posY = posY;
        this.frameCount = frameCount;
        this.currentFrame = 0;

        try {
            spriteSheet = ImageIO.read(new File(src)); // Load spritesheet image
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBounds(posX, posY, frameWidth, frameHeight); // Set the component bounds
    }

    /**
     * Advance to the next frame of animation.
     */
    public void nextFrame() {
        currentFrame = (currentFrame + 1) % frameCount; // Loop through frames
        repaint();
    }

    /**
     * Manually set a specific frame.
     */
    public void setFrame(int frameIndex) {
        if (frameIndex >= 0 && frameIndex < frameCount) {
            this.currentFrame = frameIndex;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (spriteSheet != null) {
            int sourceX = currentFrame * frameWidth; // Calculate X-coordinate for the frame
            g.drawImage(spriteSheet, 0, 0, frameWidth, frameHeight, // Destination size
                        sourceX, 0, sourceX + frameWidth, frameHeight, // Source frame area
                        this);
        }
    }


    /**
     * Move the animated image to a new position.
     */
    public void moveTo(int x, int y) {
        this.posX = x;
        this.posY = y;
        setLocation(x, y);
    }
}
