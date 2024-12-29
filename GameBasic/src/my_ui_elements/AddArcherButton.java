package my_ui_elements;

import java.util.Random;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import ui_elements.ScreenPoint;
import my_game.Archer;
import my_game.Defender;
import my_game.Marksman;
import my_game.Sharpshooter;
import my_game.Character.Action;
import my_game.Character.Direction;

public class AddArcherButton extends GameButton {
	
	MyContent content = (MyContent) Game.Content();

	public AddArcherButton(String id, String name, int posX, int posY) {
		super(id, name, 200, 40, posX, posY);
	}

	public void AddSharpshooter(ScreenPoint defenderLocation){
		String defenderId = "defender_" + System.currentTimeMillis(); // Unique ID for each archer
		Defender defender = new Sharpshooter(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
		defender.addToCanvas();
		content.addCharacter(defender);
	}

	public void AddMarksman(ScreenPoint defenderLocation){
		String defenderId = "defender_" + System.currentTimeMillis(); // Unique ID for each archer
		Defender defender = new Marksman(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
		defender.addToCanvas();
		content.addCharacter(defender);
	}

	public void AddArcher(ScreenPoint defenderLocation){
		String defenderId = "defender_" + System.currentTimeMillis(); // Unique ID for each archer
		Defender defender = new Archer(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
		defender.addToCanvas();
		content.addCharacter(defender);
	}

	@Override
	public void action() {

		if (content.coins().spendCoins(10)) {
			Random random = new Random();
			int posX = random.nextInt(300) + 500;
			int posY = random.nextInt(701);
			AddArcher(new ScreenPoint(posX + 30, posY - 10));
			AddMarksman(new ScreenPoint(posX, posY));
			AddSharpshooter(new ScreenPoint(posX - 30, posY + 10));
		}else{
			System.out.println("Not enough coins.");
		}
	}

}
