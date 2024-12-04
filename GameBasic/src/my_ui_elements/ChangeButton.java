package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;

public class ChangeButton extends GameButton{
	
	public ChangeButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void action() {
		super.action();

		MyContent content = (MyContent) Game.Content();
		content.polygon().exitEditMode();
		content.polygon().getVisualPolygon().rotate(45);
		//Change your character properties
		content.changeCharacter();
		// Play sound effect
		Game.audioPlayer().play("resources/audio/069.wav", 1);
		
	}

}
