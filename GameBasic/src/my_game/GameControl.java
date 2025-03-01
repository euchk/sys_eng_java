package my_game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import base.Game;
import base.GameCanvas;
import my_base.MyContent;
import my_game.Character.Action;
import my_game.Character.Direction;
import shapes.Text;

public class GameControl {
    private MyContent content;
    private boolean gameOver = false;

    // Wave management
    private List<Wave> waves = new ArrayList<>();
    private Wave activeWave = null;
    private int currentWaveIndex = 0;
    private int interWaveDelay = 300; // Number of gameSteps between waves
    private int interWaveDelayCounter = 0;
    private boolean wavesStarted = false;
    private boolean waveEnded = false;
    private int lastWaveTotalSpawnCount = 0;

    // Fields for text messages during game
    private Text bonusText = null;
    private int bonusTextCounter = 0;
    private final int BONUS_TEXT_LIFETIME = 20;

    // Spell buttons
    private int slowDownTimerCounter = 0;
    private final int SLOWDOWN_INTERVAL = 350;
    private boolean slowDownEffectActive = false;
    private int slowDownEffectTimer = 0;
    private final int SLOWDOWN_EFFECT_DURATION = 70;
    
    public GameControl(MyContent content) {
        this.content = content;
    }
    
    public void gameStep(){
        if (gameOver) return;

        GameCanvas canvas = Game.UI().canvas();

        // Process all game objects
        for (GameObject gameObject : content.getAllGameObjects()) {
            gameObject.gameStep();
        }

        // Update text timer
        if (bonusText != null) {
            bonusTextCounter++;
            if (bonusTextCounter >= BONUS_TEXT_LIFETIME) {
                canvas.deleteShape(bonusText.getId());
                bonusText = null;
                bonusTextCounter = 0;
            }
        }
        
        addPendingObjects();
        removeInactiveGameObjects();
        removePendingObjects();

        // Wave spawning logic
        if (wavesStarted) {
            if (activeWave == null && currentWaveIndex < waves.size()) {
            interWaveDelayCounter++;
        
            // If there are no invaders force the next wave
            if (noInvadersPresent()) {
                startNextWave();
            } else {
                // Allow user to start next wave if current wave is almost done
                if (waveEnded && getActiveInvaderCount() <= 0.5 * lastWaveTotalSpawnCount && content.startWaveButton() != null) {
                    content.startWaveButton().enableButton();
                }
    
                // When full delay is reached start the wave
                if (interWaveDelayCounter >= interWaveDelay) {
                    startNextWave();
                }
                }
            }
        

            if (activeWave != null) {
                activeWave.update();
                if (activeWave.isCompleted()) {
                    lastWaveTotalSpawnCount = activeWave.getTotalSpawnCount();
                    activeWave = null;
                    waveEnded = true;
                }
            }
            
        }

        // Spell buttons logic
        if (content.slowDownButton().isDisabled()) {
            slowDownTimerCounter++;
        }
        // Enable the button if the timer expires
        if (slowDownTimerCounter >= SLOWDOWN_INTERVAL) {
            content.slowDownButton().enableButton();
        }

        if (slowDownEffectActive) {
            slowDownEffectTimer--;
            if (slowDownEffectTimer <= 0) {
                // Restore invaders' speeds
                for (GameObject gameObject : content.getAllGameObjects()) {
                    if (gameObject instanceof Invader) {
                        Invader invader = (Invader) gameObject;
                        invader.resetSpeed(); 
                    }
                }
                slowDownEffectActive = false;
            }
        }
        
        // Check game over conditions
        // Losing because of max invaders passed the gate
        if (content.score().getCurrentScore() >= content.score().getMaxInvadersPassed()) {
            gameOver = true;
            Text losingText = new Text("game over", "YOU LOST!", 850, 450);
            losingText.setFontSize(50);
            losingText.setColor(Color.RED);
            canvas.addShape(losingText);
            return;
        }

        // Winning because all waves ended and no active invaders
        if (wavesStarted && currentWaveIndex >= waves.size() && activeWave == null && noInvadersPresent()) {
            gameOver = true;
            Text victoryText = new Text("victory", "YOU WIN!", 850, 450);
            victoryText.setFontSize(50);
            victoryText.setColor(Color.BLUE);
            canvas.addShape(victoryText);
            return;
        }
        
        canvas.revalidate();
        canvas.repaint();
    }

    private void startNextWave() {
        // Add bonus coins based on remaining inter-wave time
        int bonusCoins = (int)(((double)(interWaveDelay - interWaveDelayCounter) / interWaveDelay) * currentWaveIndex * 50);
        if (bonusCoins > 0) {
            content.coins().addCoins(bonusCoins);
            showBonusText(bonusCoins);
        }
        
        // Start the wave
        activeWave = waves.get(currentWaveIndex);
        currentWaveIndex++;
        interWaveDelayCounter = 0;
        waveEnded = false;

        // Update WaveStatus
        content.waveStatus().setcurrentWave(currentWaveIndex);
    }

    // Checks if there are no active invaders in the game (for forcing next wave)
    private boolean noInvadersPresent() {
        for (GameObject gameObject : content.getAllGameObjects()) {
            if (gameObject instanceof Invader && gameObject.isActive()) {
                return false;
            }
        }
        return true;
    }

    // Inside your GameControl class, add a helper method:
    private int getActiveInvaderCount() {
        int count = 0;
        for (GameObject gameObject : content.getAllGameObjects()) {
            if (gameObject instanceof Invader && gameObject.isActive()) {
                count++;
            }
        }
        return count;
    }

    private void showBonusText(int bonusCoins) {
        // Create a text shape with the bonus message.
        int posX = content.coins().getLocation().x + 120;
        int posY = content.coins().getLocation().y + 16;
        bonusText = new Text("bonusText", "+" + bonusCoins, posX, posY);
        bonusText.setFontSize(15);
        bonusText.setColor(Color.WHITE);
        bonusText.setzOrder(10);
        Game.UI().canvas().addShape(bonusText);
        bonusTextCounter = 0;
    }

    public void addPendingObjects() {
        content.addPendingObjects();
    }

    public void removePendingObjects() {
        content.removePendingObjects();
    }

    public void removeInactiveGameObjects() {
        for (GameObject gameObject : content.getAllGameObjects()) {
            if (!gameObject.isActive()) {
                if (gameObject instanceof Invader) {
                    Invader invader = (Invader) gameObject;
                    if (invader.getIsKilled()) {
                        content.coins().addCoins(invader.getCoins());
                    } else if (invader.getisPassed()) {
                        content.score().addScore(invader.getScoreValue());
                    }
                }
                content.removeFromContent(gameObject.getId());
            }
        }
    }
    
    // Generalized method to spawn invaders based on type
    private void spawnInvader(String invaderType, Path path) {
        String invaderId = "invader_" + System.currentTimeMillis();
        Invader invader;
        switch(invaderType) {
            case "Troll":
                invader = new Troll(invaderId, Direction.LEFT, Action.IDLE, path);
                break;
            case "Wizard":
                invader = new Wizard(invaderId, Direction.LEFT, Action.ATTACK, path);
                break;
            case "Rat":
                invader = new Rat(invaderId, Direction.LEFT, Action.IDLE, path);
                break;
            case "Slime":
                invader = new Slime(invaderId, Direction.LEFT, Action.ATTACK, path);
                break;
            case "Wolf":
                invader = new Wolf(invaderId, Direction.LEFT, Action.IDLE, path);
                break;
            case "Knight":
                invader = new Knight(invaderId, Direction.LEFT, Action.ATTACK, path);
                break;
            case "Bee":
                invader = new Bee(invaderId, Direction.LEFT, Action.IDLE, path);
                break;
            default:
                invader = new Troll(invaderId, Direction.LEFT, Action.IDLE, path);
                break;
        }
        invader.addToCanvas();
        content.addToContent(invader);
    }    
    
    // Inner class representing a wave of spawns
    private class Wave {
        private int spawnInterval; // gameSteps between spawns within this wave
        private List<SpawnInstruction> instructions;
        private int waveStepCounter = 0;
        private int nextSpawnIndex = 0;
        
        public Wave(int spawnInterval, List<SpawnInstruction> instructions) {
            this.spawnInterval = spawnInterval;
            this.instructions = instructions;
        }
        
        // Called on each gameStep to update the wave progress
        public void update() {
            waveStepCounter++;
            if (waveStepCounter % spawnInterval == 0 && nextSpawnIndex < instructions.size()) {
                SpawnInstruction instruction = instructions.get(nextSpawnIndex);
                spawnInvader(instruction.getInvaderType(), instruction.getSpawnPath());
                nextSpawnIndex++;
            }
        }
        
        public boolean isCompleted() {
            return nextSpawnIndex >= instructions.size();
        }

        public int getTotalSpawnCount() {
            return instructions.size();
        }
    }
    
    // Called from the StartWave button to initialize and start waves
    public void startWaves() {
        waves.clear();
        
        // Define Wave 1
        List<SpawnInstruction> wave1Instructions = new ArrayList<>();
        wave1Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave1Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave1Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave1Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        waves.add(new Wave(10, wave1Instructions));
        
        // Define Wave 2
        List<SpawnInstruction> wave2Instructions = new ArrayList<>();
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave2Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        waves.add(new Wave(13, wave2Instructions));

        // Define Wave 3
        List<SpawnInstruction> wave3Instructions = new ArrayList<>();
        wave3Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Slime", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave3Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        waves.add(new Wave(14, wave3Instructions));

        // Define Wave 4
        List<SpawnInstruction> wave4Instructions = new ArrayList<>();
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave4Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        waves.add(new Wave(15, wave4Instructions));

        // Define Wave 5
        List<SpawnInstruction> wave5Instructions = new ArrayList<>();
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave5Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        waves.add(new Wave(15, wave5Instructions));

        // Define Wave 6
        List<SpawnInstruction> wave6Instructions = new ArrayList<>();
        wave6Instructions.add(new SpawnInstruction("Knight", Paths.levelTwoPath()));
        wave6Instructions.add(new SpawnInstruction("Knight", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        wave6Instructions.add(new SpawnInstruction("Wolf", Paths.levelOnePath()));
        waves.add(new Wave(15, wave6Instructions));

        // Define Wave 7
        List<SpawnInstruction> wave7Instructions = new ArrayList<>();
        wave7Instructions.add(new SpawnInstruction("Knight", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Knight", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Knight", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Rat", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Troll", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Wolf", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Wolf", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Wolf", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Wolf", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Wolf", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelTwoPath()));
        wave7Instructions.add(new SpawnInstruction("Bee", Paths.levelOnePath()));
        
        // Reset counters and flags
        currentWaveIndex = 0;
        interWaveDelayCounter = 0;
        activeWave = null;
        wavesStarted = true;
    }

    public void slowDownClicked() {
        // Reset the slowDown timer
        slowDownTimerCounter = 0;
        
        // Set the slowDown effect active and initialize the effect timer.
        slowDownEffectActive = true;
        slowDownEffectTimer = SLOWDOWN_EFFECT_DURATION;
        
        // For each invader, reduce speed to zero.
        for (GameObject gameObject : content.getAllGameObjects()) {
            if (gameObject instanceof Invader) {
                Invader invader = (Invader) gameObject;
                int currentSpeed = invader.getSpeed();
                int newSpeed = Math.max(currentSpeed - 3, 2);
                invader.setSpeed(newSpeed);
            }
        }
    }

    public void startWaveClicked() {
        if (!wavesStarted) {
            startWaves();
        }
        // Force start the next wave regardless of delay.
        if (activeWave == null && currentWaveIndex < waves.size()) {
            forceNextWave();
        }
    }
    
    public void forceNextWave() {
        // Immediately start the next wave
        if (wavesStarted && activeWave == null && currentWaveIndex < waves.size()) {
            startNextWave();
        }
    }

    // Inner class representing a spawn instruction
    private class SpawnInstruction {
        private String invaderType;
        private Path spawnPath;
        
        public SpawnInstruction(String invaderType, Path spawnPath) {
            this.invaderType = invaderType;
            this.spawnPath = spawnPath;
        }
        
        public String getInvaderType() {
            return invaderType;
        }
        
        public Path getSpawnPath() {
            return spawnPath;
        }
    }
}
