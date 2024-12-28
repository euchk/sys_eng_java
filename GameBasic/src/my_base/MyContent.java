package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Character;
import my_game.Character.Direction;
import my_game.GameControl;
import my_game.Character.Action;
import my_game.Knight;
import my_game.Archer;
import my_game.Arrow;
import ui_elements.ScreenPoint;
import base.GameContent;

public class MyContent extends GameContent{
	private Archer archer1, archer2;
	private Knight knight1, knight2;
	private GameControl gameControl;
	
	private HashMap<String, Character> characters; // Store all characters with id as key
	private HashMap<String, Arrow> arrows; 		   // Store all arrows with id as key
	
	public MyContent() {
		characters = new HashMap<>();
		arrows = new HashMap<>();
    }

	@Override
	public void initContent() {

		archer1 = new Archer(new ScreenPoint(500, 500), "archer1", Direction.DOWN, Action.IDLE);
		addCharacter(archer1);

		archer2 = new Archer(new ScreenPoint(700, 500), "archer2", Direction.DOWN, Action.IDLE);
		addCharacter(archer2);
	
		knight1 = new Knight(new ScreenPoint(700, 200), "knight1", Direction.RIGHT, Action.ATTACK);
		addCharacter(knight1);

		knight2 = new Knight(new ScreenPoint(500, 100), "knight2", Direction.LEFT, Action.IDLE);
		addCharacter(knight2);

		gameControl = new GameControl(this);
	}

    // Add and remove a character from the list
    public void addCharacter(Character character) {
		characters.put(character.getId(), character);
    }

	// Allow iteration on all characters
	public Collection<Character> getAllCharacters() {
        return characters.values();
    }

	// Add and remove an arrow from the list
    public void addArrow(Arrow arrow) {
		arrows.put(arrow.getId(), arrow);
    }

	// Allow iteration on all shapes
	public Collection<Arrow> getAllArrows() {
        return arrows.values();
    }

	public GameControl gameControl() {
		return this.gameControl;
	}

}
