package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public abstract class Defender extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame

    private int attackRange; // Defined in subclass
    private int damage; // Defined in subclass
    
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
    
    public Defender(ScreenPoint startLocation, String id, Direction direction, Action action) {
        super(id, startLocation, FRAME_WIDTH, FRAME_HEIGHT, direction, action);
        setShowHealthBar(false); // defender
        initializeMappings();
        updateAnimation();
    }

    // Subclass should map the currect sprite for action and direction
    protected abstract void initializeMappings();

    protected void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
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
        Arrow arrow = new Arrow(arrowId, startLocation, target, damage);

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
            if (character instanceof Invader && isInRange(character)) {
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