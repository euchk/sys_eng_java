package my_base;




import my_game.Pokimon;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyPolygon;
import my_game.Character;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	private Character archer;
	

	@Override
	public void initContent() {
		pokimon = new Pokimon();

		archer = new Character(new ScreenPoint(600, 500), "archer", 
							"resources/objects/archer/D_Idle.png", 
							48, 48, 4);

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

	public Character archer() {
		return archer;
	}

	// public Enemy enemy1() {
	// 	return enemy1;
	// }

	public MyPolygon polygon() {
		return myPolygon;
	}
	

}
