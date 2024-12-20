package my_base;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import my_game.Character;

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

		for (Character character : content.getAllCharacters()) {
			character.periodicUpdate();
		}
		
		// Repaint canvas after all periodicUpdate 
		GameCanvas canvas = Game.UI().canvas();
		canvas.revalidate();
		canvas.repaint();
		
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

}
