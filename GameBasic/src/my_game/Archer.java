package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public class Archer extends Character {

    private final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    private final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame

    private int attackRange = 200;
    
    // Offset for arrow positioning depending on direction
    public ScreenPoint getBowOffset() {
        switch (direction) {
            case UP:
                return new ScreenPoint(20, 20); 
            case DOWN:
                return new ScreenPoint(20, 20);
            case LEFT:
                return new ScreenPoint(0, 17);
            case RIGHT:
                return new ScreenPoint(20, 20);
            default:
                return new ScreenPoint(0, 0);
        }
    }
    
    public Archer(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(id, startLocation, FRAME_WIDTH, FRAME_HEIGHT, direction, action, 80);
        initializeMappings();
        updateAnimation();
    }

    // Map the currect sprite for action and direction
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

    private void shootArrow(Character target) {
        // set ATTACK only if starting from IDLE to avoid reseting animation
        if(action != Action.ATTACK){
            setAction(Action.ATTACK);
        }
        
        // Shoot only at 4th frame of ATTACK animation
        if (animatedImage.getCurrentFrame() != 4) return;
               
        // Calibrate arrow position
        ScreenPoint bowOffset = getBowOffset();
        ScreenPoint startLocation = new ScreenPoint(getLocation().x + bowOffset.x, 
                                                    getLocation().y + bowOffset.y);
        
        // Update the archer's direction based on the target location
        updateDirection(target.getCenterLocation());
        
        // Create an arrow instance
        String arrowId = "arrow_" + System.currentTimeMillis(); // Unique ID for each arrow
        Arrow arrow = new Arrow(arrowId, startLocation, target);

        // Add arrow to the game
        content.addArrow(arrow);
        arrow.addToCanvas();
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

    private boolean isInRange(Character target) {
        double distance = Math.sqrt(Math.pow(target.getLocation().x - getLocation().x, 2) +
                                    Math.pow(target.getLocation().y - getLocation().y, 2));
        return distance <= attackRange;
    }

    private Character getNearestTarget(){
        // Find the nearest target in range
        Character nearestTarget = null;
        double minDistance = Double.MAX_VALUE;

        for (Character character : content.getAllCharacters()) {
            if (character instanceof Knight && isInRange(character)) {
                double distance = Math.sqrt(Math.pow(character.getLocation().x - getLocation().x, 2) +
                                            Math.pow(character.getLocation().y - getLocation().y, 2));
                // Stay with current target if it's health is lower
                if (distance < minDistance || (distance == minDistance && character.getHealth() < nearestTarget.getHealth())) {
                    minDistance = distance;
                    nearestTarget = character;
                }
            }
        }
        return nearestTarget;
    }

    @Override
    public void updateAnimation() {
        String spritePath = spritePaths.get(action).get(direction);
        int totalFrames = frameCounts.get(action).get(direction);
        setAnimation(spritePath, totalFrames);
    }

    @Override
    public void gameStep() {
        Character nearestTarget = getNearestTarget();
        
        // Shoot arrow if there is a target in range
        if (nearestTarget != null) {
            shootArrow(nearestTarget);
        }else{
            // Return to IDLE without reseting animation
            if(action != Action.IDLE){
                setAction(Action.IDLE);
            }
        }
        nextFrame();
    }

}