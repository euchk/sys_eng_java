package my_ui_elements;

import java.util.Random;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import ui_elements.ScreenPoint;
import my_game.Invader;
import my_game.Knight;
import my_game.Rat;
import my_game.Slime;
import my_game.Troll;
import my_game.Wizard;
import my_game.Wolf;
import my_game.Bee;
import my_game.Character.Action;
import my_game.Character.Direction;

public class StartWaveButton extends GameButton {
	
	MyContent content = (MyContent) Game.Content();

	public StartWaveButton(String id, String name, int posX, int posY) {
		super(id, name, 200, 40, posX, posY);
	}

    public void spawnKnight(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Knight(invaderLocation, invaderId, Direction.LEFT, Action.ATTACK);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnWizard(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wizard(invaderLocation, invaderId, Direction.LEFT, Action.ATTACK);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnSlime(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Slime(invaderLocation, invaderId, Direction.LEFT, Action.IDLE);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnRat(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Rat(invaderLocation, invaderId, Direction.LEFT, Action.IDLE);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnTroll(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Troll(invaderLocation, invaderId, Direction.LEFT, Action.IDLE);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnBee(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Bee(invaderLocation, invaderId, Direction.LEFT, Action.IDLE);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }

    public void spawnWolf(ScreenPoint invaderLocation) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wolf(invaderLocation, invaderId, Direction.LEFT, Action.IDLE);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addCharacter(invader); // Add the invader to the character list
    }
    

	@Override
	public void action() {
        // Random random = new Random();
        // int posX = random.nextInt(300) + 1000;
        // int posY = random.nextInt(650);

        spawnSlime(new ScreenPoint(2230, 310));
        spawnSlime(new ScreenPoint(2205, 390));
        spawnSlime(new ScreenPoint(2210, 520));
        spawnTroll(new ScreenPoint(2205, 330));
        spawnTroll(new ScreenPoint(2200, 340));
        spawnTroll(new ScreenPoint(2210, 380));
        spawnBee(new ScreenPoint(2200, 330));
        spawnBee(new ScreenPoint(2205, 360));
        spawnBee(new ScreenPoint(2210, 390));
        spawnWolf(new ScreenPoint(2200, 350));
        spawnWolf(new ScreenPoint(2200, 310));
        spawnWolf(new ScreenPoint(2200, 395));

        spawnRat(new ScreenPoint(2600, 320));
        spawnRat(new ScreenPoint(2650, 390));
        spawnRat(new ScreenPoint(2700, 460));
        spawnKnight(new ScreenPoint(2700, 380));
        spawnKnight(new ScreenPoint(2790, 320));
        spawnWizard(new ScreenPoint(2850, 305));
        spawnWizard(new ScreenPoint(2750, 375));
	}

}
