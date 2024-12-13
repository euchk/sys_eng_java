package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import base.ShapeListener;
import shapes.Image;
import ui_elements.ScreenPoint;

//TODO
//Decide if you want to implemet the ShapeListener interface to handle drag and maouse events.
//If so, add it to the class definition and implement the methods you want.
public class MyCharacter implements ShapeListener, Intersectable {
	
	private ScreenPoint location;
	private final String[] images = {"resources/tower.png"};

	private final int[] imageWidth = {70};
	private final int[] imageHeight = {130};

	private int imageIndex = 0;
	private final String imageID;

	

public MyCharacter(ScreenPoint startLocation, String id) {
	setLocation(startLocation);
	this.imageID = id;
}

public void addToCanvas() {
		GameCanvas canvas = Game.UI().canvas();
		Image image = new Image(getImageID(), getImageName(), getImageWidth(),getImageHeight(), location.x, location.y);
		image.setShapeListener(this);
		image.setzOrder(3);
		canvas.addShape(image);
	}

	public ScreenPoint getLocation() {
		return this.location;
	}
	
	public void setLocation(ScreenPoint location) {
		this.location = location;
	}
	
	public String getImageID() {
		return this.imageID;
	}

	public String getImageName() {
		return images[imageIndex];
	}

	private int getImageWidth() {
		return imageWidth[imageIndex];
	}
	
	private int getImageHeight() {
		return imageHeight[imageIndex];
	}

	@Override
	public ScreenPoint[] getIntersectionVertices() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getIntersectionVertices'");
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shapeMoved'");
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shapeStartDrag'");
	}

	@Override
	public void shapeEndDrag(String shapeID) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shapeEndDrag'");
	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shapeClicked'");
	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'shapeRightClicked'");
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseEnterShape'");
	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'mouseExitShape'");
	}

	
}	

