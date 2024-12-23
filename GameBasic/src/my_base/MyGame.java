package my_base;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import base.GameContent;
import base.GameDashboard;
import my_game.Pokimon;
import my_game.Character;
import my_ui_elements.AddButton;
import my_ui_elements.ChangeButton;
import my_ui_elements.DirectionCombo;
import my_ui_elements.DragCircleCB;
import my_ui_elements.EditPolygonButton;
import my_ui_elements.MusicButton;
import my_ui_elements.RotatePolygonButton;
import shapes.Circle;
import shapes.Shape;

public class MyGame extends Game {
	
	private MyContent content;

	@Override
	protected void initCanvas() {
		
		GameCanvas canvas = gameUI.canvas();
		canvas.setMouseHandler(Game.MouseHandler());
		canvas.setBackground(Color.WHITE);
		canvas.setBackgroundImage("resources/tiles/background.png");

		Pokimon pokimon = content.pokimon();
		// pokimon.addToCanvas();
		
		// Add all init characters to canvas
		for (Character character : content.getAllCharacters()) {
			character.addToCanvas();
		}
		
		// canvas.addShape(content.polygon().getVisualPolygon());
		Circle c = new Circle("circle", 300, 300, 50);
		// c.setDraggable(false);
		// canvas.addShape(c);
	}
	
	@Override
	protected void initDashboard() {
		super.initDashboard();
		GameDashboard dashboard = gameUI.dashboard();
		
		dashboard.setBackground(Color.BLACK);
		
		// Add a the Polygon buttons
		dashboard.addUIElement(new EditPolygonButton("editButton", "Edit", 60, 40));
		dashboard.addUIElement(new RotatePolygonButton("rotateButton", "Rotate", 60, 100));

		// Add a the Circle drag checkbox
		dashboard.addUIElement(new DragCircleCB("dragCB", "Drag Circle", 280, 80, 200, 40, false));

		// Add a the direction list combo
		dashboard.addUIElement(new DirectionCombo(280, 40));

		// Add a the AddButton button
		dashboard.addUIElement(new AddButton("addButton", "Add", 540, 40));
		
		// Add the ChangeButton button to the dashboard
		dashboard.addUIElement(new ChangeButton("changeButton", "Change", 540, 100));
		dashboard.addUIElement(new MusicButton("musicButton", "Play", 700, 40));

	}
	
	@Override
	public void setGameContent(GameContent content) {
		// Call the Game superclass to set its content 
		super.setGameContent(content);
		// point to the content with a variable of type MyContent so we have access to all
		// our game specific data
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
