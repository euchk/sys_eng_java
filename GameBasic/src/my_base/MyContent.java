package my_base;

import java.util.Collection;
import java.util.HashMap;


import ui_elements.ScreenPoint;
import my_game.GameControl;
import my_game.GameObject;
import my_game.Coins;
import my_game.Score;
import my_game.StaticAnimatedObject;
import my_game.Tower;
import my_game.WaveStatus;
import my_shapes.SlowDownButton;
import my_shapes.StartWaveButton;
import base.GameContent;


public class MyContent extends GameContent{
	private GameControl gameControl;
	private Coins coins;
	private Score score;
	private WaveStatus waveStatus;
	private StartWaveButton startWaveButton;
	private SlowDownButton slowDownButton;

	private Tower tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8;

	private StaticAnimatedObject flag1, flag2, campfire1, campfire2, tent1, tent2, tent3, tent4, tent5, tent6;
	
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

		gameControl = new GameControl(this);

		// Status texts
		score = new Score(10, 80, 850);
		coins = new Coins(290, 80, 900);
		waveStatus = new WaveStatus(7, 80, 800);
		
		// Buttons
		startWaveButton = new StartWaveButton("startWaveButton", 1700, 800, gameControl);
		slowDownButton = new SlowDownButton("slowDownButton", 1800, 800, gameControl);

		// Towers
		ScreenPoint location1 = new ScreenPoint(400, 120);
		tower1 = new Tower("tower1", location1);
		addToContent(tower1);

		ScreenPoint location2 = new ScreenPoint(715, 75);
		tower2= new Tower("tower2", location2);
		addToContent(tower2);

		ScreenPoint location3 = new ScreenPoint(1000, 120);
		tower3= new Tower("tower3", location3);
		addToContent(tower3);

		ScreenPoint location4 = new ScreenPoint(1360, 170);
		tower4 = new Tower("tower4", location4);
		addToContent(tower4);

		ScreenPoint location5 = new ScreenPoint(1160, 320);
		tower5= new Tower("tower5", location5);
		addToContent(tower5);

		ScreenPoint location6 = new ScreenPoint(960, 710);
		tower6= new Tower("tower6", location6);
		addToContent(tower6);

		ScreenPoint location7 = new ScreenPoint(1630, 155);
		tower7= new Tower("tower7", location7);
		addToContent(tower7);

		ScreenPoint location8 = new ScreenPoint(1430, 420);
		tower8= new Tower("tower8", location8);
		addToContent(tower8);

		// Animated objects on map
		ScreenPoint flag1_location = new ScreenPoint(1050, 900);
		flag1 = new StaticAnimatedObject("flag1", flag1_location, "resources/objects/objects/flag.png", 6, 32, 64, false);
		addToContent(flag1);

		ScreenPoint flag2_location = new ScreenPoint(830, 900);
		flag2 = new StaticAnimatedObject("flag2", flag2_location, "resources/objects/objects/flag.png", 6, 32, 64, false);
		addToContent(flag2);

		ScreenPoint tent1_location = new ScreenPoint(1450, 750);
		tent1 = new StaticAnimatedObject("tent1", tent1_location, "resources/objects/objects/tent1.png", 1, 57, 36, false);
		addToContent(tent1);

		ScreenPoint tent2_location = new ScreenPoint(1320, 750);
		tent2 = new StaticAnimatedObject("tent2", tent2_location, "resources/objects/objects/tent2.png", 1, 57, 36, true);
		addToContent(tent2);

		ScreenPoint tent3_location = new ScreenPoint(1400, 690);
		tent3 = new StaticAnimatedObject("tent3", tent3_location, "resources/objects/objects/tent4.png", 1, 36, 51, false);
		addToContent(tent3);

		ScreenPoint campfire1_location = new ScreenPoint(1400, 720);
		campfire1 = new StaticAnimatedObject("campfire1", campfire1_location, "resources/objects/objects/campfire1.png", 6, 32, 64, false);
		addToContent(campfire1);

		ScreenPoint tent4_location = new ScreenPoint(450, 400);
		tent4 = new StaticAnimatedObject("tent4", tent4_location, "resources/objects/objects/tent1.png", 1, 57, 36, false);
		addToContent(tent4);

		ScreenPoint tent5_location = new ScreenPoint(320, 400);
		tent5 = new StaticAnimatedObject("tent5", tent5_location, "resources/objects/objects/tent2.png", 1, 57, 36, true);
		addToContent(tent5);

		ScreenPoint tent6_location = new ScreenPoint(400, 340);
		tent6 = new StaticAnimatedObject("tent6", tent6_location, "resources/objects/objects/tent4.png", 1, 36, 51, false);
		addToContent(tent6);

		ScreenPoint campfire2_location = new ScreenPoint(400, 400);
		campfire2 = new StaticAnimatedObject("campfire2", campfire2_location, "resources/objects/objects/campfire2.png", 6, 32, 64, false);
		addToContent(campfire2);
		
		

		addPendingObjects();


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

	public WaveStatus waveStatus() {
		return waveStatus;
	}

	public StartWaveButton startWaveButton() {
		return startWaveButton;
	}

	public SlowDownButton slowDownButton() {
		return slowDownButton;
	}

	public GameControl gameControl() {
		return gameControl;
	}

}
