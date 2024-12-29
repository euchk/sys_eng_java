package my_game;

import base.Game;
import base.GameCanvas;
import shapes.TextLabel;

public class Score {
    private int invadersPassed;
    private int maxInvadersPassed;
    private TextLabel scoreText;

    public Score(int maxInvadersPassed, int posX, int posY) {
        this.invadersPassed = 0;
        this.maxInvadersPassed = maxInvadersPassed;

        // Create a TextLabel to display the score
        scoreText = new TextLabel("scoreDisplay", "Invaders passed: " + invadersPassed + "/" + maxInvadersPassed, posX, posY);
        scoreText.setFontSize(50);
        scoreText.setzOrder(10); // Set a high z-order to keep it visible
    }

    // Increment the score when an enemy passes the gate
    public void increment() {
        invadersPassed++;
        updateText();
    }

    // Get the current score
    public int getCurrentScore() {
        return invadersPassed;
    }

    public int getMaxInvadersPassed() {
        return maxInvadersPassed;
    }

    // Update the displayed score
    private void updateText() {
        scoreText.setText("Invaders passed: " + invadersPassed + "/" + maxInvadersPassed);
    }

    // Add the score display to the canvas
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(scoreText);
        canvas.revalidate();
        canvas.repaint();
    }
}
