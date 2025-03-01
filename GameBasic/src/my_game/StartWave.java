package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import shapes.Image;
import shapes.Rectangle;
import ui_elements.ScreenPoint;

public class StartWave implements ShapeListener {
    
    private String id;
    private ScreenPoint position;
    private Image image;
    private Rectangle highlightCircle;

    private final String imageSrc = "resources/objects/buttons/startWave.png";    
    
    private GameControl gameControl; // reference to GameControl

    public StartWave(String id, int posX, int posY, GameControl gameControl) {
        this.id = id;
        this.position = new ScreenPoint(posX, posY);
        this.gameControl = gameControl;
        image = new Image(id, imageSrc, 50, 50, posX, posY);
        image.setzOrder(3);
        image.setShapeListener(this);
        image.setDraggable(false);
    }

    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(image);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(image.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    private void showHighlight() {
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

    private void hideHighlight() {
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

    @Override
    public void shapeMoved(String shapeID, int dx, int dy) { }

    @Override
    public void shapeStartDrag(String shapeID) { }

    @Override
    public void shapeEndDrag(String shapeID) { }

    // Instead of spawning invaders directly, we signal GameControl to start the wave
    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        gameControl.startWaveClicked();
        hideHighlight();
        removeFromCanvas();
    }
    
    @Override
    public void shapeRightClicked(String shapeID, int x, int y) { }
    
    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        showHighlight();
    }
    
    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        hideHighlight();
    }
}
