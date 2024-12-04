package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import shapes.Image;
import ui_elements.ScreenPoint;

//TODO
//Decide if you want to implemet the ShapeListener interface to handle drag and maouse events.
//If so, add it to the class definition and implement the methods you want.
public class MyCharacter implements ShapeListener{

	public enum Direction {
		RIGHT(0),
		LEFT(1);
	
		private final int index;
	
		Direction(int index) {
			this.index = index;
		}
	
		public int getIndex() {
			return index;
		}
	}
	
	private Direction direction;
	private ScreenPoint location;
	private String imageID;
	private String equipment;
	
	//TODO
	//Add your character properties

	private final int imageWidth = 80;
	private final int imageHeight = 80;

	private final String[][] weaponIdleFrames = {
												{"resources/louisEX_idle0.png", 
												"resources/louisEX_idle1.png",
												"resources/louisEX_idle2.png",
												"resources/louisEX_idle3.png",
												"resources/louisEX_idle2.png",
												"resources/louisEX_idle1.png"}, // Right
												{"resources/louisEX_idle_mirror0.png", 
												"resources/louisEX_idle_mirror1.png",
												"resources/louisEX_idle_mirror2.png",
												"resources/louisEX_idle_mirror3.png",
												"resources/louisEX_idle_mirror2.png",
												"resources/louisEX_idle_mirror1.png"} // Left
												};

	private final String[][] weaponBoostFrames = {
												{"resources/louisEX_boost0.png", 
												"resources/louisEX_boost1.png",
												"resources/louisEX_boost2.png",
												"resources/louisEX_boost3.png",
												"resources/louisEX_boost4.png",
												"resources/louisEX_boost5.png"}, // Right
												{"resources/louisEX_boost_mirror0.png", 
												"resources/louisEX_boost_mirror1.png",
												"resources/louisEX_boost_mirror2.png",
												"resources/louisEX_boost_mirror3.png",
												"resources/louisEX_boost_mirror2.png",
												"resources/louisEX_boost_mirror1.png"} // Left
												};												

	private final String[][] armorIdleFrames = {
												{"resources/louis_idle0.png", 
												"resources/louis_idle1.png",
												"resources/louis_idle2.png",
												"resources/louis_idle3.png",
												"resources/louis_idle2.png",
												"resources/louis_idle1.png"}, // Right
												{"resources/louis_idle_mirror0.png", 
												"resources/louis_idle_mirror1.png",
												"resources/louis_idle_mirror2.png",
												"resources/louis_idle_mirror3.png",
												"resources/louis_idle_mirror2.png",
												"resources/louis_idle_mirror1.png"} // Left
												};											

	private boolean animation = true;
	private boolean boost = false; // Enables boost animation once
	private String[] currentFrames; // Current frames being used for animation
	private int currentFrameIndex;
	

	public MyCharacter(ScreenPoint startLocation, String id) {
        this.location = startLocation;
        this.imageID = id;
		currentFrameIndex = 0;
		equipment = "Armor";
		setDirection(Direction.RIGHT);
		setIdleAnimation();
    }

	public ScreenPoint getLocation() {
		return this.location;
	}
	
	public void setLocation(ScreenPoint location) {
		this.location = location;
	}
	
	public void setImageID(String id) {
		this.imageID = id;
	}
	
	public String getImageID() {
		return this.imageID;
	}

	public String getImageName() {
		return currentFrames[currentFrameIndex];
	}

	public int getImageIndex() {
		return currentFrameIndex;
	}

	private int getImageWidth() {
		return imageWidth;
	}
	
	private int getImageHeight() {
		return imageHeight;
	}

	public void addToCanvas() {
		GameCanvas canvas = Game.UI().canvas();
		//TODO
		//Create the character's graphical elements and add them to the canvas
		Image image = new Image(getImageID(), getImageName(), getImageWidth(),getImageHeight(), location.x, location.y);
		image.setShapeListener(this);
		image.setzOrder(3);
		canvas.addShape(image);
	}

	//Add setters, getters and other methods that you need for your character

	// Set the animation according to the combo box option
	public void setEquipment(String equipment){
		if (equipment == "Armor" || equipment == "Weapon"){
			this.equipment = equipment;
			setIdleAnimation();
		} else {
			throw new IllegalArgumentException("Only Armor or Weapon can be set for equipment");
		}
	}

	public void setDirection(Direction direction){
		this.direction = direction;
		setIdleAnimation();
	}

	public void stopAnimation(){
		animation = false;
	}

	public void startAnimation(){
		animation = true;
	}

	// Change the character's position to a random location
	public void changePosition(){
		ScreenPoint newLocation;
		int x, y;

		// Generate random location on the canvas
		x = (int) (Math.random() * 700); // Size to fit in the screen
		y = (int) (Math.random() * 700);
				
		// Set new location
		newLocation = new ScreenPoint(x, y);
		setLocation(newLocation);

		// Redraw the character on canvas after changing position
		Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
	}

	public void moveLocation(int dx, int dy) {
		this.location.x += dx;
		this.location.y += dy;

		// Redraw the character on canvas after changing location
		Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
	}

	// Set the character's idle animation according to the equipement
	public void setIdleAnimation() {
		if(this.equipment == "Armor"){
			currentFrames = armorIdleFrames[direction.getIndex()];
			setImage(currentFrames[currentFrameIndex]);
		}
		else if(this.equipment == "Weapon"){
			currentFrames = weaponIdleFrames[direction.getIndex()];
			setImage(currentFrames[currentFrameIndex]);
		}
	}

		// Activate boost animation once
	public void setBoostAnimation(){
		boost = true;
		currentFrames = weaponBoostFrames[direction.getIndex()];
		setImage(currentFrames[currentFrameIndex]);
	}
    
	// Change image to the next frame
	public void nextFrame() {
        if (animation == false) return;

        currentFrameIndex = (currentFrameIndex + 1) % currentFrames.length;
		setImage(currentFrames[currentFrameIndex]);

		// If animating boost sequence do it once and return to idle
		if(boost == true && currentFrameIndex == currentFrames.length - 1){
			boost = false;
			setIdleAnimation();
		}
		
    }
	
	public void setImage(String frame) {
		GameCanvas canvas = Game.UI().canvas();
		if (canvas.getShape(imageID) != null) {
		canvas.changeImage(imageID, frame, getImageWidth(), getImageHeight());
		}
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		moveLocation(dx, dy);
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		stopAnimation();
	}

	@Override
	public void shapeEndDrag(String shapeID) {
		startAnimation();
	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		;

	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		;
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		;
	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		;
	}
	
}	

