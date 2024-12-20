package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import base.IntersectionAlgorithm;
import base.PeriodicLoop;
import base.ShapeListener;
import my_base.MyContent;
import shapes.BufferedAnimatedImage;
import shapes.Image;
import ui_elements.ScreenPoint;

//TODO
//Decide if you want to implemet the ShapeListener interface to handle drag and maouse events.
//If so, add it to the class definition and implement the methods you want.
public class Enemy implements ShapeListener, Intersectable {

	public enum Direction{
		RIGHT (10,0),
		LEFT(-10,0),
		UP (0,-10),
		DOWN(0,10);
		
		private final int xVec, yVec;
		private Direction(int xVec, int yVec) {
			this.xVec = xVec;
			this.yVec = yVec;
		}
		public int xVec() {
			return xVec;
		}
		public int yVec() {
			return yVec;
		}
	}
	
	private ScreenPoint location;
	private final String[] images = {"resources/objects/knight/S_Run.png"};

	private final int[] imageWidth = {96};
	private final int[] imageHeight = {96};

	private int imageIndex = 0;
	private final String imageID;
	public BufferedAnimatedImage animatedImage;

	private Direction directionPolicy = Direction.LEFT;
	private Direction direction = Direction.LEFT;
	private boolean isMoving = true;

	

	public Enemy(ScreenPoint startLocation, String id) {
		setLocation(startLocation);
		this.imageID = id;
		// Create an instance of BufferedAnimatedImage
		animatedImage = new BufferedAnimatedImage(
		getImageName(),    // Path to spritesheet
		getImageWidth(),   // Frame width
		getImageHeight(),  // Frame height
		location.x,        // X position
		location.y,        // Y position
		6                  // Total number of frames
		);
	}


	public void addToCanvas() {
		GameCanvas canvas = Game.UI().canvas();
		animatedImage.setName(getImageID());  // Optional: Set a name for identification
		animatedImage.setLocation(location.x, location.y);
		animatedImage.setOpaque(false);       // Ensure transparency if needed
		// animatedImage.setzOrder(3);           // Set z-order equivalent

		// Add animated image to canvas
		canvas.add(animatedImage);
		int zOrder = 1;
		if (canvas.getComponentCount() > 1) {
			canvas.setComponentZOrder(animatedImage, zOrder); // Put it at the bottom
		}
		canvas.revalidate();  // Refresh the layout
		canvas.repaint();
	}

	public BufferedAnimatedImage animatedImage(){
		return animatedImage;
	}
	public ScreenPoint getLocation() {
		return this.location;
	}
	
	public void setDirectionPolicy(Direction direction) {
		directionPolicy = direction;
	}

	public Direction getDirection() {
		return direction;
	}

	public Direction getPolicy() {
		return directionPolicy;
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

	
	public void move() {
		if (isMoving) {
			// Move according to policy
			ScreenPoint desired = new ScreenPoint(location.x + directionPolicy.xVec(), location.y + directionPolicy.yVec());
			// if move is possible, i.e., maze does not block
			direction = directionPolicy;
			location.x = desired.x;
			location.y = desired.y;
			animatedImage.moveTo(location.x, location.y);
			// After changing the pokimon self location, move also its image in the canvas accordingly.
			// Game.UI().canvas().moveShapeToLocation(imageID, location.x, location.y);
		}
	}

	@Override
	public ScreenPoint[] getIntersectionVertices() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getIntersectionVertices'");
	}

	@Override
	public void shapeMoved(String shapeID, int dx, int dy) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'shapeMoved'");
	}

	@Override
	public void shapeStartDrag(String shapeID) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'shapeStartDrag'");
	}

	@Override
	public void shapeEndDrag(String shapeID) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'shapeEndDrag'");
	}

	@Override
	public void shapeClicked(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'shapeClicked'");
	}

	@Override
	public void shapeRightClicked(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'shapeRightClicked'");
	}

	@Override
	public void mouseEnterShape(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'mouseEnterShape'");
	}

	@Override
	public void mouseExitShape(String shapeID, int x, int y) {
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'mouseExitShape'");
	}

	
}	

