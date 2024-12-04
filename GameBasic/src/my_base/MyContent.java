package my_base;




import my_game.Pokimon;
import my_ui_elements.BoostButton;
import my_ui_elements.EquipmentCombo;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyCharacter;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	
	//Declare your own character
	private MyCharacter myCharacter;
	private EquipmentCombo equipmentCombo;
	private BoostButton boostButton;
	

	@Override
	public void initContent() {
		pokimon = new Pokimon();
		ScreenPoint[] points = {
			new ScreenPoint(100, 100),
			new ScreenPoint(130, 50),
			new ScreenPoint(170, 50),
			new ScreenPoint(200, 100),
			new ScreenPoint(220, 170),
			new ScreenPoint(170, 150),
			new ScreenPoint(130, 150)
		};

		myPolygon = new MyPolygon(points);
	}	
	
	public Pokimon pokimon() {
		return pokimon;
	}

	public MyPolygon polygon() {
		return myPolygon;
	}
	
	public void addCharacter() {
		// Limit to 1 press
		if(myCharacter != null) return;
	
		// Add the character to the canvas
		myCharacter = new MyCharacter(new ScreenPoint(200, 700), "myCharacter");
		myCharacter.addToCanvas();

		// Create a boost button but don't add it to the dashboard yet
		// boostButton must be initiated before equipmentCombo because the combo box default setting
		// calls a method of boost button
		boostButton = new BoostButton("boostButton", "Boost", 700, 100);
		
		// Add a the equipment list combo to the dashboard
		equipmentCombo = new EquipmentCombo(280, 120);
		equipmentCombo.addToDashboard();

	}

	//create a method with the name myCharacter which returns
	//your character for others to use.
	public MyCharacter myCharacter() {
        return myCharacter;
	}

	// Return boostButton for others to use
	public BoostButton boostButton(){
		return boostButton;
	}
	
	//create a changeCharacter method and change inside all the properties you like.
	public void changeCharacter(){
		if(myCharacter == null) return;
		myCharacter.changePosition();
	}

	public void boostCharacter(){
		if(myCharacter == null) return;
		myCharacter.setBoostAnimation();
	}
}
