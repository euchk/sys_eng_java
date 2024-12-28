package my_base;

import my_game.Knight;
import my_game.Character;
import my_ui_elements.DirectionCombo;

import java.awt.event.KeyEvent;

import base.Game;
import base.KeyboardListener;

public class MyKeyboardListener extends KeyboardListener{

	private MyContent myContent;
	private int dx, dy;
	
	public MyKeyboardListener() {
		super();
		myContent = (MyContent) this.content;
	}

	@Override
	public void directionalKeyPressed(Direction direction) {
		switch (direction) {
		  case RIGHT:
			  dx = 10;
			  dy = 0;
			  for (Character character : myContent.getAllCharacters()) {
				if (character instanceof Knight){
					character.setDirection(Character.Direction.RIGHT);
				}
			  }
			  ((DirectionCombo) (Game.UI().dashboard().getUIElement("directionCombo"))).setDirection("Right");
			  break;
		  case LEFT:
			  dx = -10;
			  dy = 0;
			  for (Character character : myContent.getAllCharacters()) {
				if (character instanceof Knight){
					character.setDirection(Character.Direction.LEFT);
				}
			  }
			  ((DirectionCombo) (Game.UI().dashboard().getUIElement("directionCombo"))).setDirection("Left");
			  break;
		  case UP:
			  dx = 0;
			  dy = -10;
			  for (Character character : myContent.getAllCharacters()) {
				if (character instanceof Knight){
					character.setDirection(Character.Direction.UP);
				}
			  }
			  break;
		  case DOWN:
			  dx = 0;
			  dy = 10;
			  for (Character character : myContent.getAllCharacters()) {
				if (character instanceof Knight){
					character.setDirection(Character.Direction.DOWN);
				}
			  }
			  break;
		}
		for (Character character : myContent.getAllCharacters()) {
			if (character instanceof Knight){
				character.move(dx, dy);
				break;
			}
			
		  }
	}
	
	@Override
	public void characterTyped(char c) {
		System.out.println("key character = '" + c + "'" + " pressed.");
	}
	
	@Override
	public void enterKeyPressed() {
		System.out.println("enter key pressed.");
	}
	
	@Override
	public void backSpaceKeyPressed() {
		System.out.println("backSpace key pressed.");
	}
	
	@Override
	public void spaceKeyPressed() {
		System.out.println("space key pressed.");
	}
	
	public void otherKeyPressed(KeyEvent e) {
		System.out.println("other key pressed. type:" + e);
	}
}
