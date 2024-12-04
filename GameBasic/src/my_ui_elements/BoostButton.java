package my_ui_elements;

import base.Game;
import base.GameDashboard;
import my_base.MyContent;
import ui_elements.GameButton;

public class BoostButton extends GameButton{
	
	GameDashboard dashboard = Game.UI().dashboard();

	public BoostButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void action() {
		super.action();

		MyContent content = (MyContent) Game.Content();
		// Plays once an animation sequence
		content.boostCharacter();
		// Play sound effect
		Game.audioPlayer().play("resources/audio/064.wav", 1);
	}

	// Add to the dashboard
    public void addToDashboard() {
		dashboard.addUIElement(this);
    }

	// Remove from the dashboard
    public void removeFromDashboard() {
		dashboard.deleteUIElement("boostButton");
    }

}
