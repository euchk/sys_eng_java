package my_base;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.GameContent;
import base.GameDashboard;
import my_game.GameObject;

public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		
		GameCanvas canvas = gameUI.canvas();
		canvas.setMouseHandler(Game.MouseHandler());
		canvas.setBackground(Color.WHITE);
		canvas.setBackgroundImage("resources/tiles/level1_map.png");
		
		// Add coins text to the canvas
		content.coins().addToCanvas();
		
		// Add Score text to the canvas
		content.score().addToCanvas();

		content.waveStatus().addToCanvas();

		// Add startWave button to canvas
		content.startWaveButton().addToCanvas();
		content.startWaveButton().enableButton();

		// Add slowDown button to canvas
		content.slowDownButton().addToCanvas();
		content.slowDownButton().disableButton();

		// Add all init characters to canvas
		for (GameObject gameObject : content.getAllGameObjects()) {
			gameObject.addToCanvas();
		}
		
	}
	
	@Override
	protected void initDashboard() {
		;
		// super.initDashboard();
		// GameDashboard dashboard = gameUI.dashboard();
		
		// dashboard.setBackground(Color.BLACK);
		

	}
	
	@Override
	public void setGameContent(GameContent content) {
		// Call the Game superclass to set its content 
		super.setGameContent(content);
		// point to the content
		this.content = (MyContent) content;
	}
	
	public MyContent getContent() {
		return this.content;
	}

	public static void main(String[] args) {
		MyGame game = new MyGame();
		game.setGameContent(new MyContent());
		MyPeriodicLoop periodicLoop = new MyPeriodicLoop();
		periodicLoop.setContent(game.getContent());
		game.setPeriodicLoop(periodicLoop);
		game.setMouseHandler(new MyMouseHandler());
		game.setKeyboardListener(new MyKeyboardListener());
		game.initGame();
		
	}

}
