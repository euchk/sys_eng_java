package my_game;

import ui_elements.ScreenPoint;

public abstract class GameObject {
    // protected String id;
    // protected ScreenPoint location;

    public GameObject() {

    }
    
    // public GameObject(String id) {
    //     this.id = id;
    // }

    // public String getId() {
    //     return id;
    // }

    // public void setLocation(int x, int y) {
    //     this.location.x = x;
    //     this.location.y = y;
    // }

    public abstract String getId();
    
    public abstract boolean isActive();

    public abstract void gameStep(); // Enforces logic per game tick

    public abstract void addToCanvas(); // Adds object to canvas

    public abstract void removeFromCanvas(); // Removes object from canvas
}
