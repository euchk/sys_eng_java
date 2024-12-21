package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Pokimon;
import my_game.Character;
import my_game.Character.Direction;
import my_game.Character.Action;
import my_game.Knight;
import my_game.Archer;
import ui_elements.ScreenPoint;
import base.GameContent;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	private Archer archer;
	private Knight knight1, knight2;
	
	private HashMap<String, Character> characters; // Store all characters with id as key

    public MyContent() {
		characters = new HashMap<>();
    }

    // Add and remove a character from the list
    public void addCharacter(Character character) {
		characters.put(character.getId(), character);
    }

	// Currently after removing character it cannot be added because id is lost
	public void removeCharacter(Character character) {
		characters.remove(character.getId());
    }

	// Retrieve a character by its ID
    public Character getCharacterById(String id) {
        return characters.get(id);
    }

	// Allow iteration on all characters
	public Collection<Character> getAllCharacters() {
        return characters.values();
    }
	
	@Override
	public void initContent() {
		pokimon = new Pokimon();

		archer = new Archer(new ScreenPoint(600, 500), "archer1", Direction.LEFT, Action.IDLE);
		addCharacter(archer);
	
		knight1 = new Knight(new ScreenPoint(700, 200), "knight1", Direction.RIGHT, Action.IDLE);
		addCharacter(knight1);

		knight2 = new Knight(new ScreenPoint(500, 200), "knight2", Direction.LEFT, Action.IDLE);
		addCharacter(knight2);
	

		ScreenPoint[] points = {
			new ScreenPoint(100, 100),
			new ScreenPoint(130, 50),
			new ScreenPoint(170, 50),
			new ScreenPoint(200, 100),
			new ScreenPoint(220, 170),
			new ScreenPoint(170, 150),
			new ScreenPoint(130, 150)
		};

		myPolygon = new MyPolygon(points);
	}	
	
	public Pokimon pokimon() {
		return pokimon;
	}

	public MyPolygon polygon() {
		return myPolygon;
	}
	
}
