package my_game;

import base.Game;
import base.GameCanvas;
import shapes.TextLabel;

public class Score {
    private int enemiesPassed;
    private int maxEnemiesPassed;
    private TextLabel scoreText;

    public Score(int maxEnemiesPassed, int posX, int posY) {
        this.enemiesPassed = 0;
        this.maxEnemiesPassed = maxEnemiesPassed;

        // Create a TextLabel to display the score
        scoreText = new TextLabel("scoreDisplay", "Score: " + enemiesPassed + "/" + maxEnemiesPassed, posX, posY);
        scoreText.setFontSize(50);
        scoreText.setzOrder(10); // Set a high z-order to keep it visible
    }

    // Increment the score when an enemy passes the gate
    public void increment() {
        enemiesPassed++;
        updateText();
    }

    // Get the current score
    public int getCurrentScore() {
        return enemiesPassed;
    }

    public int getMaxEnemiesPassed() {
        return maxEnemiesPassed;
    }

    // Update the displayed score
    private void updateText() {
        scoreText.setText("Score: " + enemiesPassed + "/" + maxEnemiesPassed);
    }

    // Add the score display to the canvas
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(scoreText);
        canvas.revalidate();
        canvas.repaint();
    }
}
