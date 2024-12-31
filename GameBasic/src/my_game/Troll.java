package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Troll extends Invader {

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame
    private static final int speed = 2;
    private static final int maxHealth = 300;
    
    public Troll(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(startLocation, id, direction, action, FRAME_HEIGHT, FRAME_WIDTH);
        setSpeed(speed);
        setMaxHealth(maxHealth);
        initializeMappings();
        updateAnimation();
    }

    protected void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/troll/U_Walk.png");
        idlePaths.put(Direction.DOWN, "resources/objects/troll/D_Walk.png");
        idlePaths.put(Direction.LEFT, "resources/objects/troll/S_Walk.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/troll/S_Walk.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 6); // 6 frames in idle animation
        idleFrames.put(Direction.DOWN, 6);
        idleFrames.put(Direction.LEFT, 6);
        idleFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.IDLE, idleFrames);

        // ATTACK mappings
        Map<Direction, String> attackPaths = new HashMap<>();
        attackPaths.put(Direction.UP, "resources/objects/troll/U_Attack.png");
        attackPaths.put(Direction.DOWN, "resources/objects/troll/D_Attack.png");
        attackPaths.put(Direction.LEFT, "resources/objects/troll/S_Attack.png");
        attackPaths.put(Direction.RIGHT, "resources/objects/troll/S_Attack.png");
        spritePaths.put(Action.ATTACK, attackPaths);

        Map<Direction, Integer> attackFrames = new HashMap<>();
        attackFrames.put(Direction.UP, 6); // 6 frames in idle animation
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }
}

