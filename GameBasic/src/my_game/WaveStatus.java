
package my_game;

import java.awt.Color;
import base.Game;
import base.GameCanvas;
import shapes.TextLabel;

public class WaveStatus {
    private int currentWave;
    private int totalWaves;
    private TextLabel waveStatusText;

    public WaveStatus(int totalWaves, int posX, int posY) {
        this.currentWave = 0;
        this.totalWaves = totalWaves;
        waveStatusText = new TextLabel("waveStatusDisplay", "Wave: " + currentWave + "/" + totalWaves, posX, posY);
        waveStatusText.setColor(Color.WHITE);  // Must be before setFontSize
        waveStatusText.setFontSize(20);
        waveStatusText.setzOrder(10);
    }

    public void setcurrentWave(int currentWave) {
        this.currentWave = currentWave;
        updateText();
    }

    public int getcurrentWave() {
        return currentWave;
    }

    public int getTotalWaves() {
        return totalWaves;
    }

    // Update the displayed text.
    private void updateText() {
        waveStatusText.setText("Wave: " + currentWave + "/" + totalWaves);
    }

    // Add the wave status display to the canvas.
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(waveStatusText);
        canvas.revalidate();
        canvas.repaint();
    }
}
