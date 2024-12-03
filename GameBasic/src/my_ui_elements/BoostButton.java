package my_ui_elements;

import base.Game;
import base.GameDashboard;
import my_base.MyContent;
import ui_elements.GameButton;

public class BoostButton extends GameButton{
	
	public BoostButton(String id, String name, int posX, int posY) {
		super(id, name, 100, 40, posX, posY);
	}

	@Override
	public void action() {
		// The basic buttonAction prints the id of the button to the console.
		// Keep the call to super to preserve this behavior or remove it if you don't want the printing.
		super.action();
		
		MyContent content = (MyContent) Game.Content();
		
		// Plays a one time animation sequence
		content.boostCharacter();
	}

	// Add the "hidden" boost button to dashboard
    public void addToDashboard() {
        GameDashboard dashboard = Game.UI().dashboard();
        dashboard.addUIElement(this);
    }

	// Remove from dashboard
    public void removeFromDashboard() {
        GameDashboard dashboard = Game.UI().dashboard();
        dashboard.deleteUIElement("boostButton");
    }

}
