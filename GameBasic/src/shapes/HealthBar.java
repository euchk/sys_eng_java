package shapes;

import java.awt.Color;

import base.Game;
import base.GameCanvas;

public class HealthBar extends Rectangle {    
    private int maxWidth;
    private int maxHealth = 100; // Default value
    private int currentHealth;
    private boolean isVisible = true; // Display the bar on the canvas

    public HealthBar(String id, int x, int y, int width, int height) {
        super(id, x, y, width, height);
        this.maxWidth = width;
        setMaxHealth(maxHealth);
        setColor(Color.GREEN); // Initial color for full health
    }

    public void reduceHealth(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        updateHealthBar();
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        updateHealthBar();
    }

    private void updateHealthBar() {
        // Update width proportionally to current health
        int newWidth = (int) ((double) currentHealth / maxHealth * maxWidth);
        setWidth(newWidth);

        // Update color based on health percentage
        double healthPercentage = (double) currentHealth / maxHealth;
        if (healthPercentage > 0.5) {
            setColor(Color.GREEN);
        } else if (healthPercentage > 0.2) {
            setColor(Color.ORANGE);
        } else {
            setColor(Color.RED);
        }
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setIsVisible(boolean isVisible) { 
        this.isVisible = isVisible;
    }

    public void addToCanvas() {
        if (!isVisible) return;

        GameCanvas canvas = Game.UI().canvas();
        setzOrder(1);
        canvas.addShape(this);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        if (!isVisible) return;

        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(getId());
        canvas.revalidate();
        canvas.repaint();
    }

}
