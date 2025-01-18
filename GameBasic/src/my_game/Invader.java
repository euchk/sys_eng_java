package my_game;

import java.util.HashMap;
import java.util.Map;

import ui_elements.ScreenPoint;

public abstract class Invader extends Character {

    protected final Map<Action, Map<Direction, String>> spritePaths = new HashMap<>();
    protected final Map<Action, Map<Direction, Integer>> frameCounts = new HashMap<>();

    private boolean isPassed = false; // Passed the gate
    private Path path;
    private int currentWaypointIndex;
    
    public Invader(String id, Direction direction, Action action, int FRAME_HEIGHT, int FRAME_WIDTH, Path path) {
        super(id, path.getWaypoint(0), FRAME_WIDTH, FRAME_HEIGHT, direction, action);
        this.path = path;
        this.currentWaypointIndex = 0;
        setShowHealthBar(true);
        initializeMappings();
        updateAnimation();
    }

    protected abstract void initializeMappings();

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
        if (currentWaypointIndex < path.getPathLength()) {
            ScreenPoint currentWaypoint = path.getWaypoint(currentWaypointIndex);
            ScreenPoint currentPosition = getLocation();

            int deltaX = currentWaypoint.x - currentPosition.x;
            int deltaY = currentWaypoint.y - currentPosition.y;

            if (deltaX != 0) {
                move((int)(Math.signum(deltaX) * speed), 0);
            } else if (deltaY != 0) {
                move(0, (int)(Math.signum(deltaY) * speed));
            }

            // Check if the invader has reached the current waypoint
            if (Math.abs(deltaX) <= speed && Math.abs(deltaY) <= speed) {
                setLocation(currentWaypoint.x, currentWaypoint.y);
                currentWaypointIndex++;
            }
        } else {
            setisPassed();
        }

        nextFrame();
    }
    
    @Override
    public void updateAnimation() {
        String spritePath = spritePaths.get(action).get(direction);
        int totalFrames = frameCounts.get(action).get(direction);
        setAnimation(spritePath, totalFrames);
    }

}

