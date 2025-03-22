package shapes;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import ui_elements.ScreenPoint;

public abstract class ShapeButton implements ShapeListener {
    protected String id;
    protected ScreenPoint position;
    
    protected int height, width;

    protected Image image;
    protected Text text;
    protected Rectangle highlightCircle;

    protected boolean isDisabled = false;

    protected String imagePath;
    protected BufferedImage originalImage;
    protected BufferedImage grayscaleImage;

    public ShapeButton(String id, int width, int height, int posX, int posY, String imagePath, String buttonText) {
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

        text = new Text(id + "_text", buttonText, posX - 1, posY + height + 15);
        text.setFontSize(13);
        text.setColor(Color.WHITE);
        text.setzOrder(10);
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

    protected void showHighlight() {
        if (isDisabled) return;

        GameCanvas canvas = Game.UI().canvas();
    
        int extraWidth = (int) (width * 0.1);
        int extraHeight = (int) (height * 0.1);
    
        int highlightX = position.x - (extraWidth / 2);
        int highlightY = position.y - (extraHeight / 2);
        int highlightWidth = width + extraWidth;
        int highlightHeight = height + extraHeight;
    
        highlightCircle = new Rectangle(id + "_highlight", highlightX, highlightY, highlightWidth, highlightHeight);
        highlightCircle.setIsFilled(true);
        highlightCircle.setFillColor(new Color(240, 240, 160, 80));
        highlightCircle.setColor(new Color(240, 240, 160, 80));
        highlightCircle.setWeight(0);
        highlightCircle.setzOrder(2);
        canvas.addShape(highlightCircle);
        canvas.revalidate();
        canvas.repaint();
    }

    protected void hideHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        if (highlightCircle != null) {
            canvas.deleteShape(highlightCircle.getId());
            highlightCircle = null;
            canvas.revalidate();
            canvas.repaint();
        }
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
        hideHighlight();
        onClick();
        disableButton(); // Disabled when clicked. Override to change behaviour
    }

    @Override public void shapeMoved(String shapeID, int dx, int dy) {}
    @Override public void shapeStartDrag(String shapeID) {}
    @Override public void shapeEndDrag(String shapeID) {}
    @Override public void shapeRightClicked(String shapeID, int x, int y) {}

    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        if (!isDisabled) showHighlight();
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        if (!isDisabled) hideHighlight();
    }

    // Convert to grayscale
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
