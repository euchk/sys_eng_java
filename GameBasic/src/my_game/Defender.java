package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public abstract class Defender extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame

    private int attackRange;
    private int damage;
    private int arrowFrequency; // Multiplier for cooldown
    private int arrowCooldownFrames = 0; // cooldown time between arrows
    private Invader currentTarget = null; // Locks on a target after first arrow


    
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
        setShowHealthBar(false); // Can't attack defender
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

    protected void setArrowFrequency(int arrowFrequency) {
        this.arrowFrequency = arrowFrequency;
    }

    private void shootArrow(Invader target) {
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
        content.addToContent(arrow);
        arrow.addToCanvas();

         // Set cooldown: arrowFrequency rounds of the IDLE animation
        int idleFrames = frameCounts.get(Action.IDLE).get(direction);
        arrowCooldownFrames = arrowFrequency  * idleFrames;
        
        // Return to IDLE after shooting
        setAction(Action.IDLE);
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

    private boolean isInRange(Invader target) {
        double distance = Math.sqrt(Math.pow(target.getLocation().x - getLocation().x, 2) +
                                    Math.pow(target.getLocation().y - getLocation().y, 2));
        return distance <= attackRange;
    }

    private Invader getNearestTarget(){
        // If there is a target already and it's in range, keep shooting it
        if (currentTarget != null && currentTarget.isActive() && isInRange(currentTarget)) {
            return currentTarget;
        }

        // Find the nearest target in range
        Invader nearestTarget = null;
        double minDistance = Double.MAX_VALUE;

        for (GameObject gameObject : content.getAllGameObjects()) {
            if (gameObject instanceof Invader) {
                Invader invader = (Invader) gameObject;
                if (isInRange(invader)) {
                    double distance = Math.sqrt(Math.pow(invader.getLocation().x - getLocation().x, 2) +
                                                Math.pow(invader.getLocation().y - getLocation().y, 2));
                    // Stay with current target if it's health is lower
                    if (distance < minDistance || (distance == minDistance && invader.getHealth() < nearestTarget.getHealth())) {
                        minDistance = distance;
                        nearestTarget = invader;
                    }
                }
            }
        }
        // Lock on to the new target
        currentTarget = nearestTarget;
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
        // Check if current target is deactivated
        if (currentTarget != null && !currentTarget.isActive()) {
            currentTarget = null;
        }
        
        // If cooldown is active, decrement and remain idle
        if (arrowCooldownFrames > 0) {
            arrowCooldownFrames--;
            if(action != Action.IDLE){
                setAction(Action.IDLE);
            }
            nextFrame();
            return;
        }

        Invader nearestTarget = getNearestTarget();
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