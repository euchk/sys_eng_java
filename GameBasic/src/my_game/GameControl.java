package my_game;

import java.util.Iterator;

import org.apache.poi.ss.formula.functions.T;

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
		for (Character character : content.getAllCharacters()) {
			character.gameStep();
		}

		// Iterate over all shapes and activate periodic method
		for (Arrow arrow : content.getAllArrows()) {
			arrow.gameStep();
		}
		
        // Remove inactive objects
        removeInactiveCharacters();
		removeInactiveArrows();

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

    // Safely removes inactive characters
    public void removeInactiveCharacters() {
        Iterator<Character> iterator = content.getAllCharacters().iterator();
        while (iterator.hasNext()) {
            Character character = iterator.next();
            if (!character.isActive()) {
                if (character instanceof Knight) {
                    Knight knight = (Knight) character;
                    if (knight.getIsKilled()) {
                        content.coins().addCoins(5);
                    }
                    else if (knight.getisPassed()) {
                        content.score().increment();
                    }
                }
                iterator.remove();
            }
        }
    }

    // Safely removes inactive arrows
	public void removeInactiveArrows(){
		Iterator<Arrow> iterator = content.getAllArrows().iterator();
		while (iterator.hasNext()) {
			Arrow arrow = iterator.next();
			if (!arrow.isActive()) {
				iterator.remove();
			}
		}
	}

    // Check for game over conditions
    public void checkGameOver() {
        if (content.score().getCurrentScore() > content.score().getMaxEnemiesPassed()) {
            gameOver = true;
        }
    }
    
}
