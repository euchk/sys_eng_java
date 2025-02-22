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
	
	private final HashMap<String, GameObject> gameObjects; // Store all game objects with id as key
	private final HashMap<String, GameObject> pendingGameObjects; //  // Stores all objects to be added to the game
	private final HashMap<String, GameObject> pendingRemovals; // Allow safe removal of gameObjects

	
	public MyContent() {
		gameObjects = new HashMap<>();
		pendingGameObjects = new HashMap<>();
		pendingRemovals = new HashMap<>();
    }

	@Override
	public void initContent() {

		score = new Score(5, 70, 20);
		coins = new Coins(300, 70, 100);

		ScreenPoint location1 = new ScreenPoint(1200, 150);
		tower1 = new Tower("tower1", location1);
		addToContent(tower1);
		// archer1 = new Archer(new ScreenPoint(location1.x + 12, location1.y + 120), "archer1", Direction.DOWN, Action.IDLE);
		// addToContent(archer1);
		// archer2 = new Archer(new ScreenPoint(location1.x - 8, location1.y + 110), "archer2", Direction.DOWN, Action.IDLE);
		// addToContent(archer2);
		// archer3 = new Archer(new ScreenPoint(location1.x + 28, location1.y + 110), "archer3", Direction.DOWN, Action.IDLE);
		// addToContent(archer3);

		ScreenPoint location2 = new ScreenPoint(800, 120);
		tower2= new Tower("tower2", location2);
		addToContent(tower2);
		// marksman1 = new Marksman(new ScreenPoint(location2.x + 12, location2.y + 120), "marksman1", Direction.DOWN, Action.IDLE);
		// addToContent(marksman1);
		// marksman2 = new Marksman(new ScreenPoint(location2.x - 8, location2.y + 110), "marksman2", Direction.DOWN, Action.IDLE);
		// addToContent(marksman2);
		// marksman3 = new Marksman(new ScreenPoint(location2.x + 28, location2.y + 110), "marksman3", Direction.DOWN, Action.IDLE);
		// addToContent(marksman3);

		ScreenPoint location3 = new ScreenPoint(400, 150);
		tower3= new Tower("tower3", location3);
		addToContent(tower3);
		// sharpshooter1 = new Sharpshooter(new ScreenPoint(location3.x + 12, location3.y + 120), "sharpshooter1", Direction.DOWN, Action.IDLE);
		// addToContent(sharpshooter1);
		// sharpshooter2 = new Sharpshooter(new ScreenPoint(location3.x - 8, location3.y + 110), "sharpshooter2", Direction.DOWN, Action.IDLE);
		// addToContent(sharpshooter2);
		// sharpshooter3 = new Sharpshooter(new ScreenPoint(location3.x + 28, location3.y + 110), "sharpshooter3", Direction.DOWN, Action.IDLE);
		// addToContent(sharpshooter3);

		addPendingObjects();

		gameControl = new GameControl(this);
	}

	// Retrieves a GameObject from the game by its ID, returns `null` if doesn't exist
	public GameObject getFromContent(String id) {
		return gameObjects.get(id);
	}

	// Get a collection of all active game objects
    public Collection<GameObject> getAllGameObjects() {
        return gameObjects.values();
    }

    /**
     * Adds a GameObject to the pending list.
     * This object will be added to the main gameObjects list at the end of the game loop.
     */
    public void addToContent(GameObject gameObject) {
        pendingGameObjects.put(gameObject.getId(), gameObject);
    }

    /**
     * Moves all pending game objects to the main content list
     * This should be called at the end of the game loop to finalize additions
     */
    public void addPendingObjects() {
        gameObjects.putAll(pendingGameObjects);
        pendingGameObjects.clear();
    }

	/**
     * Marks a game object for removal
     * The object will be removed at the end of the loop
     */
    public void removeFromContent(String id) {
        GameObject gameObject = gameObjects.get(id);
        if (gameObject != null) {
            pendingRemovals.put(id, gameObject);
        }
    }
	
	/**
     * Finalizes removals: removes all objects that were marked for deletion
     *  This should be called at the end of the game loop to finalize removals
     */
    public void removePendingObjects() {
        for (String id : pendingRemovals.keySet()) {
            gameObjects.remove(id);
        }
        pendingRemovals.clear();
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
