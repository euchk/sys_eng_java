package my_base;




import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import my_game.MyCharacter;

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

		// TODO: for some reason while the 2 characters redraw we get artifacts in myCharacter animation
		// might be the accesive repaint() use
		// redrawPokimon();
		
		//Redraw your character periodically by calling the correct method		
		redrawCharacter();
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

	private void redrawCharacter() {
		MyCharacter character = content.myCharacter();
		if (character == null) return;
        character.nextFrame(); // Advance the animation frame
	}
}
