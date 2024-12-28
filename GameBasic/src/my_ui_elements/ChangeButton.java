package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import my_game.Character;

public class ChangeButton extends GameButton{
	
	public ChangeButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void action() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.action();
		
		MyContent content = (MyContent) Game.Content();
		for (Character character : content.getAllCharacters()) {
			character.reduceHealth(10);
		}		
	}
}
