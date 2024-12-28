package base;


public abstract class Game {
	protected static GameContent gameContent;
	protected static GameUI gameUI;
	protected static MouseHandler mouseHandler;
	private static KeyboardListener keyboardListener;
	private static PeriodicScheduler scheduler;
	private static AudioPlayer audioPlayer;

	public Game() {
		scheduler = new PeriodicScheduler();
		audioPlayer = new AudioPlayer();
	}

	/*
	 * Make  sure to set the game content and periodic loop before calling initGame, since it is probable
	 * that you will need the content to initialize the UI and the scheduler should know what loop
	 * to run.
	 */
	public void setGameContent(GameContent content) {
		gameContent = content;
	}
	
	public void setPeriodicLoop(PeriodicLoop periodicLoop) {
		scheduler.setPeriodicLoop(periodicLoop);
	}

	// Allow adding tasks to PeriodicLoop 
	public static PeriodicLoop getPeriodicLoop() {
		return scheduler.getPeriodicLoop();
	}

	public void setMouseHandler(MouseHandler myMouseHandler) {
		mouseHandler = myMouseHandler;
	}
		
	public void setKeyboardListener(KeyboardListener myKeyboardListener) {
		keyboardListener = myKeyboardListener;
	}

	public void initGame() {
		initContent();
		initUI();
		if (keyboardListener != null)
			gameUI.frame().addKeyListener(keyboardListener.keyListener);
		scheduler.start();
	}
	
	public void initUI() {
		gameUI = new GameUI("My Game", 2056, 1024);
		initCanvas();
		initDashboard();
		gameUI.setFocusable(true);
		gameUI.setVisible(true);
	}
	
	public static void endGame() {
		scheduler.end();
	}
	
	protected abstract void initCanvas();
	
	protected void initContent() {
		gameContent.initContent();
	}
	
	protected void initDashboard() {
		;
	}
	
	//You can refer to the game UI from anywhere by Game.UI()
	public static GameUI UI() {
		return gameUI;
	}

	
	public static MouseHandler MouseHandler() {
		return mouseHandler;
	}
	
	public static KeyboardListener keybListener() {
		return keyboardListener;
	}
	
	public static GameContent Content() {
		return gameContent;
	}

	public static AudioPlayer audioPlayer() {
		return audioPlayer;
	}

	
}
