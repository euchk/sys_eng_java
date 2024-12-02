package my_base;




import my_game.Pokimon;
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
	
	//TODO
	//Declare your own character
	private MyCharacter myCharacter;
	private EquipmentCombo weaponCombo;
	

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
		//TODO
		//Create an instance of your character and set its properties with
		//initial values
		
		//TODO
		//Add your character visual representation to the canvas using its addToCanvas() method.

		// There is no limit for pressing add so each press will reset the character position and properties
		// Add a the equipment list combo to the dashboard
		weaponCombo = new EquipmentCombo(280, 120);
		weaponCombo.addToDashboard();
		
		// Add the character to the canvas
		myCharacter = new MyCharacter(new ScreenPoint(200, 700), "myCharacter");
		myCharacter.addToCanvas();

		// Play sound effect
		Game.audioPlayer().play("resources/audio/099.wav", 1);
	}
	
	//TODO
	//create a method with the name myCharacter which returns
	//your character for others to use.
	public MyCharacter myCharacter() {
        return myCharacter;
	}
	
	//TODO
	//create a changeCharacter method and change inside all the properties you like.
	public void changeCharacter(){
		if(myCharacter() == null) return;
		myCharacter().changePosition();
		
		// Play sound effect
		Game.audioPlayer().play("resources/audio/060.wav", 1);
	}
}
