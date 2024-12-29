package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public abstract class Invader extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private static final int FRAME_WIDTH = 96;   // Width of each frame
    private static final int FRAME_HEIGHT = 96;  // Height of each frame

    private boolean isPassed = false; // Passed the gate
    private boolean isKilled = false; // Killed by defender
    private int speed;
    
    public Invader(ScreenPoint startLocation, String id, Direction direction, Action action, int maxHealth) {
        super(id, startLocation, FRAME_WIDTH, FRAME_HEIGHT, direction, action, maxHealth);
        initializeMappings();
        updateAnimation();
    }

    protected void setSpeed(int speed) {
        this.speed = speed;
    }

    protected abstract void initializeMappings();

    private void setisPassed() {
        this.isPassed = true;
    }
    
    public boolean getisPassed() {
        return isPassed;
    }

    private void setIsKilled() {
        this.isKilled = true;
    }
    
    public boolean getIsKilled() {
        return isKilled;
    }

    @Override
    public void gameStep() {
        move(-speed, 0);
        nextFrame();

        if (getHealth() <= 0) {
            setIsKilled();
            deactivate();
        }

        if (getLocation().x <= 0) {
            setisPassed();
            deactivate();
        }
    }
    
    @Override
    public void updateAnimation() {
        String spritePath = spritePaths.get(action).get(direction);
        int totalFrames = frameCounts.get(action).get(direction);
        setAnimation(spritePath, totalFrames);
    }

}

