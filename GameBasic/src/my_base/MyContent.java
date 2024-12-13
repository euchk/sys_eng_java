package my_base;




import my_game.Pokimon;
import my_game.MyCharacter;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyCharacter tower1, tower2;
	private MyPolygon myPolygon;
	

	@Override
	public void initContent() {
		pokimon = new Pokimon();
		tower1 = new MyCharacter(new ScreenPoint(320, 220), "tower1");
		tower2 = new MyCharacter(new ScreenPoint(450, 380), "tower2");
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

	public MyCharacter tower1() {
		return tower1;
	}

	public MyCharacter tower2() {
		return tower2;
	}

	public MyPolygon polygon() {
		return myPolygon;
	}
	

}
