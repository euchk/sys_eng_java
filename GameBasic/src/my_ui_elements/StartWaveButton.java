package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameButton;
import ui_elements.ScreenPoint;
import my_game.Invader;
import my_game.Knight;
import my_game.Path;
import my_game.Paths;
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
    Path levelOnePath = Paths.levelOnePath();

	public StartWaveButton(String id, String name, int posX, int posY) {
		super(id, name, 200, 40, posX, posY);
	}

    public void spawnKnight(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Knight(invaderId, Direction.LEFT, Action.ATTACK, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnWizard(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wizard(invaderId, Direction.LEFT, Action.ATTACK, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnSlime(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Slime(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnRat(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Rat(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnTroll(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Troll(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnBee(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Bee(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }

    public void spawnWolf(Path path) {
        String invaderId = "invader_" + System.currentTimeMillis(); // Unique ID for each archer
        Invader invader = new Wolf(invaderId, Direction.LEFT, Action.IDLE, path);
        invader.addToCanvas(); // Add the invader to the game canvas
        content.addGameObject(invader); // Add the invader to the character list
    }
    

	@Override
	public void action() {

        spawnSlime(Paths.testPath());
        spawnTroll(Paths.levelTwoPath());
        spawnBee(Paths.levelThreePath());
        spawnWolf(Paths.levelFourPath());
        spawnRat(Paths.levelFivePath());
        spawnKnight(Paths.testPath());
        spawnWizard(Paths.testPath());
        
	}

}
