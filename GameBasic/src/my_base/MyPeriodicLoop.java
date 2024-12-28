package my_base;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import my_game.Character;
import my_game.Arrow;


public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
		
	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		// You can comment this line if you don't want the pokimon to move.
		redrawPokimon();

		// Iterate over all characters and activate periodic method
		for (Character character : content.getAllCharacters()) {
			character.periodicUpdate();
		}

		// Iterate over all shapes and activate periodic method
		for (Arrow arrow : content.getAllArrows()) {
			arrow.periodicUpdate();
		}
		
		// Remove inactivated objects
		content.removeInactivatedArrows();
		content.removeInactivatedCharacters();

		// Repaint canvas after all periodicUpdates 
		GameCanvas canvas = Game.UI().canvas();
		canvas.revalidate();
		canvas.repaint();
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

}
