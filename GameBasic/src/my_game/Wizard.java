package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Wizard extends Invader {

    private static final int FRAME_WIDTH = 96;   // Width of each frame
    private static final int FRAME_HEIGHT = 96;  // Height of each frame
    private static final int speed = 4;
    private static final int maxHealth = 100;
    
    public Wizard(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(startLocation, id, direction, action, FRAME_HEIGHT, FRAME_WIDTH);
        setSpeed(speed);
        setMaxHealth(maxHealth);
        initializeMappings();
        updateAnimation();
    }

    protected void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/wizard/U_Special.png");
        idlePaths.put(Direction.DOWN, "resources/objects/wizard/D_Special.png");
        idlePaths.put(Direction.LEFT, "resources/objects/wizard/S_Special.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/wizard/S_Special.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 6); // 6 frames in idle animation
        idleFrames.put(Direction.DOWN, 6);
        idleFrames.put(Direction.LEFT, 6);
        idleFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.IDLE, idleFrames);

        // ATTACK mappings
        Map<Direction, String> attackPaths = new HashMap<>();
        attackPaths.put(Direction.UP, "resources/objects/wizard/U_Fly.png");
        attackPaths.put(Direction.DOWN, "resources/objects/wizard/D_Fly.png");
        attackPaths.put(Direction.LEFT, "resources/objects/wizard/S_Fly.png");
        attackPaths.put(Direction.RIGHT, "resources/objects/wizard/S_Fly.png");
        spritePaths.put(Action.ATTACK, attackPaths);

        Map<Direction, Integer> attackFrames = new HashMap<>();
        attackFrames.put(Direction.UP, 6); // 6 frames in idle animation
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }
}

