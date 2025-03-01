package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import my_shapes.AnimatedImage;
import shapes.Circle;
import shapes.Image;
import shapes.Rectangle;
import shapes.Text;
import ui_elements.ScreenPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower extends GameObject implements ShapeListener {

    public enum TowerState {
        // Build cost; Sell value; numOfDefenders; attackRange;
        NOT_CONSTRUCTED(100, 0, 0),
        CONSTRUCTED_LEVEL1(30, 85, 150),
        CONSTRUCTED_LEVEL2(130, 110, 170),
        CONSTRUCTED_LEVEL3(165, 220, 190),
        // CONSTRUCTED_LEVEL4(150, 80, 190),       // Cancelled level 4 because the sprite has a roof
        CONSTRUCTED_LEVEL5(200, 350, 210),
        CONSTRUCTED_LEVEL6(0, 520, 230); 

        private final int upgradeCost;
        private final int sellValue;
        private final int attackRange;

        TowerState(int upgradeCost, int sellValue, int attackRange) {
            this.upgradeCost = upgradeCost;
            this.sellValue = sellValue;
            this.attackRange = attackRange;
        }

        public int getUpgradeCost() {
            return upgradeCost;
        }
    
        public int getSellValue() {
            return sellValue;
        }

        public int getAttackRange() {
            return attackRange;
        }

    }

    private TowerState state = TowerState.NOT_CONSTRUCTED;

    private boolean isClicked = false;

    // Circles to visualize attack range when hovering
    private Circle currentRangeCircle;
    private Circle nextRangeCircle;

    // Circle for background highlight when hovering
    private Circle highlightCircle;

    // Highlight rectangles for buttons when hovering
    private Rectangle buildButtonHighlight;
    private Rectangle sellButtonHighlight;


    // Defenders linked to the tower
    private static final int MAX_DEFENDERS = 2;
    private static int nextDefenderId = 0;
    private final List<String> defenders = new ArrayList<>();
    
    // Animated image variables
    private AnimatedImage animatedImage;
    private Map<TowerState, String> spritePaths = new HashMap<>();
    private Map<TowerState, Integer> frameCounts = new HashMap<>();
    private static final int FRAME_WIDTH = 70;
    private static final int FRAME_HEIGHT = 130;

    private MyContent content = (MyContent) Game.Content();

    // Button images for building/upgrading and selling
    private Image buildButton;
    private Image sellButton;
    private static final int BUTTON_WIDTH = 45;
    private static final int BUTTON_HEIGHT = 45;
    private Text priceLabel;
    private Text sellLabel;


    public Tower(String id, ScreenPoint location) {
        super(id, location);

        // Initialize animation mappings
        initializeMappings();

        // Initialize animated image
        this.animatedImage = new AnimatedImage(id + "_img", FRAME_WIDTH, FRAME_HEIGHT, false);
        updateAnimation(); // Apply initial animation
        this.animatedImage.moveToLocation(location.x, location.y);
        this.animatedImage.setShapeListener(this);
        // addActivityCircle(); // applies shapeListener
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

    // Returns the current TowerState
    public TowerState getState() {
        return state;
    }

    // Returns the next TowerState
    private TowerState getNextState() {
        switch (state) {
            case NOT_CONSTRUCTED:
                return TowerState.CONSTRUCTED_LEVEL1;
            case CONSTRUCTED_LEVEL1:
                return TowerState.CONSTRUCTED_LEVEL2;
            case CONSTRUCTED_LEVEL2:
                return TowerState.CONSTRUCTED_LEVEL3;
            case CONSTRUCTED_LEVEL3:
                return TowerState.CONSTRUCTED_LEVEL5;
            case CONSTRUCTED_LEVEL5:
                return TowerState.CONSTRUCTED_LEVEL6;
            default:
                return null;
        }
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
        nextDefenderId += 1;
        String defenderId = "defender_" + getId() + "_" + nextDefenderId; // use counter instead of time
        Defender defender = null;
    
        // Cannot add a defender if the tower hasn't been built.
        if (state == TowerState.NOT_CONSTRUCTED) return;
        
        // Using ordinal comparisons to choose defender type:
        if (state.ordinal() > TowerState.NOT_CONSTRUCTED.ordinal() 
                && state.ordinal() <= TowerState.CONSTRUCTED_LEVEL3.ordinal()) {
            defender = new Archer(defenderLocation, defenderId, Direction.DOWN, Action.IDLE, state.getAttackRange());
        } else if (state.ordinal() > TowerState.CONSTRUCTED_LEVEL3.ordinal() 
                && state.ordinal() <= TowerState.CONSTRUCTED_LEVEL5.ordinal()) {
            defender = new Marksman(defenderLocation, defenderId, Direction.DOWN, Action.IDLE, state.getAttackRange());
        } else if (state.ordinal() > TowerState.CONSTRUCTED_LEVEL5.ordinal()) {
            defender = new Sharpshooter(defenderLocation, defenderId, Direction.DOWN, Action.IDLE, state.getAttackRange());
        }
        
        if (defender == null) return;
        
        content.addToContent(defender);
        defender.addToCanvas();
        defender.animatedImage.setShapeListener(this); // Avoid z-order mouse activity issues
        defenders.add(defenderId);
    }
    

    /* 
    Upgrades defenders to the correct type based on tower state.
    It deactivates all current defenders and then re-adds the same number
    (because of changes in locations and defender type or stats)
    */
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
        nextDefenderId = 0; 
        /* 
        Resetting the id counter is not necessary theoretically 
        However not resetting introduces a bug where sometimes a defender is not properly deactivated
        */
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

    // Shows the current attack range circle around the tower
    private void showCurrentAttackRangeCircle() {
        GameCanvas canvas = Game.UI().canvas();
        ScreenPoint center = new ScreenPoint(location.x + FRAME_WIDTH / 2 - 1, location.y + FRAME_HEIGHT - 32);
        int radius = state.getAttackRange();
        
        if (currentRangeCircle == null) {
            currentRangeCircle = new Circle(this.id + "_currentRange", center, radius);
            currentRangeCircle.setIsFilled(true);
            currentRangeCircle.setFillColor(new java.awt.Color(51, 170, 255, 40));
            currentRangeCircle.setColor(new java.awt.Color(51, 170, 255, 40));
            currentRangeCircle.setzOrder(0);
            canvas.addShape(currentRangeCircle);
        } else {
            currentRangeCircle.setRadius(radius);
        }
        canvas.revalidate();
        canvas.repaint();
    }

    // Hides the current attack range circle
    private void hideCurrentAttackRangeCircle() {
        GameCanvas canvas = Game.UI().canvas();
        if (currentRangeCircle != null) {
            canvas.deleteShape(currentRangeCircle.getId());
            currentRangeCircle = null;
        }
        canvas.revalidate();
        canvas.repaint();
    }

    // Shows the next upgrade attack range circle based on the next state
    private void showNextAttackRangeCircle() {
        TowerState next = getNextState();
        if (next == null) return; // already at max
        GameCanvas canvas = Game.UI().canvas();
        ScreenPoint center = new ScreenPoint(location.x + FRAME_WIDTH / 2 - 1, location.y + FRAME_HEIGHT - 32);

        if (state != TowerState.NOT_CONSTRUCTED) { // if level !=0 show difference between levels
            int addedRange = next.getAttackRange() - state.getAttackRange();
            int radius = state.getAttackRange() + addedRange / 2;   
            if (nextRangeCircle == null) {
                nextRangeCircle = new Circle(this.id + "_nextRange", center, radius);
                nextRangeCircle.setIsFilled(false);
                // nextRangeCircle.setFillColor(new java.awt.Color(250, 0, 0, 80));
                nextRangeCircle.setColor(new java.awt.Color(0, 255, 0, 40));
                nextRangeCircle.setWeight(addedRange);
                nextRangeCircle.setzOrder(1);
                canvas.addShape(nextRangeCircle);
            } else {
                nextRangeCircle.setRadius(radius);
            }
        } else { // if level 0 show a filled circle
            int radius = next.getAttackRange();
            if (currentRangeCircle == null) {
                currentRangeCircle = new Circle(this.id + "_currentRange", center, radius);
                currentRangeCircle.setIsFilled(true);
                currentRangeCircle.setFillColor(new java.awt.Color(51, 170, 255, 40));
                currentRangeCircle.setColor(new java.awt.Color(51, 170, 255, 40));
                currentRangeCircle.setzOrder(0);
                canvas.addShape(currentRangeCircle);
            } else {
                currentRangeCircle.setRadius(radius);
            }
        }
        canvas.revalidate();
        canvas.repaint();
    }

    // Hides the next attack range circle
    private void hideNextAttackRangeCircle() {
        GameCanvas canvas = Game.UI().canvas();
        if (nextRangeCircle != null) {
            canvas.deleteShape(nextRangeCircle.getId());
            nextRangeCircle = null;
        }
        canvas.revalidate();
        canvas.repaint();
    }
    
    // Highlight the tower when hovering with a circle
    private void showHighlight() {
        if (highlightCircle == null) {
            ScreenPoint center = new ScreenPoint(location.x + FRAME_WIDTH / 2 - 1, location.y + FRAME_HEIGHT - 31);
            int radius = FRAME_WIDTH / 2 - 3;
            
            // Create the highlight circle
            highlightCircle = new Circle(this.id + "_highlight", center, radius);
            highlightCircle.setIsFilled(true);
            highlightCircle.setFillColor(new java.awt.Color(240, 240, 160, 80));
            highlightCircle.setColor(new java.awt.Color(240, 240, 160, 80));
            highlightCircle.setzOrder(0);
            
            // Add the circle to the canvas
            Game.UI().canvas().addShape(highlightCircle);
            Game.UI().canvas().revalidate();
            Game.UI().canvas().repaint();
        }
    }

    // Hides the highlight circle
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
        animatedImage.setzOrder(9);
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

    // Show Build/Upgrade and Sell buttons near the tower
    private void showButtons() {
        GameCanvas canvas = Game.UI().canvas();
        // Calculate positions relative to the tower location.
        int buildX = location.x - BUTTON_WIDTH/2;
        int buildY = location.y - 20;
        int sellX = location.x + BUTTON_WIDTH;
        int sellY = location.y - 20;

        // Create Build button
    
        if (buildButton == null) {
            String buildButtonPath = "resources/objects/buttons/archers.png";
            if (state != TowerState.NOT_CONSTRUCTED) {
                buildButtonPath = "resources/objects/buttons/upgrade.png";
            }
            
            buildButton = new Image(this.id + "_build", buildButtonPath, BUTTON_WIDTH, BUTTON_HEIGHT, buildX, buildY);
            buildButton.setzOrder(10);
            buildButton.setDraggable(false);
            buildButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    advanceState();
                    hideAll();
                }
                @Override public void shapeMoved(String shapeID, int dx, int dy) {}
                @Override public void shapeStartDrag(String shapeID) {}
                @Override public void shapeEndDrag(String shapeID) {}
                @Override public void shapeRightClicked(String shapeID, int x, int y) {}
                @Override
                public void mouseEnterShape(String shapeID, int x, int y) {
                    showBuildButtonHighlight();
                    showNextAttackRangeCircle();
                }
                @Override
                public void mouseExitShape(String shapeID, int x, int y) {
                    hideBuildButtonHighlight();
                    hideNextAttackRangeCircle();
                }
            });
        }
        
        // Create Sell button
        if (sellButton == null) {
            sellButton = new Image(this.id + "_sell", "resources/objects/buttons/sell.png", BUTTON_WIDTH, BUTTON_HEIGHT, sellX, sellY);
            sellButton.setzOrder(10);
            sellButton.setDraggable(false);
            sellButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    sell();
                    hideAll();
                }
                @Override public void shapeMoved(String shapeID, int dx, int dy) {}
                @Override public void shapeStartDrag(String shapeID) {}
                @Override public void shapeEndDrag(String shapeID) {}
                @Override public void shapeRightClicked(String shapeID, int x, int y) {}
                @Override
                public void mouseEnterShape(String shapeID, int x, int y) {
                    showSellButtonHighlight();
                }
                @Override
                public void mouseExitShape(String shapeID, int x, int y) {
                    hideSellButtonHighlight();
                }
            });
        } 

        // Add the buttons to the canvas
        if (state != TowerState.CONSTRUCTED_LEVEL6) { // don't show the upgrade button at max level
            canvas.addShape(buildButton);
        }
        if (state != TowerState.NOT_CONSTRUCTED) { // don't show sell if level 0
            canvas.addShape(sellButton);
        }

        // Add text labels below the buttons
        int priceX = buildX - 5;
        int priceY = buildY + BUTTON_HEIGHT + 12;
        String priceText = "Buy for " + getState().getUpgradeCost();
        
        if (priceLabel == null) {
            priceLabel = new Text(this.id + "_price", priceText, priceX, priceY);
            priceLabel.setColor(java.awt.Color.WHITE);
            if (state != TowerState.CONSTRUCTED_LEVEL6) { // don't show the price at max level
            canvas.addShape(priceLabel);
        }
            
        } else {
            priceLabel.setText(priceText);
            priceLabel.moveToLocation(priceX, priceY);
        }
        
        // And display the sell value below the sell button.
        int sellTextX = sellX - 1;
        int sellTextY = sellY + BUTTON_HEIGHT + 12;
        String sellText = "Sell for " + getState().getSellValue();
        
        if (sellLabel == null) {
            sellLabel = new Text(this.id + "_sellLabel", sellText, sellTextX, sellTextY);
            sellLabel.setColor(java.awt.Color.WHITE);
            if (state != TowerState.NOT_CONSTRUCTED) { // don't show sell if level 0
                canvas.addShape(sellLabel);
        }
        } else {
            sellLabel.setText(sellText);
            sellLabel.moveToLocation(sellTextX, sellTextY);
        }

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
        if (priceLabel != null) {
            canvas.deleteShape(priceLabel.getId());
            priceLabel = null;
        }
        if (sellLabel != null) {
            canvas.deleteShape(sellLabel.getId());
            sellLabel = null;
        }
        canvas.revalidate();
        canvas.repaint();
    }

    private void showBuildButtonHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        int rectX = location.x - BUTTON_WIDTH/2 - 2;
        int rectY = location.y - 22;
        int rectWidth = BUTTON_WIDTH + 4;
        int rectHeight = BUTTON_HEIGHT + 4;
        if (buildButtonHighlight == null) {
            buildButtonHighlight = new Rectangle(this.id + "_buildHighlight", rectX, rectY, rectWidth, rectHeight);
            buildButtonHighlight.setIsFilled(true);
            buildButtonHighlight.setFillColor(new java.awt.Color(240, 240, 160, 80));
            buildButtonHighlight.setColor(new java.awt.Color(240, 240, 160, 80));
            buildButtonHighlight.setWeight(0);
            buildButtonHighlight.setzOrder(3);
            canvas.addShape(buildButtonHighlight);
            canvas.revalidate();
            canvas.repaint();
        }
    }
    
    private void hideBuildButtonHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        if (buildButtonHighlight != null) {
            canvas.deleteShape(buildButtonHighlight.getId());
            buildButtonHighlight = null;
            canvas.revalidate();
            canvas.repaint();
        }
    }

    private void showSellButtonHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        int rectX = location.x + BUTTON_WIDTH - 2;
        int rectY = location.y - 22;
        int rectWidth = BUTTON_WIDTH + 4;
        int rectHeight = BUTTON_HEIGHT + 4;
        if (sellButtonHighlight == null) {
            sellButtonHighlight = new Rectangle(this.id + "_sellHighlight", rectX, rectY, rectWidth, rectHeight);
            sellButtonHighlight.setIsFilled(true);
            sellButtonHighlight.setFillColor(new java.awt.Color(240, 240, 160, 80));
            sellButtonHighlight.setColor(new java.awt.Color(240, 240, 160, 80));
            sellButtonHighlight.setWeight(0);
            sellButtonHighlight.setzOrder(3);
            canvas.addShape(sellButtonHighlight);
            canvas.revalidate();
            canvas.repaint();
        }
    }
    
    private void hideSellButtonHighlight() {
        GameCanvas canvas = Game.UI().canvas();
        if (sellButtonHighlight != null) {
            canvas.deleteShape(sellButtonHighlight.getId());
            sellButtonHighlight = null;
            canvas.revalidate();
            canvas.repaint();
        }
    }
    
    // public methods for MyMouseHandler
    public void hideAll() {
        isClicked = false;
        hideButtons();
        hideCurrentAttackRangeCircle();
        hideHighlight();
        hideNextAttackRangeCircle();
        hideBuildButtonHighlight();
        hideSellButtonHighlight();
    }

    public void hideHoverLogic() {
        hideNextAttackRangeCircle();
        hideBuildButtonHighlight();
        hideSellButtonHighlight();
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
        isClicked = true;
        showButtons();
        showCurrentAttackRangeCircle();
    }

    @Override
    public void shapeRightClicked(String shapeID, int x, int y) {
        ;
    }

    @Override
    public void mouseEnterShape(String shapeID, int x, int y) {
        showHighlight();
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        if (!isClicked) hideHighlight();
    }
}
