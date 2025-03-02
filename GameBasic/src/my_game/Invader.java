package my_game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import ui_elements.ScreenPoint;

public abstract class Invader extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private boolean isPassed = false; // Passed the gate
    private Path path;
    private int currentWaypointIndex;
    private double speedMultiplier;
    private int startDelay; // Random delay before movement starts
    private int delayCounter = 0; // Random delay before start moving
    protected int scoreValue; // Score points differ between invaders

    
    public Invader(String id, Direction direction, Action action, int FRAME_HEIGHT, int FRAME_WIDTH, Path path) {
        super(id, path.getWaypoint(0), FRAME_WIDTH, FRAME_HEIGHT, direction, action);
        this.path = path;
        this.currentWaypointIndex = 0;

        Random random = new Random();
        this.speedMultiplier = 0.85 + (random.nextDouble() * 0.3); // Speed variation factor
        this.startDelay = random.nextInt(5); // Random delay before movement starts

        setShowHealthBar(true);
        initializeMappings();
        setDirection(determineDirection(path.getWaypoint(0), path.getWaypoint(1))); // Initial direction
        // updateAnimation(); // setDirection already updates animation
    }

    protected abstract void initializeMappings();

    public int getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    private void setisPassed() {
        this.isPassed = true;
        setAction(Action.IDLE);
        deactivate();
    }
    
    public boolean getisPassed() {
        return isPassed;
    }

    @Override
    public void gameStep() {
        // Apply start delay
        if (delayCounter < startDelay) {
            delayCounter++;
            return;
        }
        if (currentWaypointIndex < path.getPathLength()) {
            ScreenPoint currentWaypoint = path.getWaypoint(currentWaypointIndex);
            ScreenPoint currentPosition = getLocation();

            int deltaX = currentWaypoint.x - currentPosition.x;
            int deltaY = currentWaypoint.y - currentPosition.y;
            
            double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
            double normX = (distance > 0) ? (deltaX / distance) * speed * speedMultiplier : 0;
            double normY = (distance > 0) ? (deltaY / distance) * speed * speedMultiplier : 0;

            move((int) normX, (int) normY);

            // Check if the invader has reached the current waypoint
            if (Math.abs(deltaX) <= speed && Math.abs(deltaY) <= speed) {
                setLocation(currentWaypoint.x, currentWaypoint.y);
                currentWaypointIndex++;
                
                if (currentWaypointIndex < path.getPathLength()) {
                    setDirection(determineDirection(currentWaypoint, path.getWaypoint(currentWaypointIndex)));
                }
            }
        } else {
            setisPassed();
        }

        nextFrame();
    }
    
    private Direction determineDirection(ScreenPoint from, ScreenPoint to) {
        int deltaX = to.x - from.x;
        int deltaY = to.y - from.y;

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? Direction.RIGHT : Direction.LEFT;
        } else {
            return (deltaY > 0) ? Direction.DOWN : Direction.UP;
        }
    }

    
    @Override
    public void updateAnimation() {
        String spritePath = spritePaths.get(action).get(direction);
        int totalFrames = frameCounts.get(action).get(direction);
        setAnimation(spritePath, totalFrames);
    }

}

