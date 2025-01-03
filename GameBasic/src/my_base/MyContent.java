package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Character;
import my_game.Character.Action;
import my_game.Character.Direction;
import ui_elements.ScreenPoint;
import my_game.GameControl;
import my_game.Marksman;
import my_game.Archer;
import my_game.Arrow;
import my_game.Coins;
import my_game.Defender;
import my_game.Score;
import my_game.Sharpshooter;
import my_game.Tower;
import base.GameContent;


public class MyContent extends GameContent{
	private GameControl gameControl;
	private Coins coins;
	private Score score;

	private Tower tower1, tower2, tower3;
	private Archer archer1, archer2, archer3;
	private Marksman marksman1, marksman2, marksman3;
	private Sharpshooter sharpshooter1, sharpshooter2, sharpshooter3;
	
	private HashMap<String, Character> characters; // Store all characters with id as key
	private HashMap<String, Arrow> arrows; 		   // Store all arrows with id as key
	
	public MyContent() {
		characters = new HashMap<>();
		arrows = new HashMap<>();
    }

	@Override
	public void initContent() {

		score = new Score(5, 70, 20);
		coins = new Coins(300, 70, 100);

		ScreenPoint location1 = new ScreenPoint(1200, 150);
		tower1 = new Tower("tower1", location1, "resources/objects/tower/4.png");
		archer1 = new Archer(new ScreenPoint(location1.x + 12, location1.y + 120), "archer1", Direction.DOWN, Action.IDLE);
		addCharacter(archer1);
		archer2 = new Archer(new ScreenPoint(location1.x - 8, location1.y + 110), "archer2", Direction.DOWN, Action.IDLE);
		addCharacter(archer2);
		archer3 = new Archer(new ScreenPoint(location1.x + 28, location1.y + 110), "archer3", Direction.DOWN, Action.IDLE);
		addCharacter(archer3);

		ScreenPoint location2 = new ScreenPoint(800, 120);
		tower2= new Tower("tower2", location2, "resources/objects/tower/5.png");
		// marksman1 = new Marksman(new ScreenPoint(location2.x + 12, location2.y + 120), "marksman1", Direction.DOWN, Action.IDLE);
		// addCharacter(marksman1);
		marksman2 = new Marksman(new ScreenPoint(location2.x - 8, location2.y + 110), "marksman2", Direction.DOWN, Action.IDLE);
		addCharacter(marksman2);
		marksman3 = new Marksman(new ScreenPoint(location2.x + 28, location2.y + 110), "marksman3", Direction.DOWN, Action.IDLE);
		addCharacter(marksman3);

		ScreenPoint location3 = new ScreenPoint(400, 150);
		tower3= new Tower("tower3", location3, "resources/objects/tower/6.png");
		sharpshooter1 = new Sharpshooter(new ScreenPoint(location3.x + 12, location3.y + 120), "sharpshooter1", Direction.DOWN, Action.IDLE);
		addCharacter(sharpshooter1);
		sharpshooter2 = new Sharpshooter(new ScreenPoint(location3.x - 8, location3.y + 110), "sharpshooter2", Direction.DOWN, Action.IDLE);
		addCharacter(sharpshooter2);
		sharpshooter3 = new Sharpshooter(new ScreenPoint(location3.x + 28, location3.y + 110), "sharpshooter3", Direction.DOWN, Action.IDLE);
		addCharacter(sharpshooter3);

		gameControl = new GameControl(this);
	}

    // Add and remove a character from the list
    public void addCharacter(Character character) {
		characters.put(character.getId(), character);
    }

	// Allow iteration on all characters
	public Collection<Character> getAllCharacters() {
        return characters.values();
    }

	// Add and remove an arrow from the list
    public void addArrow(Arrow arrow) {
		arrows.put(arrow.getId(), arrow);
    }

	// Allow iteration on all arrows
	public Collection<Arrow> getAllArrows() {
        return arrows.values();
    }

	public Coins coins() {
		return coins;
	}

	public Score score() {
		return score;
	}

	public GameControl gameControl() {
		return gameControl;
	}

	public Tower tower1() {
		return tower1;
	}

	public Tower tower2() {
		return tower2;
	}

	public Tower tower3() {
		return tower3;
	}

}
