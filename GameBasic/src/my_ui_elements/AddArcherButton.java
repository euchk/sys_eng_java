package my_ui_elements;

import java.util.Random;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import ui_elements.ScreenPoint;
import my_game.Archer;
import my_game.Character.Action;
import my_game.Character.Direction;

public class AddArcherButton extends GameButton {
	
	MyContent content = (MyContent) Game.Content();

	public AddArcherButton(String id, String name, int posX, int posY) {
		super(id, name, 200, 40, posX, posY);
	}

	@Override
	public void action() {

		if (content.coins().spendCoins(10)) {
			Random random = new Random();
			ScreenPoint archerLocation = new ScreenPoint(random.nextInt(300) + 500, 
														 random.nextInt(701));
			
			String archerId = "archer_" + System.currentTimeMillis(); // Unique ID for each archer
			Archer archer = new Archer(archerLocation, archerId, Direction.DOWN, Action.IDLE);
			archer.addToCanvas();
			content.addCharacter(archer);
		}else{
			System.out.println("Not enough coins.");
		}
	}

}
