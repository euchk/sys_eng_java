package my_game;

import java.util.Iterator;

import base.Game;
import base.GameCanvas;
import my_base.MyContent;

public class GameControl {
    private MyContent content;

    public GameControl(MyContent content) {
        this.content = content;
    }

    public void gameStep(){

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

        // Repaint canvas after all gameSteps 
		GameCanvas canvas = Game.UI().canvas();
		canvas.revalidate();
		canvas.repaint();
    }

    // Safely removes inactive characters
    public void removeInactiveCharacters() {
        Iterator<Character> iterator = content.getAllCharacters().iterator();
        while (iterator.hasNext()) {
            Character character = iterator.next();
            if (!character.isActive()) {
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
    
}
