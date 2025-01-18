package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Character.Action;
import my_game.Character.Direction;
import ui_elements.ScreenPoint;
import my_game.GameControl;
import my_game.GameObject;
import my_game.Marksman;
import my_game.Archer;
import my_game.Coins;
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
	
	private HashMap<String, GameObject> pendingGameObjects; // Store all game objects with id as key
	private HashMap<String, GameObject> gameObjects; // Store all game objects with id as key
	
	public MyContent() {
		gameObjects = new HashMap<>();
		pendingGameObjects = new HashMap<>(); // Stores all objects to be added to the game
    }

	@Override
	public void initContent() {

		score = new Score(5, 70, 20);
		coins = new Coins(300, 70, 100);

		ScreenPoint location1 = new ScreenPoint(1200, 150);
		tower1 = new Tower("tower1", location1, "resources/objects/tower/4.png");
		addGameObject(tower1);
		archer1 = new Archer(new ScreenPoint(location1.x + 12, location1.y + 120), "archer1", Direction.DOWN, Action.IDLE);
		addGameObject(archer1);
		archer2 = new Archer(new ScreenPoint(location1.x - 8, location1.y + 110), "archer2", Direction.DOWN, Action.IDLE);
		addGameObject(archer2);
		archer3 = new Archer(new ScreenPoint(location1.x + 28, location1.y + 110), "archer3", Direction.DOWN, Action.IDLE);
		addGameObject(archer3);

		ScreenPoint location2 = new ScreenPoint(800, 120);
		tower2= new Tower("tower2", location2, "resources/objects/tower/5.png");
		addGameObject(tower2);
		marksman1 = new Marksman(new ScreenPoint(location2.x + 12, location2.y + 120), "marksman1", Direction.DOWN, Action.IDLE);
		addGameObject(marksman1);
		marksman2 = new Marksman(new ScreenPoint(location2.x - 8, location2.y + 110), "marksman2", Direction.DOWN, Action.IDLE);
		addGameObject(marksman2);
		marksman3 = new Marksman(new ScreenPoint(location2.x + 28, location2.y + 110), "marksman3", Direction.DOWN, Action.IDLE);
		addGameObject(marksman3);

		ScreenPoint location3 = new ScreenPoint(400, 150);
		tower3= new Tower("tower3", location3, "resources/objects/tower/6.png");
		addGameObject(tower3);
		sharpshooter1 = new Sharpshooter(new ScreenPoint(location3.x + 12, location3.y + 120), "sharpshooter1", Direction.DOWN, Action.IDLE);
		addGameObject(sharpshooter1);
		sharpshooter2 = new Sharpshooter(new ScreenPoint(location3.x - 8, location3.y + 110), "sharpshooter2", Direction.DOWN, Action.IDLE);
		addGameObject(sharpshooter2);
		sharpshooter3 = new Sharpshooter(new ScreenPoint(location3.x + 28, location3.y + 110), "sharpshooter3", Direction.DOWN, Action.IDLE);
		addGameObject(sharpshooter3);

		addPendingObjects();

		gameControl = new GameControl(this);
	}

	// Get a collection of all active game objects
    public Collection<GameObject> getAllGameObjects() {
        return gameObjects.values();
    }

    // Add a game object to pending objects
    public void addGameObject(GameObject gameObject) {
        pendingGameObjects.put(gameObject.getId(), gameObject);
    }

    // This allows adding gameObject while iterating on the gameObjects
    public void addPendingObjects() {
        gameObjects.putAll(pendingGameObjects);
        pendingGameObjects.clear();
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

}
