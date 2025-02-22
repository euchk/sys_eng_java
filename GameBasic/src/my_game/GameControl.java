package my_game;

import java.util.Iterator;

import base.Game;
import base.GameCanvas;
import my_base.MyContent;
import shapes.Text;

public class GameControl {
    private MyContent content;
    private boolean gameOver = false;

    public GameControl(MyContent content) {
        this.content = content;
    }

    public void gameStep(){
        if (gameOver) return;

        GameCanvas canvas = Game.UI().canvas();

        // Iterate over all characters and activate periodic method
        for (GameObject gameObject : content.getAllGameObjects()) {
            gameObject.gameStep();
        }
		
        addPendingObjects();
        removeInactiveGameObjects();
        removePendingObjects();

        // Check game over
        checkGameOver();
        if (gameOver) {
            Text text = new Text("game over", "GAME OVER", 700, 350);
            text.setFontSize(50);
            canvas.addShape(text);
            return;
        }

        // Repaint canvas after all gameSteps 
		canvas.revalidate();
		canvas.repaint();
    }

    // Add all pending gameObjects to the game
    public void addPendingObjects() {
        content.addPendingObjects();
    }

    // Remove all pending gameObjects from the game
    public void removePendingObjects() {
        content.removePendingObjects();
    }

    // Safely marks inactive objects for removal
    public void removeInactiveGameObjects() {
        for (GameObject gameObject : content.getAllGameObjects()) {
            if (!gameObject.isActive()) {
                // Handle specific logic for Invader objects
                if (gameObject instanceof Invader) {
                    Invader invader = (Invader) gameObject;
                    if (invader.getIsKilled()) {
                        content.coins().addCoins(invader.getCoins());
                    } else if (invader.getisPassed()) {
                        content.score().increment();
                    }
                }
                content.removeFromContent(gameObject.getId());
            }
        }
    }

    // Check for game over conditions
    public void checkGameOver() {
        if (content.score().getCurrentScore() >= content.score().getMaxInvadersPassed()) {
            gameOver = true;
        }
    }
    
}
