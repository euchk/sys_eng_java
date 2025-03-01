package my_game;

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
    private StartWave startWaveButton = null;
    private boolean gameOver = false;

    // Wave management
    private List<Wave> waves = new ArrayList<>();
    private Wave activeWave = null;
    private int currentWaveIndex = 0;
    private int interWaveDelay = 5000; // Number of gameSteps between waves
    private int interWaveDelayCounter = 0;
    private boolean wavesStarted = false;
    private boolean waveEnded = false;
    private int lastWaveTotalSpawnCount = 0;

    
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
        
        addPendingObjects();
        removeInactiveGameObjects();
        removePendingObjects();

        // Wave spawning logic
        if (wavesStarted) {
            if (activeWave == null && currentWaveIndex < waves.size()) {
                // Increment the delay counter each gameStep when no wave is active
                interWaveDelayCounter++;
        
                // If there are no invaders force the next wave
                if (noInvadersPresent()) {
                    if (startWaveButton != null) {
                        startWaveButton.removeFromCanvas();
                        startWaveButton = null;
                    }
                    startNextWave();
                } else {
                    // Allow user to start next wave if current wave is almost done
                    if (waveEnded && getActiveInvaderCount() < 0.3 * lastWaveTotalSpawnCount && startWaveButton == null) {
                        startWaveButton = new StartWave("startWaveButton", 1800, 50, this);
                        startWaveButton.addToCanvas();
                    }
        
                    // When full delay is reached start the wave
                    if (interWaveDelayCounter >= interWaveDelay) {
                        if (startWaveButton != null) {
                            startWaveButton.removeFromCanvas();
                            startWaveButton = null;
                        }
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
        
        // Check game over conditions
        // Losing because of max invaders passed the gate
        if (content.score().getCurrentScore() >= content.score().getMaxInvadersPassed()) {
            gameOver = true;
            Text losingText = new Text("game over", "YOU LOST!", 700, 350);
            losingText.setFontSize(50);
            canvas.addShape(losingText);
            return;
        }

        // Winning because all waves ended and no active invaders
        if (wavesStarted && currentWaveIndex >= waves.size() && activeWave == null && noInvadersPresent()) {
            gameOver = true;
            Text victoryText = new Text("victory", "YOU WIN!", 700, 350);
            victoryText.setFontSize(50);
            canvas.addShape(victoryText);
            return;
        }
        
        canvas.revalidate();
        canvas.repaint();
    }

    private void startNextWave() {
        // Add bonus coins based on remaining inter-wave time
        int bonusCoins = (int)(((double)(interWaveDelay - interWaveDelayCounter) / interWaveDelay) * currentWaveIndex * 50);
        content.coins().addCoins(bonusCoins);
        
        activeWave = waves.get(currentWaveIndex);
        currentWaveIndex++;
        interWaveDelayCounter = 0;
        waveEnded = false;
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
                        content.score().increment();
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
        waves.add(new Wave(10, wave2Instructions));

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
        waves.add(new Wave(12, wave3Instructions));

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
        waves.add(new Wave(13, wave4Instructions));
        
        // Reset counters and flags
        currentWaveIndex = 0;
        interWaveDelayCounter = 0;
        activeWave = null;
        wavesStarted = true;
    }

    public void startWaveClicked() {
        if (!wavesStarted) {
            startWaves();
        }
        // Force start the next wave regardless of delay.
        else if (activeWave == null && currentWaveIndex < waves.size()) {
            // Add bonus coins if user started wave before time (relative to time passed)
            int bonusCoins = (int)(((double)(interWaveDelay - interWaveDelayCounter) / interWaveDelay) * 50);
            content.coins().addCoins(bonusCoins);
            forceNextWave();
        }
    }
    
    public void forceNextWave() {
        // Remove the button if it's currently displayed.
        if (startWaveButton != null) {
            startWaveButton.removeFromCanvas();
            startWaveButton = null;
        }
        // Immediately start the next wave.
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
