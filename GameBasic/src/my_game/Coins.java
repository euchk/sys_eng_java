package my_game;

import java.awt.Color;

import base.Game;
import base.GameCanvas;
import shapes.TextLabel;

public class Coins {
    private int currentCoins;
    private TextLabel coinsText;


    public Coins(int initialCoins, int posX, int posY) {
        if (initialCoins < 0) {
            throw new IllegalArgumentException("Initial coins cannot be negative.");
        }
        this.currentCoins = initialCoins;

        // Create a TextLabel to display the coins
        coinsText = new TextLabel("coinsDisplay", "Coins: " + currentCoins, posX, posY);
        coinsText.setFontSize(50);
        coinsText.setzOrder(10); // Set a high z-order to keep it visible
    }

    // Add coins
    public void addCoins(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add a negative amount of coins.");
        }
        currentCoins += amount;
        updateText();
    }

    // Spend coins
    public boolean spendCoins(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot spend a negative amount of coins.");
        }
        if (currentCoins >= amount) {
            currentCoins -= amount;
            updateText();
            return true; // Spend successful
        }
        return false; // Not enough coins
    }

    // Get current coin count
    public int getCurrentCoins() {
        return currentCoins;
    }

    // Update the displayed coin count
    private void updateText() {
        coinsText.setText("Coins: " + currentCoins);
    }

    // Add the coin display to the canvas
    public void addToCanvas() {
        GameCanvas canvas = Game.UI().canvas();
        canvas.addShape(coinsText);
        canvas.revalidate();
        canvas.repaint();
    }
}
