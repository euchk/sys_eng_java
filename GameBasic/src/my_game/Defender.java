package my_game;

import base.Game;
import base.GameCanvas;
import base.Intersectable;
import base.ShapeListener;
import shapes.BufferedAnimatedImage;
import ui_elements.ScreenPoint;


public class Defender implements ShapeListener, Intersectable {
	
	private ScreenPoint location;
	private final String[] images = {"resources/objects/archer/S_Attack.png"};

	private final int[] imageWidth = {48};
	private final int[] imageHeight = {48};

	private int imageIndex = 0;
	private final String imageID;
	public BufferedAnimatedImage animatedImage;

	

	public Defender(ScreenPoint startLocation, String id) {
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
		int zOrder = 0;
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

