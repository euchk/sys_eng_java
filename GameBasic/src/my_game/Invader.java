package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public abstract class Invader extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private boolean isPassed = false; // Passed the gate
    
    public Invader(ScreenPoint startLocation, String id, Direction direction, Action action, int FRAME_HEIGHT, int FRAME_WIDTH) {
        super(id, startLocation, FRAME_WIDTH, FRAME_HEIGHT, direction, action);
        setShowHealthBar(true);
        initializeMappings();
        updateAnimation();
    }

    protected abstract void initializeMappings();

    private void setisPassed() {
        this.isPassed = true;
    }
    
    public boolean getisPassed() {
        return isPassed;
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

