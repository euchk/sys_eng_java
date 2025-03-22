package shapes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Test panel for demonstrating AnimatedImage instances
 */
public class AnimatedImageTest extends JPanel implements ActionListener {

    private final AnimatedImage[] animatedSprites;
    private final Timer timer;

    public AnimatedImageTest() {

        // Sprite parameters
        String spritePath = "resources/sprites/sprite_sheet1.png";
        int frameWidth = 128;
        int frameHeight = 128;
        int totalRows = 10;
        int totalCols = 10;

        // Create 10 animated images, one for each row in the sprite
        animatedSprites = new AnimatedImage[10]; 

        // === Sprite 1: first row (4 frames) ===
        animatedSprites[0] = new AnimatedImage("attack1", frameWidth, frameHeight, false);
        animatedSprites[0].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[0].setAnimationRow(0, 4);
        animatedSprites[0].moveToLocation(0, 0);

        // === Sprite 2: second row (3 frames) ===
        animatedSprites[1] = new AnimatedImage("attack2", frameWidth, frameHeight, false);
        animatedSprites[1].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[1].setAnimationRow(1, 3);
        animatedSprites[1].moveToLocation(0, 100);

        // === Sprite 3: third row (4 frames) ===
        animatedSprites[2] = new AnimatedImage("attack3", frameWidth, frameHeight, false);
        animatedSprites[2].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[2].setAnimationRow(2, 4);
        animatedSprites[2].moveToLocation(0, 200);

        // === Sprite 4: fourth row (3 frames) ===
        animatedSprites[3] = new AnimatedImage("dead", frameWidth, frameHeight, false);
        animatedSprites[3].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[3].setAnimationRow(3, 3);
        animatedSprites[3].moveToLocation(0, 300);

        // === Sprite 5: fifth row (3 frames) ===
        animatedSprites[4] = new AnimatedImage("hurt", frameWidth, frameHeight, false);
        animatedSprites[4].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[4].setAnimationRow(4, 3);
        animatedSprites[4].moveToLocation(0, 400);

        // === Sprite 6: sixth row (6 frames), mirrored ===
        animatedSprites[5] = new AnimatedImage("idle", frameWidth, frameHeight, true);
        animatedSprites[5].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[5].setAnimationRow(5, 6);
        animatedSprites[5].moveToLocation(200, 0);

        // === Sprite 7: seventh row (10 frames), mirrored ===
        animatedSprites[6] = new AnimatedImage("jump", frameWidth, frameHeight, true);
        animatedSprites[6].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[6].setAnimationRow(6, 10);
        animatedSprites[6].moveToLocation(200, 100);

        // === Sprite 8: eighth row (8 frames), mirrored ===
        animatedSprites[7] = new AnimatedImage("run", frameWidth, frameHeight, true);
        animatedSprites[7].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[7].setAnimationRow(7, 8);
        animatedSprites[7].moveToLocation(200, 200);

        // === Sprite 9: ninth row (2 frames), mirrored ===
        animatedSprites[8] = new AnimatedImage("shield", frameWidth, frameHeight, true);
        animatedSprites[8].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[8].setAnimationRow(8, 2);
        animatedSprites[8].moveToLocation(200, 300);

        // === Sprite 10: tenth row (8 frames), mirrored ===
        animatedSprites[9] = new AnimatedImage("walk", frameWidth, frameHeight, true);
        animatedSprites[9].setSpriteSheet(spritePath, totalRows, totalCols);
        animatedSprites[9].setAnimationRow(9, 8);
        animatedSprites[9].moveToLocation(200, 400);

        // Timer to update animation every 100ms (10 FPS)
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (AnimatedImage sprite : animatedSprites) {
            sprite.draw((Graphics2D) g);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (AnimatedImage sprite : animatedSprites) {
            sprite.nextFrame();
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("AnimatedImage Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 600);
        frame.setContentPane(new AnimatedImageTest());
        frame.setVisible(true);
    }
}
