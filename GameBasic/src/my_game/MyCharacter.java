package my_game;

import base.Game;
import base.GameCanvas;
import ui_elements.ScreenPoint;

//TODO
//Decide if you want to implemet the ShapeListener interface to handle drag and maouse events.
//If so, add it to the class definition and implement the methods you want.
public class MyCharacter {
	
	private ScreenPoint location;
	private String imageID;
	
	//TODO
	//Add your character properties
	
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

	public void addToCanvas() {
		GameCanvas canvas = Game.UI().canvas();
		//TODO
		//Create the character's graphical elements and add them to the canvas
	}
	
	//TODO
	//Add setters, getters and other methods that you need for your character
	
}	

