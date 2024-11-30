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
	
	private ScreenPoint location;
	private String imageID;
	private String equipment;
	
	//TODO
	//Add your character properties

	private final int imageWidth = 80;
	private final int imageHeight = 80;

	private final String[] weaponIdleFrames = {"resources/louisEX_idle0.png", 
											"resources/louisEX_idle1.png",
											"resources/louisEX_idle2.png",
											"resources/louisEX_idle3.png",
											"resources/louisEX_idle2.png",
											"resources/louisEX_idle1.png"};

	private final String[] armorIdleFrames = {"resources/louis_idle0.png", 
											"resources/louis_idle1.png",
											"resources/louis_idle2.png",
											"resources/louis_idle3.png",
											"resources/louis_idle2.png",
											"resources/louis_idle1.png"};

	private boolean animation = true;
	private String[] currentFrames; // Current frames being used for animation
	private int currentFrameIndex;
	

	public MyCharacter(ScreenPoint startLocation, String id) {
        this.location = startLocation;
        this.imageID = id;
		currentFrameIndex = 0;
		equipment = "Armor";
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

	//TODO
	//Add setters, getters and other methods that you need for your character

	public void setEquipment(String equipment){
		if (equipment == "Armor" || equipment == "Weapon"){
			this.equipment = equipment;
			setIdleAnimation();
		} else {
			throw new IllegalArgumentException("Only Armor or Weapon can be set for equipment");
		}
	}

	public void stopAnimation(){
		animation = false;
	}

	public void startAnimation(){
		animation = true;
	}

	// Switches the image of the character to the next image index
	public void switchImage() {
		// setImage(1 - imageIndex);
		// setImage(1 - currentFrameIndex);
		;
	}

	public void moveLocation(int dx, int dy) {
		this.location.x += dx;
		this.location.y += dy;
	}

	public void setIdleAnimation() {
		if(this.equipment == "Armor"){
			currentFrames = armorIdleFrames;
			setImage(currentFrames[currentFrameIndex]);
		}
		else if(this.equipment == "Weapon"){
			currentFrames = weaponIdleFrames;
			setImage(currentFrames[currentFrameIndex]);
		}
	}
    
	public void nextFrame() {
        if (animation == false) return;

        currentFrameIndex = (currentFrameIndex + 1) % currentFrames.length;
		setImage(currentFrames[currentFrameIndex]);
		
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

