package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import shapes.AnimatedImage;
import ui_elements.ScreenPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower extends GameObject implements ShapeListener {

    public enum TowerState {
        NOT_CONSTRUCTED,  // 0
        CONSTRUCTED,      // 1
        UPGRADE_1,        // 2
        UPGRADE_2         // 3
    }

    private TowerState state = TowerState.NOT_CONSTRUCTED;

    private static final int MAX_DEFENDERS = 3;
    private final List<String> defenders = new ArrayList<>();
    
    private static final int FRAME_WIDTH = 70;
    private static final int FRAME_HEIGHT = 130;
    
    private AnimatedImage animatedImage;
    private Map<TowerState, String> spritePaths = new HashMap<>();
    private Map<TowerState, Integer> frameCounts = new HashMap<>();

    private MyContent content = (MyContent) Game.Content();

    public Tower(String id, ScreenPoint location) {
        super(id, location);

        // Initialize animation mappings
        initializeMappings();

        // Initialize animated image
        this.animatedImage = new AnimatedImage(id + "_img", FRAME_WIDTH, FRAME_HEIGHT, false);
        this.animatedImage.setShapeListener(this);
        updateAnimation(); // Apply initial animation
        this.animatedImage.moveToLocation(location.x, location.y);
    }

    // Maps states to sprite paths and frame counts
    protected void initializeMappings() {
        spritePaths.put(TowerState.NOT_CONSTRUCTED, "resources/objects/tower/2.png");
        spritePaths.put(TowerState.CONSTRUCTED, "resources/objects/tower/3.png");
        spritePaths.put(TowerState.UPGRADE_1, "resources/objects/tower/4.png");
        spritePaths.put(TowerState.UPGRADE_2, "resources/objects/tower/7.png");

        frameCounts.put(TowerState.NOT_CONSTRUCTED, 4);
        frameCounts.put(TowerState.CONSTRUCTED, 4);
        frameCounts.put(TowerState.UPGRADE_1, 6);
        frameCounts.put(TowerState.UPGRADE_2, 6);
    }

    // Updates animation based on the current state
    public void updateAnimation() {
        String spritePath = spritePaths.get(state);
        int totalFrames = frameCounts.get(state);
        setAnimation(spritePath, totalFrames);
    }

    // Sets the animation for the animated image
    public void setAnimation(String spritePath, int totalFrames) {
        animatedImage.setSpriteSheet(spritePath, totalFrames);
    }

    public void moveToLocation(ScreenPoint newLocation) {
        this.location = newLocation;
        animatedImage.moveToLocation(newLocation.x, newLocation.y);
    }

    public void nextFrame() {
        animatedImage.nextFrame();
    }

    // Adds a defender based on the tower state (max 3 defenders)
    public void addDefender() {
        if (defenders.size() >= MAX_DEFENDERS) {
            return;
        }

        ScreenPoint defenderLocation = getDefenderSpawnLocation();
        String defenderId = "defender_" + System.currentTimeMillis();
        Defender defender;

        // Assign the correct defender type based on tower state
        switch (state) {
            case CONSTRUCTED:
                defender = new Archer(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
                break;
            case UPGRADE_1:
                defender = new Marksman(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
                break;
            case UPGRADE_2:
                defender = new Sharpshooter(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
                break;
            default:
                return;
        }

        content.addToContent(defender);
        defender.addToCanvas();
        defenders.add(defenderId);
    }

    // Upgrades defenders to the correct type based on tower state
    private void upgradeDefenders() {
        if (defenders.isEmpty()) return;
    
        int defenderCount = defenders.size(); // Store current number of defenders
        deactivateAllDefenders(); // Deactivate all defenders
    
        // Re-add the same number of defenders with the upgraded version
        for (int i = 0; i < defenderCount; i++) {
            addDefender();
        }
    }

    // Deactivates all defenders
    private void deactivateAllDefenders() {
        for (String defenderId : defenders) {
            Defender defender = (Defender) content.getFromContent(defenderId);
            if (defender != null) {
                defender.deactivate();
                defender.removeFromCanvas();
            }
        }
        defenders.clear();
    }

    // Determines spawn location for the next defender
    private ScreenPoint getDefenderSpawnLocation() {
        System.out.println(getLocation().x);
        System.out.println(getLocation().y);
        int offsetX = -10 + 20 * defenders.size();
        int offsetY = 110;
        if (defenders.size() == 1) { // Add offset to the middle defender
            offsetY += 10; 
        }
        
        return new ScreenPoint(location.x + offsetX, location.y + offsetY);
    }

    @Override
    public void gameStep() {
        nextFrame();
    }

    @Override
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        animatedImage.setzOrder(1);
        canvas.addShape(animatedImage);
        canvas.revalidate();
        canvas.repaint();
    }

    @Override
    public void removeFromCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(animatedImage.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    // Advance tower state and update animation
    public void advanceState() {
        if (state == TowerState.UPGRADE_2) {
            System.out.println("Already at max upgrade.");
            return;
        }

        switch (state) {
            case NOT_CONSTRUCTED:
                state = TowerState.CONSTRUCTED;
                break;
            case CONSTRUCTED:
                state = TowerState.UPGRADE_1;
                break;
            case UPGRADE_1:
                state = TowerState.UPGRADE_2;
                break;
            default:
                break;
        }

        updateAnimation();
        upgradeDefenders();
    }

    // Sell tower and reset animation
    public void sell() {
        state = TowerState.NOT_CONSTRUCTED;
        deactivateAllDefenders();
        updateAnimation();
    }

    public TowerState getState() {
        return state;
    }

    @Override
    public void shapeMoved(String shapeID, int dx, int dy) {
    }

    @Override
    public void shapeStartDrag(String shapeID) {
        ;
    }

    @Override
    public void shapeEndDrag(String shapeID) {
        ;
    }

    @Override
    public void shapeClicked(String shapeID, int x, int y) {
        advanceState();
        addDefender();
    }

    @Override
    public void shapeRightClicked(String shapeID, int x, int y) {
        sell();
        deactivateAllDefenders();
    }

    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        ;
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        ;
    }
}
