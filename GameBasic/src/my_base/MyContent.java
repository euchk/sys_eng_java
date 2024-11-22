package my_base;




import my_game.Pokimon;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	
	//TODO
	//Declare your own character
	

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

	}
	
	//TODO
	//create a method with the name myCharacter which returns
	//your character for others to use.

	
	//TODO
	//create a changeCharacter method and change inside all the properties you like.
}
