package my_game;

import ui_elements.ScreenPoint;

// Game Object is an abstract class that each game entity entends (Tower, Arrow, Character)
// This class implements common methods and makes it possible to iterate over all game objects
public abstract class GameObject {
    protected String id;
    protected boolean active;
    protected ScreenPoint location;

    public GameObject(String id, ScreenPoint location) {
        this.id = id;
        this.active = true;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }
    
    public void deactivate() {
        active = false;
        removeFromCanvas();
    }

    public ScreenPoint getLocation() {
        return location;
    }

    public abstract void gameStep(); // Periodic logic

    public abstract void addToCanvas(); // Adds object to canvas

    public abstract void removeFromCanvas(); // Removes object from canvas
}
