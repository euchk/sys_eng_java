package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Sharpshooter extends Defender {

    public Sharpshooter(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(startLocation, id, direction, action);
        setAttackRange(300);
        setDamage(7);
    }

    @Override
    protected void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/sharpshooter/U_Idle.png");
        idlePaths.put(Direction.DOWN, "resources/objects/sharpshooter/D_Idle.png");
        idlePaths.put(Direction.LEFT, "resources/objects/sharpshooter/S_Idle.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/sharpshooter/S_Idle.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 4);
        idleFrames.put(Direction.DOWN, 4);
        idleFrames.put(Direction.LEFT, 4);
        idleFrames.put(Direction.RIGHT, 4);
        frameCounts.put(Action.IDLE, idleFrames);

        // ATTACK mappings
        Map<Direction, String> attackPaths = new HashMap<>();
        attackPaths.put(Direction.UP, "resources/objects/sharpshooter/U_Attack.png");
        attackPaths.put(Direction.DOWN, "resources/objects/sharpshooter/D_Attack.png");
        attackPaths.put(Direction.LEFT, "resources/objects/sharpshooter/S_Attack.png");
        attackPaths.put(Direction.RIGHT, "resources/objects/sharpshooter/S_Attack.png");
        spritePaths.put(Action.ATTACK, attackPaths);

        Map<Direction, Integer> attackFrames = new HashMap<>();
        attackFrames.put(Direction.UP, 6);
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }
}
