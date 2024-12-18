package my_base;




import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;

	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		// You can comment this line if you don't want the pokimon to move.
		redrawPokimon();
		
		redrawTower1();
		redrawTower2();
		redrawArcher1();
		redrawArcher2();
		redrawEnemy1();

		
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

	private void redrawTower1() {
		if (content.tower1() == null)
			return;
		content.tower1().animatedImage().nextFrame();
	}

	private void redrawTower2() {
		if (content.tower2() == null)
			return;
		content.tower2().animatedImage().nextFrame();
	}

	private void redrawArcher1() {
		if (content.archer1() == null)
			return;
		content.archer1().animatedImage().nextFrame();
	}

	private void redrawArcher2() {
		if (content.archer2() == null)
			return;
		content.archer2().animatedImage().nextFrame();
	}

	private void redrawEnemy1() {
		if (content.enemy1() == null)
			return;
		content.enemy1().move();
		content.enemy1().animatedImage().nextFrame();
	}


}
