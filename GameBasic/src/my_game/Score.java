package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import shapes.TextLabel;

public class Score {
    private int invadersPassed;
    private int maxInvadersPassed;
    private int lifeRemaining;
    private TextLabel scoreText;

    public Score(int maxInvadersPassed, int posX, int posY) {
        this.invadersPassed = 0;
        this.maxInvadersPassed = maxInvadersPassed;
        this.lifeRemaining = maxInvadersPassed - invadersPassed;

        // Create a TextLabel to display the score
        scoreText = new TextLabel("scoreDisplay", "Life: " + lifeRemaining + "/" + maxInvadersPassed, posX, posY);
        scoreText.setColor(Color.BLACK); // Color must be before setFontSize
        scoreText.setFontSize(20);
        scoreText.setzOrder(10);
    }

    // Increment the score when an enemy passes the gate
    public void addScore(int scoreValue) {
        invadersPassed += scoreValue;
        lifeRemaining = maxInvadersPassed - invadersPassed;
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
        scoreText.setText("Life: " + lifeRemaining + "/" + maxInvadersPassed);
    }

    // Add the score display to the canvas
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(scoreText);
        canvas.revalidate();
        canvas.repaint();
    }
}
