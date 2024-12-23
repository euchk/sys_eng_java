package my_game;

import java.util.HashMap;
import java.util.Map;

import base.Game;
import my_base.MyPeriodicLoop;
import ui_elements.ScreenPoint;

public class Archer extends Character {

    private final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    private final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame
    
    public ScreenPoint getBowOffset() {
        switch (direction) {
            case UP:
                return new ScreenPoint(20, 20); // Adjust as needed for the bow's position in the sprite
            case DOWN:
                return new ScreenPoint(20, 20);
            case LEFT:
                return new ScreenPoint(0, 17);
            case RIGHT:
                return new ScreenPoint(20, 15);
            default:
                return new ScreenPoint(0, 0);
        }
    }
    

    public Archer(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(id, startLocation, FRAME_WIDTH, FRAME_HEIGHT, direction, action, 80);
        initializeMappings();
        updateAnimation();
    }

    private void initializeMappings() {
        // IDLE mappings
        Map<Direction, String> idlePaths = new HashMap<>();
        idlePaths.put(Direction.UP, "resources/objects/archer/U_Idle.png");
        idlePaths.put(Direction.DOWN, "resources/objects/archer/D_Idle.png");
        idlePaths.put(Direction.LEFT, "resources/objects/archer/S_Idle.png");
        idlePaths.put(Direction.RIGHT, "resources/objects/archer/S_Idle.png");
        spritePaths.put(Action.IDLE, idlePaths);

        Map<Direction, Integer> idleFrames = new HashMap<>();
        idleFrames.put(Direction.UP, 4); // 4 frames in idle animation
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
        attackFrames.put(Direction.UP, 6); // 6 frames in idle animation
        attackFrames.put(Direction.DOWN, 6);
        attackFrames.put(Direction.LEFT, 6);
        attackFrames.put(Direction.RIGHT, 6);
        frameCounts.put(Action.ATTACK, attackFrames);
    }

    // @Override
    // public void periodicUpdate() { // Archer is a static character
    //     nextFrame();
    // }

    @Override
    public void periodicUpdate() {
        // Handle attack animation and arrow shooting
        if (action == Action.ATTACK) {
            if (animatedImage.getCurrentFrame() == 3) { // Midpoint of animation
                shootArrow(new ScreenPoint(700, 500));
            }
        }
        nextFrame();
    }

    public void shootArrow(ScreenPoint targetLocation) {
        // Starting point for the arrow
        ScreenPoint archerLocation  = new ScreenPoint(getLocation().x, getLocation().y);
        
        // Update the archer's direction based on the target location
        updateDirection(targetLocation);
        
        // Create an arrow instance
        ScreenPoint bowOffset = getBowOffset();
        String arrowId = "arrow_" + System.currentTimeMillis(); // Unique ID for each arrow
        Arrow arrow = new Arrow(arrowId, archerLocation, bowOffset, targetLocation);

         // Add arrow to the canvas
        Game.UI().canvas().addShape(arrow);

        // Track the arrow in the periodic loop for movement
        MyPeriodicLoop myPeriodicLoop = (MyPeriodicLoop) Game.getPeriodicLoop();
        myPeriodicLoop.addTask(() -> {
            arrow.move();
            if (!arrow.isActive()) {
                Game.UI().canvas().deleteShape(arrow.getId());
            }
        });

        // Play attack animation while shooting arrows
        setAction(Action.ATTACK);
    }

    private void updateDirection(ScreenPoint targetLocation) {
        ScreenPoint archerLocation = getLocation();
        int dx = targetLocation.x - archerLocation.x;
        int dy = targetLocation.y - archerLocation.y;
    
        // Determine the primary direction
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) {
                setDirection(Direction.RIGHT); // Target is to the right
            } else {
                setDirection(Direction.LEFT);  // Target is to the left
            }
        } else {
            if (dy > 0) {
                setDirection(Direction.DOWN); // Target is below
            } else {
                setDirection(Direction.UP);   // Target is above
            }
        }
    }


    @Override
    public void updateAnimation() {
        String spritePath = spritePaths.get(action).get(direction);
        int totalFrames = frameCounts.get(action).get(direction);
        setAnimation(spritePath, totalFrames);
    }

}
