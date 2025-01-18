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

    // Safely removes inactive objects
    public void removeInactiveGameObjects() {
        Iterator<GameObject> iterator = content.getAllGameObjects().iterator();
        while (iterator.hasNext()) {
            GameObject gameObject = iterator.next();
            if (!gameObject.isActive()) {
                // Handle specific logic for Invader objects
                if (gameObject instanceof Invader) {
                    Invader invader = (Invader) gameObject;
                    if (invader.getIsKilled()) {
                        content.coins().addCoins(5);
                    } else if (invader.getisPassed()) {
                        content.score().increment();
                    }
                }
                // Remove from game
                iterator.remove();
            }
        }
    }


    // // Safely removes inactive characters
    // public void removeInactiveCharacters() {
    //     Iterator<Character> iterator = content.getAllCharacters().iterator();
    //     while (iterator.hasNext()) {
    //         Character character = iterator.next();
    //         if (!character.isActive()) {
    //             if (character instanceof Invader) {
    //                 Invader knight = (Invader) character;
    //                 if (knight.getIsKilled()) {
    //                     content.coins().addCoins(5);
    //                 }
    //                 else if (knight.getisPassed()) {
    //                     content.score().increment();
    //                 }
    //             }
    //             iterator.remove();
    //         }
    //     }
    // }

    // // Safely removes inactive arrows
	// public void removeInactiveArrows(){
	// 	Iterator<Arrow> iterator = content.getAllArrows().iterator();
	// 	while (iterator.hasNext()) {
	// 		Arrow arrow = iterator.next();
	// 		if (!arrow.isActive()) {
	// 			iterator.remove();
	// 		}
	// 	}
	// }

    // Check for game over conditions
    public void checkGameOver() {
        if (content.score().getCurrentScore() >= content.score().getMaxInvadersPassed()) {
            gameOver = true;
        }
    }
    
}
