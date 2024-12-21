package my_base;

import my_game.Pokimon;
import my_game.Character.Action;
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
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.RIGHT);
			  dx = 3;
			  dy = 0;
			  for (Character character : myContent.getAllCharacters()) {
				character.setDirection(Character.Direction.RIGHT);
			  }
			  ((DirectionCombo) (Game.UI().dashboard().getUIElement("directionCombo"))).setDirection("Right");
			  break;
		  case LEFT:
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.LEFT);
			  dx = -3;
			  dy = 0;
			  for (Character character : myContent.getAllCharacters()) {
				character.setDirection(Character.Direction.LEFT);
			  }
			  ((DirectionCombo) (Game.UI().dashboard().getUIElement("directionCombo"))).setDirection("Left");
			  break;
		  case UP:
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.UP);
			  dx = 0;
			  dy = -3;
			  for (Character character : myContent.getAllCharacters()) {
				character.setDirection(Character.Direction.UP);
			  }
			  break;
		  case DOWN:
			  myContent.pokimon().setDirectionPolicy(Pokimon.Direction.DOWN);
			  dx = 0;
			  dy = 3;
			  for (Character character : myContent.getAllCharacters()) {
				character.setDirection(Character.Direction.DOWN);
			  }
			  break;
		}
		for (Character character : myContent.getAllCharacters()) {
			character.setAction(Action.ATTACK);
			character.move(dx, dy);
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
