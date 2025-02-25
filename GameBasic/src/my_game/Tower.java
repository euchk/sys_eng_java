package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import shapes.AnimatedImage;
import shapes.Circle;
import shapes.Image;
import ui_elements.ScreenPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower extends GameObject implements ShapeListener {

    public enum TowerState {
        NOT_CONSTRUCTED(100, 0),           // Build cost 100; sell value 0
        CONSTRUCTED_LEVEL1(120, 50),       // Upgrading to Level1 costs 120; sell value 50; adds 1 archer
        CONSTRUCTED_LEVEL2(130, 60),       // Level2: costs 130; sell value 60; no defender change
        CONSTRUCTED_LEVEL3(140, 70),       // Level3: costs 140; sell value 70; adds 2nd archer
        // CONSTRUCTED_LEVEL4(150, 80),       // Level4: costs 150; sell value 80; upgrades both to Marksmen
        CONSTRUCTED_LEVEL5(160, 90),       // Level5: costs 160; sell value 90; no change
        CONSTRUCTED_LEVEL6(0, 100);        // Level6: cost 0 (max); sell value 100; upgrades both to Sharpshooters

        private final int upgradeCost;
        private final int sellValue;

        TowerState(int upgradeCost, int sellValue) {
            this.upgradeCost = upgradeCost;
            this.sellValue = sellValue;
        }

        public int getUpgradeCost() {
            return upgradeCost;
        }
    
        public int getSellValue() {
            return sellValue;
        }

    }

    private TowerState state = TowerState.NOT_CONSTRUCTED;

    private static final int MAX_DEFENDERS = 2;
    private final List<String> defenders = new ArrayList<>();
    
    // Animated image for the tower
    private AnimatedImage animatedImage;
    private Map<TowerState, String> spritePaths = new HashMap<>();
    private Map<TowerState, Integer> frameCounts = new HashMap<>();

    // Animate image size constants
    private static final int FRAME_WIDTH = 70;
    private static final int FRAME_HEIGHT = 130;

    private MyContent content = (MyContent) Game.Content();

    // Button images
    private Image buildButton;
    private Image sellButton;

    // Background highlight when hovering
    private Circle highlightCircle;
    
    // Button size constants (adjust as needed)
    private static final int BUTTON_WIDTH = 75;
    private static final int BUTTON_HEIGHT = 75;

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
        spritePaths.put(TowerState.NOT_CONSTRUCTED, "resources/objects/tower/0.png");
        spritePaths.put(TowerState.CONSTRUCTED_LEVEL1, "resources/objects/tower/1.png");
        spritePaths.put(TowerState.CONSTRUCTED_LEVEL2, "resources/objects/tower/2.png");
        spritePaths.put(TowerState.CONSTRUCTED_LEVEL3, "resources/objects/tower/3.png");
        // spritePaths.put(TowerState.CONSTRUCTED_LEVEL4, "resources/objects/tower/4.png");
        spritePaths.put(TowerState.CONSTRUCTED_LEVEL5, "resources/objects/tower/5.png");
        spritePaths.put(TowerState.CONSTRUCTED_LEVEL6, "resources/objects/tower/6.png");

        frameCounts.put(TowerState.NOT_CONSTRUCTED, 1);
        frameCounts.put(TowerState.CONSTRUCTED_LEVEL1, 1);
        frameCounts.put(TowerState.CONSTRUCTED_LEVEL2, 4);
        frameCounts.put(TowerState.CONSTRUCTED_LEVEL3, 4);
        // frameCounts.put(TowerState.CONSTRUCTED_LEVEL4, 6);
        frameCounts.put(TowerState.CONSTRUCTED_LEVEL5, 6);
        frameCounts.put(TowerState.CONSTRUCTED_LEVEL6, 6);
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

    // Advance tower state and update animation
    public void advanceState() {
        if (state == TowerState.CONSTRUCTED_LEVEL6) {
            System.out.println("Already at max upgrade.");
            return;
        }
        // Check for sufficiant coins
        if (content.coins().getCurrentCoins() < getState().getUpgradeCost()) return;
        // Spend coins
        content.coins().spendCoins(getState().getUpgradeCost());

        switch (state) {            
            case NOT_CONSTRUCTED:
                state = TowerState.CONSTRUCTED_LEVEL1;
                addDefender();
                break;
            case CONSTRUCTED_LEVEL1:
                state = TowerState.CONSTRUCTED_LEVEL2;
                upgradeDefenders();
                break;
            case CONSTRUCTED_LEVEL2:
                state = TowerState.CONSTRUCTED_LEVEL3;
                addDefender();
                upgradeDefenders();
                break;
            case CONSTRUCTED_LEVEL3:
                state = TowerState.CONSTRUCTED_LEVEL5;
                upgradeDefenders();
                break;
            // case CONSTRUCTED_LEVEL4:
            //     state = TowerState.CONSTRUCTED_LEVEL5;
            //     upgradeDefenders();
            //     break;
            case CONSTRUCTED_LEVEL5:
                state = TowerState.CONSTRUCTED_LEVEL6;
                upgradeDefenders();
                break;
            case CONSTRUCTED_LEVEL6:
                System.out.println("Already at max upgrade.");
                return;
        }
        
        updateAnimation();
    }

    // Sell tower and reset animation
    public void sell() {
        if (getState() == TowerState.NOT_CONSTRUCTED) return;
        content.coins().addCoins(getState().getSellValue());
        state = TowerState.NOT_CONSTRUCTED;
        deactivateAllDefenders();
        updateAnimation();
    }

    // Adds a defender based on the tower state (max 2 defenders)
    public void addDefender() {
        if (defenders.size() >= MAX_DEFENDERS) return;
        
        ScreenPoint defenderLocation = getDefenderSpawnLocation();
        String defenderId = "defender_" + System.currentTimeMillis();
        Defender defender = null;

        // Cannot add a defender if the tower hasn't been built.
        if (state == TowerState.NOT_CONSTRUCTED) return;
        
        // Using ordinal comparisons to choose defender type:
        if (state.ordinal() > TowerState.NOT_CONSTRUCTED.ordinal() 
                && state.ordinal() <= TowerState.CONSTRUCTED_LEVEL3.ordinal()) {
            defender = new Archer(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);

        } else if (state.ordinal() > TowerState.CONSTRUCTED_LEVEL3.ordinal() 
                && state.ordinal() <= TowerState.CONSTRUCTED_LEVEL5.ordinal()) {
            defender = new Marksman(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
            
        } else if (state.ordinal() > TowerState.CONSTRUCTED_LEVEL5.ordinal()) {
            defender = new Sharpshooter(defenderLocation, defenderId, Direction.DOWN, Action.IDLE);
        }
        
        if (defender == null) return;
        
        content.addToContent(defender);
        defender.addToCanvas();
        defenders.add(defenderId);
    }

    // Upgrades defenders to the correct type based on tower state.
    // It deactivates all current defenders and then re-adds the same number
    // (which will pick the correct defender type in addDefender()).
    private void upgradeDefenders() {
        if (defenders.isEmpty()) return;
        
        int defenderCount = defenders.size(); // store current number of defenders
        deactivateAllDefenders();             // remove current defenders
        
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
        int offsetX = 0;
        int offsetY = 0;
        switch (state) {            
            case NOT_CONSTRUCTED:
                break;
            case CONSTRUCTED_LEVEL1:
                offsetX = 11;
                offsetY = 67;
                break;
            case CONSTRUCTED_LEVEL2:
                offsetX = 11;
                offsetY = 58;
                break;
            case CONSTRUCTED_LEVEL3:
                offsetX = 3 + defenders.size() * 17;
                offsetY = 50  - defenders.size() * 5;
                break;
            // case CONSTRUCTED_LEVEL4:
            //     offsetX = 3 + defenders.size() * 17;
            //     offsetY = 60  - defenders.size() * 5;
            //     break;
            case CONSTRUCTED_LEVEL5:
                offsetX = 3 + defenders.size() * 17;
                offsetY = 45  - defenders.size() * 5;
                break;
            case CONSTRUCTED_LEVEL6:
                offsetX = 3 + defenders.size() * 17;
                offsetY = 45  - defenders.size() * 5;
        }
        return new ScreenPoint(location.x + offsetX, location.y + offsetY);
    }

    /**
     * Creates and displays a semi-transparent highlight circle around the tower.
     * Uses FilledShape methods: setIsFilled(true) and setFillColor(...)
     */
    private void showHighlight() {
        if (highlightCircle == null) {
            // Determine the center of the tower (assuming location is the top-left corner)
            ScreenPoint center = new ScreenPoint(location.x + FRAME_WIDTH / 2 - 1, location.y + FRAME_HEIGHT - 32);
            int radius = FRAME_WIDTH / 2 - 2; // adjust radius as needed
            
            // Create the highlight circle
            highlightCircle = new Circle(this.id + "_highlight", center, radius);
            // Mark it as filled and set a semi-transparent yellow color (alpha 128 for ~50% opacity)
            highlightCircle.setIsFilled(true);
            highlightCircle.setFillColor(new java.awt.Color(255, 255, 0, 128));
            highlightCircle.setColor(new java.awt.Color(255, 255, 0, 128));
            
            // Optionally, if your canvas supports z-order, you can set it lower so it appears behind the tower.
            highlightCircle.setzOrder(0);
            
            // Add the circle to the canvas
            Game.UI().canvas().addShape(highlightCircle);
            Game.UI().canvas().revalidate();
            Game.UI().canvas().repaint();
        }
    }

    /**
     * Removes the highlight circle from the canvas.
     */
    private void hideHighlight() {
        if (highlightCircle != null) {
            Game.UI().canvas().deleteShape(highlightCircle.getId());
            highlightCircle = null;
            Game.UI().canvas().revalidate();
            Game.UI().canvas().repaint();
        }
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

    

    public TowerState getState() {
        return state;
    }

    // Show Build, Buy, and Sell buttons near the tower.
    private void showButtons() {
        GameCanvas canvas = Game.UI().canvas();
        // Calculate positions relative to the tower location.
        int buildX = location.x - BUTTON_WIDTH/2;
        int buildY = location.y - 25;
        int sellX = location.x + BUTTON_WIDTH/2;
        int sellY = location.y - 25;

        // Create Build button
    
        if (buildButton == null) {
            String buildButtonPath = "resources/objects/buttons/archers.png";
            if (state != TowerState.NOT_CONSTRUCTED) {
                buildButtonPath = "resources/objects/buttons/upgrade.png";
            }
            
            buildButton = new Image(this.id + "_build", buildButtonPath, BUTTON_WIDTH, BUTTON_HEIGHT, buildX, buildY);
            buildButton.setDraggable(false);
            buildButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    advanceState();
                    hideButtons();
                }
                @Override public void shapeMoved(String shapeID, int dx, int dy) {}
                @Override public void shapeStartDrag(String shapeID) {}
                @Override public void shapeEndDrag(String shapeID) {}
                @Override public void shapeRightClicked(String shapeID, int x, int y) {}
                @Override public void mouseEnterShape(String shapeID, int x, int y) {}
                @Override public void mouseExitShape(String shapeID, int x, int y) {}
            });
        } else {
            buildButton.moveToLocation(buildX, buildY);
        }
        
        // Create Sell button
        if (sellButton == null) {
            sellButton = new Image(this.id + "_sell", "resources/objects/buttons/sell.png", BUTTON_WIDTH, BUTTON_HEIGHT, sellX, sellY);
            sellButton.setDraggable(false);
            sellButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    sell();
                    hideButtons();
                }
                @Override public void shapeMoved(String shapeID, int dx, int dy) {}
                @Override public void shapeStartDrag(String shapeID) {}
                @Override public void shapeEndDrag(String shapeID) {}
                @Override public void shapeRightClicked(String shapeID, int x, int y) {}
                @Override public void mouseEnterShape(String shapeID, int x, int y) {}
                @Override public void mouseExitShape(String shapeID, int x, int y) {}
            });
        } else {
            sellButton.moveToLocation(sellX, sellY);
        }
        // Add the buttons to the canvas
        if (state != TowerState.CONSTRUCTED_LEVEL6) { // Can't upgrade at level 6
            canvas.addShape(buildButton);
        }
        canvas.addShape(sellButton);
        canvas.revalidate();
        canvas.repaint();
    }

    // Hide (remove) the buttons from the canvas
    private void hideButtons() {
        GameCanvas canvas = Game.UI().canvas();
        if (buildButton != null) {
            canvas.deleteShape(buildButton.getId());
            buildButton = null;
        }
        if (sellButton != null) {
            canvas.deleteShape(sellButton.getId());
            sellButton = null;
        }
        canvas.revalidate();
        canvas.repaint();
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
        ;
    }

    @Override
    public void shapeRightClicked(String shapeID, int x, int y) {
        ;
    }

    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        showHighlight();
        showButtons();
        
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        hideHighlight();
        hideButtons();
    }
}
