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
	
	//TODO
	//Add your character properties

	private final int[] imageWidth = {320,320};
	private final int[] imageHeight = {360,360};

	private final String[] images = {"resources/louis_s.png", "resources/louisEX_s.png"};

	private int imageIndex = 0;

	public MyCharacter(ScreenPoint startLocation, String id) {
        this.location = startLocation;
        this.imageID = id;
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
		return images[imageIndex];
	}

	public int getImageIndex() {
		return imageIndex;
	}

	private int getImageWidth() {
		return imageWidth[imageIndex];
	}
	
	private int getImageHeight() {
		return imageHeight[imageIndex];
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

	// Switches the image of the character to the next image index
	public void switchImage() {
		setImage(1 - imageIndex);
	}

	public void setImage(int index) {
		this.imageIndex = index;
		Game.UI().canvas().changeImage(imageID, getImageName(), getImageWidth(), getImageHeight());
	}

	public void moveLocation(int dx, int dy) {
		this.location.x += dx;
		this.location.y += dy;
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		moveLocation(dx, dy);
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		;
	}

	@Override
	public void shapeEndDrag(String shapeID) {
		;
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

