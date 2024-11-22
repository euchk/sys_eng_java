package my_base;




import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;

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
		
		//TODO
		//Redraw your character periodically by calling the correct method
		
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

	private void redrawCharacter() {
		
		GameCanvas canvas = Game.UI().canvas();
		
		//TODO
		//Remove the comment from the next line so you can easily 
		//access your character

		//MyCharacter character = content.myCharacter();
		
		//Since this function is called every interval, it will also be called
		//before the character is created. Therefore, we check if the character 
		//exists and if not, we return without doing anything.
		
		//TODO: Remove comments from next 2 lines
//		if (character == null)
//			return;
		
		//TODO
		//Call the canvas to change the shape properties according to
		//its current property values
		//You can get the shape using canvas.getShape(id) with the id of your character
		//Then you can cast it so you can refer to its specific properties.
		//For example, if your shape is a Circle you can use:
		//Circle circle = (Circle) canvas.getShape(id)
		//and then change the specific Circle properties.
		
	}


}
