package my_base;

import java.util.Collection;
import java.util.HashMap;

import my_game.Pokimon;
import my_game.Character;
import my_game.Character.Direction;
import ui_elements.ScreenPoint;
import base.GameContent;
import my_game.MyPolygon;

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

	// Allow iteration on all characters
	public Collection<Character> getAllCharacters() {
        return characters.values();
    }
	
	@Override
	public void initContent() {
		pokimon = new Pokimon();

		String[] knightSpriteSheets = {
            "resources/objects/knight/U_Run.png",
            "resources/objects/knight/D_Run.png",
			"resources/objects/knight/S_Run.png",
			"resources/objects/knight/S_Run.png",
			"resources/objects/knight/U_Attack.png",
            "resources/objects/knight/D_Attack.png",
			"resources/objects/knight/S_Attack.png",
			"resources/objects/knight/S_Attack.png"
        };

		String[] archerSpriteSheets = {
            "resources/objects/archer/U_Idle.png",
            "resources/objects/archer/D_Idle.png",
			"resources/objects/archer/S_Idle.png",
			"resources/objects/archer/S_Idle.png",
			"resources/objects/archer/U_Attack.png",
            "resources/objects/archer/D_Attack.png",
			"resources/objects/archer/S_Attack.png",
			"resources/objects/archer/S_Attack.png"
        };

		String[] towerSpriteSheets = {
            "resources/objects/archer_tower/4.png"
        };

		archer = new Character(new ScreenPoint(600, 500), "archer",
							archerSpriteSheets, 48, 48, 
							4, Direction.DOWN);
		addCharacter(archer);
		
		knight = new Character(new ScreenPoint(400, 500), "knight", 
							knightSpriteSheets, 96, 96, 
							6, Direction.RIGHT);
		addCharacter(knight);

		// tower = new Character(new ScreenPoint(200, 500), "tower", 
		// 					towerSpriteSheets, 70, 130, 
		// 					6, Direction.UP);
		// addCharacter(tower);
		

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
