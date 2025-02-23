package my_game;

import base.Game;
import base.GameCanvas;
import base.ShapeListener;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import shapes.AnimatedImage;
import shapes.Image;
import ui_elements.ScreenPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tower extends GameObject implements ShapeListener {

    public enum TowerState {
        NOT_CONSTRUCTED(100, 0, 0), // Building costs 100 coins; can't sell; can't train defenders
        CONSTRUCTED(120, 50, 30),  // Upgrade costs 120 coins; selling gives 50 coins; archer costs 30
        UPGRADE_1(150, 70, 40),    // Upgrade costs 150 coins; selling gives 70 coins; marksman costs 40
        UPGRADE_2(0, 90, 50);   // can't upgrade; selling gives 90 coins; sharpshooter costs 50
    
        private final int upgradeCost; // cost to build/upgrade to this level
        private final int sellValue;   // coins earned when selling at this level
        private final int defenderCost;   // cost to train a defender

    
        TowerState(int upgradeCost, int sellValue, int defenderCost) {
            this.upgradeCost = upgradeCost;
            this.sellValue = sellValue;
            this.defenderCost = defenderCost;
        }

        public int getUpgradeCost() {
            return upgradeCost;
        }
    
        public int getSellValue() {
            return sellValue;
        }

        public int getDefenderCost() {
            return defenderCost;
        }
    }

    private TowerState state = TowerState.NOT_CONSTRUCTED;

    private static final int MAX_DEFENDERS = 3;
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
    private Image buyButton;
    private Image sellButton;
    
    // Button size constants (adjust as needed)
    private static final int BUTTON_WIDTH = 33;
    private static final int BUTTON_HEIGHT = 33;

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
        // Check if there are already 3 defenders
        if (defenders.size() >= MAX_DEFENDERS) return;
        // Check for sufficiant coins
        if (content.coins().getCurrentCoins() < getState().getDefenderCost()) return;
        // Spend coins
        content.coins().spendCoins(getState().getDefenderCost()); 

        // Create defender
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
        // Check for sufficiant coins
        if (content.coins().getCurrentCoins() < getState().getUpgradeCost()) return;
        // Spend coins
        content.coins().spendCoins(getState().getUpgradeCost());

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
        if (getState() == TowerState.NOT_CONSTRUCTED) return;
        content.coins().addCoins(getState().getSellValue());
        state = TowerState.NOT_CONSTRUCTED;
        deactivateAllDefenders();
        updateAnimation();
    }

    public TowerState getState() {
        return state;
    }

    // Show Build, Buy, and Sell buttons near the tower.
    private void showButtons() {
        GameCanvas canvas = Game.UI().canvas();
        // Calculate positions relative to the tower location.
        int buildX = location.x -2 - BUTTON_WIDTH/2;
        int buildY = location.y + 10;
        int buyX = location.x + BUTTON_WIDTH/2;
        int buyY = location.y + 10 - BUTTON_WIDTH/2;
        int sellX = location.x + 2 + BUTTON_WIDTH + BUTTON_WIDTH/2;
        int sellY = location.y + 10;

        // Create Build button
        if (buildButton == null) {
            buildButton = new Image(this.id + "_build", "resources/objects/buttons/build.png", BUTTON_WIDTH, BUTTON_HEIGHT, buildX, buildY);
            buildButton.setDraggable(false);
            buildButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    advanceState();
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
        // Create Buy button
        if (buyButton == null) {
            buyButton = new Image(this.id + "_buy", "resources/objects/buttons/buy.png", BUTTON_WIDTH, BUTTON_HEIGHT, buyX, buyY);
            buyButton.setDraggable(false);
            buyButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    addDefender();
                }
                @Override public void shapeMoved(String shapeID, int dx, int dy) {}
                @Override public void shapeStartDrag(String shapeID) {}
                @Override public void shapeEndDrag(String shapeID) {}
                @Override public void shapeRightClicked(String shapeID, int x, int y) {}
                @Override public void mouseEnterShape(String shapeID, int x, int y) {}
                @Override public void mouseExitShape(String shapeID, int x, int y) {}
            });
        } else {
            buyButton.moveToLocation(buyX, buyY);
        }
        // Create Sell button
        if (sellButton == null) {
            sellButton = new Image(this.id + "_sell", "resources/objects/buttons/sell.png", BUTTON_WIDTH, BUTTON_HEIGHT, sellX, sellY);
            sellButton.setDraggable(false);
            sellButton.setShapeListener(new ShapeListener() {
                @Override
                public void shapeClicked(String shapeID, int x, int y) {
                    sell();
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
        canvas.addShape(buildButton);
        canvas.addShape(buyButton);
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
        if (buyButton != null) {
            canvas.deleteShape(buyButton.getId());
            buyButton = null;
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
        showButtons();
    }

    @Override
    public void mouseExitShape(String shapeID, int x, int y) {
        hideButtons();
    }
}
