package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Rat extends Invader {
    
    public Rat(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(startLocation, id, direction, action, 70);
        setSpeed(7);
        initializeMappings();
        updateAnimation();
    }

    protected void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/rat/U_Special.png");
        idlePaths.put(Direction.DOWN, "resources/objects/rat/D_Special.png");
        idlePaths.put(Direction.LEFT, "resources/objects/rat/S_Special.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/rat/S_Special.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 6); // 6 frames in idle animation
        idleFrames.put(Direction.DOWN, 6);
        idleFrames.put(Direction.LEFT, 6);
        idleFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.IDLE, idleFrames);

        // ATTACK mappings
        Map<Direction, String> attackPaths = new HashMap<>();
        attackPaths.put(Direction.UP, "resources/objects/rat/U_Run.png");
        attackPaths.put(Direction.DOWN, "resources/objects/rat/D_Run.png");
        attackPaths.put(Direction.LEFT, "resources/objects/rat/S_Run.png");
        attackPaths.put(Direction.RIGHT, "resources/objects/rat/S_Run.png");
        spritePaths.put(Action.ATTACK, attackPaths);

        Map<Direction, Integer> attackFrames = new HashMap<>();
        attackFrames.put(Direction.UP, 6); // 6 frames in idle animation
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }
}

