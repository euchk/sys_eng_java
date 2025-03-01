package my_shapes;

import java.awt.Color;
import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import shapes.Image;
import shapes.Rectangle;
import shapes.Text;
import ui_elements.ScreenPoint;

public abstract class ShapeButton implements ShapeListener {
    protected String id;
    protected ScreenPoint position;
    protected Image image;
    protected Text text;
    protected Rectangle highlightCircle;

    /*
    This abstract class hold all common logic for shape buttons (rectangles)
    An image and a text linked to the button
    ShapeListener logic
    Highlight rectangle when hovering over the button
    */
    public ShapeButton(String id, int posX, int posY, String imageSrc, String buttonText) {
        this.id = id;
        this.position = new ScreenPoint(posX, posY);
        
        // Create the button image.
        image = new Image(id, imageSrc, 50, 50, posX, posY);
        image.setzOrder(3);
        image.setShapeListener(this);
        image.setDraggable(false);
        
        // Create the text label.
        text = new Text(id + "_text", buttonText, posX - image.getWeight() / 2 - 10, posY + image.getHeight() + 15);
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

    protected void showHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        highlightCircle = new Rectangle(getId() + "_buildHighlight", getPosition().x - 2, getPosition().y - 2, 54, 54);
        highlightCircle.setIsFilled(true);
        highlightCircle.setFillColor(new java.awt.Color(240, 240, 160, 80));
        highlightCircle.setColor(new java.awt.Color(240, 240, 160, 80));
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

    // Abstract method for subclass to implement
    protected abstract void onClick();

    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        onClick();
        hideHighlight();
        removeFromCanvas();
    }
    
    @Override public void shapeMoved(String shapeID, int dx, int dy) { }
    @Override public void shapeStartDrag(String shapeID) { }
    @Override public void shapeEndDrag(String shapeID) { }
    @Override public void shapeRightClicked(String shapeID, int x, int y) { }
    
    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        showHighlight();
    }
    
    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        hideHighlight();
    }
}
