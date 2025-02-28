package my_game;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import shapes.Image;
import shapes.Rectangle;
import ui_elements.ScreenPoint;

public class StartWave implements ShapeListener {
    
    private String id;
    private ScreenPoint position;
    private Image image;
    private Rectangle highlightCircle;

    private final String imageSrc = "resources/objects/buttons/startWave.png";    

    MyContent content = (MyContent) Game.Content();
 
    public StartWave(String id, int posX, int posY) {
        this.id = id;
        this.position = new ScreenPoint(posX, posY);
        image = new Image(id, imageSrc, 50, 50, posX, posY);
        image.setzOrder(3);
        image.setShapeListener(this);
        image.setDraggable(false);
    }

    // Start the wave when clicking
    public void startWave() {
        // spawnSlime(Paths.levelOnePath());
        spawnTroll(Paths.levelOnePath());
        spawnTroll(Paths.levelTwoPath());
        spawnTroll(Paths.levelOnePath());
        spawnTroll(Paths.levelTwoPath());
        spawnTroll(Paths.levelOnePath());
        spawnTroll(Paths.levelTwoPath());
        spawnTroll(Paths.levelOnePath());
        spawnTroll(Paths.levelTwoPath());
        // spawnBee(Paths.levelOnePath());
        // spawnWolf(Paths.levelOnePath());
        // spawnRat(Paths.levelOnePath());
        // spawnKnight(Paths.levelOnePath());
        // spawnWizard(Paths.levelOnePath());
    }

    public void spawnKnight(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Knight(invaderId, Direction.LEFT, Action.ATTACK, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnWizard(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wizard(invaderId, Direction.LEFT, Action.ATTACK, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnSlime(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Slime(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnRat(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Rat(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnTroll(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Troll(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnBee(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Bee(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }

    public void spawnWolf(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wolf(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addToContent(invader); // Add the invader to content
    }
    
    // Add the button to the canvas
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

    public String getId() {
        return id;
    }
    
    public ScreenPoint getPosition() {
        return position;
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


    @Override
    public void shapeMoved(String shapeID, int dx, int dy) {
        ;
    }
    
    @Override
    public void shapeStartDrag(String shapeID) {
        ;
    }
    
    @Override
    public void shapeEndDrag(String shapeID) {
        ;
    }
    
    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        startWave();
        hideHighlight();
        removeFromCanvas();
    }
    
    @Override
    public void shapeRightClicked(String shapeID, int x, int y) {
        ;
    }
    
    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        showHighlight();
    }
    
    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        hideHighlight();
    }
}
