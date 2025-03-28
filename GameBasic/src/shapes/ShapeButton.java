package shapes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import ui_elements.ScreenPoint;

/**
 * ShapeButton is an abstract base class for creating interactive UI buttons that are
 * based on rectangle images and can be added to the canvas (not to the dashboard).
 *
 * Features:
 * - Shows a visual image with optional text below it.
 * - Responds to mouse clicks and hover events.
 * - Supports visual glow effect on hover.
 * - Supports disabling/enabling the button (disabling shows grayscale image).
 * - Subclasses implement the onClick() method to define behavior when clicked.
 * - Optionally disables itself on click, configurable via setDisableOnClick(true/false).
 * - Text can be updated dynamically using setText().
 *
 * Usage:
 * 1. Extend this class and implement the onClick() method.
 * 2. Use addToCanvas() after construction to make the button visible.
 * 3. Call setText() to configure or update the button label.
 * 4. Use disableButton() and enableButton() to control interactivity.
 * 5. Use setDisableOnClick(false) if you want to manage disabling manually.
 * 6. Use setGlowEnabled(false) if you don't want the glow when hovering effect.
 *
 * Example:
 * public class MyButton extends ShapeButton {
 *     public MyButton(int x, int y) {
 *         super("my_button", 50, 50, x, y, "res/image.png");
 *         setText("Click Me");
 *         setDisableOnClick(true);
 *     }
 *     protected void onClick() {
 *         System.out.println("Button clicked!");
 *     }
 * }
 */

public abstract class ShapeButton implements ShapeListener {
    protected String id;
    protected ScreenPoint position;
    protected int height, width;
    protected Image image;
    protected Text text;
    protected Rectangle glowEffect;
    protected boolean isDisabled = false;
    protected boolean disableOnClick = true;
    protected boolean glowEnabled = true;

    protected String imagePath;
    protected BufferedImage originalImage;
    protected BufferedImage grayscaleImage;

    public ShapeButton(String id, int width, int height, int posX, int posY, String imagePath) {
        this.id = id;
        this.position = new ScreenPoint(posX, posY);
        this.width = width;
        this.height = height;
        this.imagePath = imagePath;

        try {
            originalImage = ImageIO.read(new File(imagePath));
            grayscaleImage = toGrayscale(originalImage);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load or process image: " + imagePath, e);
        }

        image = new Image(id, imagePath, width, height, posX, posY);
        image.setzOrder(10);
        image.setShapeListener(this);
        image.setDraggable(false);

        text = new Text(id + "_text", "", posX - 1, posY + height + 15);
        text.setFontSize(13);
        text.setColor(Color.WHITE);
        text.setzOrder(10);
    }

    public void setText(String buttonText) {
        text.setText(buttonText);
    }

    public void setGlowEnabled(boolean enabled) {
        this.glowEnabled = enabled;
    }

    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(image);
        canvas.addShape(text);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(image.getId());
        canvas.deleteShape(text.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    public void disableButton() {
        isDisabled = true;
        image.setBufferedImage(grayscaleImage);
    }

    public void enableButton() {
        isDisabled = false;
        image.setBufferedImage(originalImage);
    }

    protected void showGlow() {
        if (isDisabled || !glowEnabled) return;

        GameCanvas canvas = Game.UI().canvas();
        int extraWidth = (int) (width * 0.1);
        int extraHeight = (int) (height * 0.1);

        int glowX = position.x - (extraWidth / 2);
        int glowY = position.y - (extraHeight / 2);
        int glowWidth = width + extraWidth;
        int glowHeight = height + extraHeight;

        glowEffect = new Rectangle(id + "_glow", glowX, glowY, glowWidth, glowHeight);
        glowEffect.setIsFilled(true);
        glowEffect.setFillColor(new Color(0, 240, 160, 80));
        glowEffect.setColor(new Color(0, 240, 160, 80));
        glowEffect.setWeight(0);
        glowEffect.setzOrder(2);
        canvas.addShape(glowEffect);
        canvas.revalidate();
        canvas.repaint();
    }

    protected void hideGlow() {
        GameCanvas canvas = Game.UI().canvas();
        if (glowEffect != null) {
            canvas.deleteShape(glowEffect.getId());
            glowEffect = null;
            canvas.revalidate();
            canvas.repaint();
        }
    }

    public void setDisableOnClick(boolean disable) {
        this.disableOnClick = disable;
    }

    public String getId() {
        return id;
    }

    public ScreenPoint getPosition() {
        return position;
    }

    public boolean isDisabled() {
        return isDisabled;
    }

    protected abstract void onClick();

    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        if (isDisabled) return;
        hideGlow();
        onClick();
        if (disableOnClick) disableButton();
    }

    @Override public void shapeMoved(String shapeID, int dx, int dy) {}
    @Override public void shapeStartDrag(String shapeID) {}
    @Override public void shapeEndDrag(String shapeID) {}
    @Override public void shapeRightClicked(String shapeID, int x, int y) {}

    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        if (!isDisabled) showGlow();
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        if (!isDisabled) hideGlow();
    }

    private BufferedImage toGrayscale(BufferedImage src) {
        BufferedImage gray = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                Color color = new Color(src.getRGB(x, y), true);
                int avg = (color.getRed() + color.getGreen() + color.getBlue()) / 3;
                Color grayColor = new Color(avg, avg, avg, color.getAlpha());
                gray.setRGB(x, y, grayColor.getRGB());
            }
        }
        return gray;
    }
}
