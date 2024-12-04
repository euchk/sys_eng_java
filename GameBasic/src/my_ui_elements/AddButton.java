package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;


public class AddButton extends GameButton{
	
	public AddButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void action() {
		super.action();

		MyContent content = (MyContent) Game.Content();
		//Add your character to your game content
		content.addCharacter();
		// Play sound effect
		Game.audioPlayer().play("resources/audio/099.wav", 1);
		
	}

}
