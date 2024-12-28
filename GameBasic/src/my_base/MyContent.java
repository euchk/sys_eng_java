package my_base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import my_game.Pokimon;
import my_game.Character;
import my_game.Character.Direction;
import my_game.Character.Action;
import my_game.Knight;
import my_game.Archer;
import my_game.Arrow;
import ui_elements.ScreenPoint;
import base.GameContent;
import my_game.MyPolygon;

public class MyContent extends GameContent{
	private Pokimon pokimon;
	private MyPolygon myPolygon;
	private Archer archer1, archer2;
	private Knight knight1, knight2;
	
	private HashMap<String, Character> characters; // Store all characters with id as key
	private HashMap<String, Arrow> arrows; 		   // Store all arrows with id as key

    public MyContent() {
		characters = new HashMap<>();
		arrows = new HashMap<>();
    }

	

    // Add and remove a character from the list
    public void addCharacter(Character character) {
		characters.put(character.getId(), character);
		// character.addToCanvas();
    }

	// Allow iteration on all characters
	public Collection<Character> getAllCharacters() {
        return characters.values();
    }
	
	// Safely removes inactivated characters
	public void removeInactivatedCharacters(){
		Iterator<Character> iterator = getAllCharacters().iterator();
		while (iterator.hasNext()) {
			Character character = iterator.next();
			if (!character.isActive()) {
				// character.removeFromCanvas(); // Custom cleanup logic
				iterator.remove(); // Remove the character safely
			}
		}
	}
	
	// Currently after removing character it cannot be added because id is lost
	// public void removeCharacter(String id) {
	// 	characters.remove(id);
    // }

	// Retrieve a character by its ID
    // public Character getCharacterById(String id) {
    //     return characters.get(id);
    // }

	// Add and remove an arrow from the list
    public void addArrow(Arrow arrow) {
		arrows.put(arrow.getId(), arrow);
    }

	// Allow iteration on all shapes
	public Collection<Arrow> getAllArrows() {
        return arrows.values();
    }
	
	// Safely removes inactivated arrows
	public void removeInactivatedArrows(){
		Iterator<Arrow> iterator = getAllArrows().iterator();
		while (iterator.hasNext()) {
			Arrow arrow = iterator.next();
			if (!arrow.isActive()) {
				arrow.removeFromCanvas(); // Custom cleanup logic
				iterator.remove(); // Remove the character safely
			}
		}
	}


	// public void removeArrow(Arrow arrow) {
	// 	arrows.remove(arrow.getId());
    // }

	// // Retrieve a shape by its ID
    // public Arrow getArrowById(String id) {
    //     return arrows.get(id);
    // }

	

	
	
	
	@Override
	public void initContent() {
		pokimon = new Pokimon();

		archer1 = new Archer(new ScreenPoint(500, 500), "archer1", Direction.DOWN, Action.IDLE);
		addCharacter(archer1);

		archer2 = new Archer(new ScreenPoint(700, 500), "archer2", Direction.DOWN, Action.IDLE);
		addCharacter(archer2);
	
		knight1 = new Knight(new ScreenPoint(700, 200), "knight1", Direction.RIGHT, Action.ATTACK);
		addCharacter(knight1);

		knight2 = new Knight(new ScreenPoint(500, 100), "knight2", Direction.LEFT, Action.IDLE);
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
