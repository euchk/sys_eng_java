package my_base;

import java.util.ArrayList;
import java.util.List;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import my_game.Character;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
	List<Character> charactersToRemove = new ArrayList<>();


	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		// You can comment this line if you don't want the pokimon to move.
		redrawPokimon();


		// Iterate over all characters for animation and for health check (removes below zero)
		for (Character character : content.getAllCharacters()) {
			character.periodicUpdate();
			if (character.getHealth() <= 0) {
				charactersToRemove.add(character);
			}
		}

		// Remove collected characters (avoids removing from a list while iterating)
		for (Character character : charactersToRemove) {
			character.removeFromCanvas();
			content.removeCharacter(character);
		}

		
		// Repaint canvas after all periodicUpdates 
		GameCanvas canvas = Game.UI().canvas();
		canvas.revalidate();
		canvas.repaint();
		
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

}
