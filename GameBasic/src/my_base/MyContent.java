package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Character;
import my_game.GameControl;
import my_game.Arrow;
import my_game.Coins;
import my_game.Score;
import base.GameContent;


public class MyContent extends GameContent{
	private GameControl gameControl;
	private Coins coins;
	private Score score;
	
	private HashMap<String, Character> characters; // Store all characters with id as key
	private HashMap<String, Arrow> arrows; 		   // Store all arrows with id as key
	
	public MyContent() {
		characters = new HashMap<>();
		arrows = new HashMap<>();
    }

	@Override
	public void initContent() {

		score = new Score(5, 70, 20);
		coins = new Coins(300, 70, 100);
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

	// Allow iteration on all arrows
	public Collection<Arrow> getAllArrows() {
        return arrows.values();
    }

	public Coins coins() {
		return coins;
	}

	public Score score() {
		return score;
	}

	public GameControl gameControl() {
		return gameControl;
	}

}
