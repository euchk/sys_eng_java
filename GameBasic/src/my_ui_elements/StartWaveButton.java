package my_ui_elements;

import java.util.Random;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import ui_elements.ScreenPoint;
import my_game.Knight;
import my_game.Character.Action;
import my_game.Character.Direction;

public class StartWaveButton extends GameButton {
	
	MyContent content = (MyContent) Game.Content();

	public StartWaveButton(String id, String name, int posX, int posY) {
		super(id, name, 200, 40, posX, posY);
	}

	@Override
	public void action() {

			Random random = new Random();
			ScreenPoint knightLocation = new ScreenPoint(1200, random.nextInt(700));
			
			String knightId = "knight_" + System.currentTimeMillis(); // Unique ID for each archer
			Knight knight = new Knight(knightLocation, knightId, Direction.LEFT, Action.ATTACK);
			knight.addToCanvas();
			content.addCharacter(knight);
		
	}

}
