package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Archer extends Defender {

    public Archer(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(startLocation, id, direction, action);
        setAttackRange(150);
        setDamage(5);
    }

    @Override
    protected void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/archer/U_Idle.png");
        idlePaths.put(Direction.DOWN, "resources/objects/archer/D_Idle.png");
        idlePaths.put(Direction.LEFT, "resources/objects/archer/S_Idle.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/archer/S_Idle.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 4);
        idleFrames.put(Direction.DOWN, 4);
        idleFrames.put(Direction.LEFT, 4);
        idleFrames.put(Direction.RIGHT, 4);
        frameCounts.put(Action.IDLE, idleFrames);

        // ATTACK mappings
        Map<Direction, String> attackPaths = new HashMap<>();
        attackPaths.put(Direction.UP, "resources/objects/archer/U_Attack.png");
        attackPaths.put(Direction.DOWN, "resources/objects/archer/D_Attack.png");
        attackPaths.put(Direction.LEFT, "resources/objects/archer/S_Attack.png");
        attackPaths.put(Direction.RIGHT, "resources/objects/archer/S_Attack.png");
        spritePaths.put(Action.ATTACK, attackPaths);

        Map<Direction, Integer> attackFrames = new HashMap<>();
        attackFrames.put(Direction.UP, 6);
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }
}
