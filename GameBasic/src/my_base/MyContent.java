package my_base;




import my_game.Pokimon;
import my_game.Tower;
import my_game.Defender;
import my_game.Enemy;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private Tower tower1, tower2;
	private Defender archer1, archer2;
	private Enemy enemy1;
	private MyPolygon myPolygon;
	

	@Override
	public void initContent() {
		pokimon = new Pokimon();
		tower1 = new Tower(new ScreenPoint(520, 440), "tower1");
		archer1 = new Defender(new ScreenPoint(530, 540), "archer1");
		tower2 = new Tower(new ScreenPoint(685, 600), "tower2");
		archer2 = new Defender(new ScreenPoint(695, 700), "archer2");
		enemy1 = new Enemy(new ScreenPoint(930, 640), "enemy1");
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

	public Tower tower1() {
		return tower1;
	}

	public Tower tower2() {
		return tower2;
	}

	public Defender archer1() {
		return archer1;
	}

	public Defender archer2() {
		return archer2;
	}

	public Enemy enemy1() {
		return enemy1;
	}

	public MyPolygon polygon() {
		return myPolygon;
	}
	

}
