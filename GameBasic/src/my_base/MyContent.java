package my_base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import my_game.Pokimon;
import my_game.Character;
import ui_elements.ScreenPoint;
import base.Game;
import base.GameCanvas;
import base.GameContent;
import my_game.MyPolygon;
import my_game.Character;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	private Character archer;
	private Character knight;
	private Character tower;
	
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

	public Collection<Character> getAllCharacters() {
        return characters.values();
    }
	
	@Override
	public void initContent() {
		pokimon = new Pokimon();

		archer = new Character(new ScreenPoint(600, 500), "archer", 
							"resources/objects/archer/D_Idle.png", 
							48, 48, 4);
		addCharacter(archer);
		
		knight = new Character(new ScreenPoint(400, 500), "knight", 
							"resources/objects/knight/S_Attack.png", 
							96, 96, 6);
		addCharacter(knight);

		tower = new Character(new ScreenPoint(200, 500), "tower", 
							"resources/objects/archer_tower/4.png", 
							70, 130, 6);
		addCharacter(tower);
		

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

	public Character knight() {
		return knight;
	}

	public MyPolygon polygon() {
		return myPolygon;
	}
	
}
