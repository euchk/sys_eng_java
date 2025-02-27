package shapes;

import java.awt.Color;

import base.Game;
import base.GameCanvas;

public class HealthBar {    
    private Rectangle healthBarRectangle;
    private Rectangle healthBarMaxRectangle;
    private int maxWidth;
    private int maxHealth = 100; // Default value
    private int currentHealth;
    private boolean isVisible = true; // Display the bar on the canvas

    public HealthBar(String id, int x, int y, int width, int height) {
        
        this.maxWidth = width;

        // Health bar
        this.healthBarRectangle = new Rectangle(id, x, y, width, height);
        this.healthBarRectangle.setIsFilled(true);
        this.healthBarRectangle.setFillColor(Color.GREEN);
        this.healthBarRectangle.setColor(Color.GREEN);
        this.healthBarRectangle.setWeight(0);
        this.healthBarRectangle.setzOrder(10);

        // Health bar background
        this.healthBarMaxRectangle = new Rectangle(id + "_bg", x, y, width, height);
        this.healthBarMaxRectangle.setIsFilled(true);
        this.healthBarMaxRectangle.setFillColor(Color.WHITE);
        this.healthBarMaxRectangle.setColor(Color.WHITE);
        this.healthBarMaxRectangle.setWeight(0);
        this.healthBarMaxRectangle.setzOrder(9);

        setMaxHealth(maxHealth);
        
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
        healthBarRectangle.setWidth(newWidth);

        // Update color based on health percentage
        double healthPercentage = (double) currentHealth / maxHealth;
        if (healthPercentage > 0.6) {
            healthBarRectangle.setFillColor(Color.GREEN);
            healthBarRectangle.setColor(Color.GREEN);
        } else if (healthPercentage > 0.3) {
            healthBarRectangle.setFillColor(Color.ORANGE);
            healthBarRectangle.setColor(Color.ORANGE);
        } else {
            healthBarRectangle.setFillColor(Color.RED);
            healthBarRectangle.setColor(Color.RED);
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
        canvas.addShape(healthBarRectangle);
        canvas.addShape(healthBarMaxRectangle);
        canvas.revalidate();
        canvas.repaint();
    }

    public void removeFromCanvas() {
        if (!isVisible) return;

        GameCanvas canvas = Game.UI().canvas();
        canvas.deleteShape(healthBarRectangle.getId());
        canvas.deleteShape(healthBarMaxRectangle.getId());
        canvas.revalidate();
        canvas.repaint();
    }

    public void move(int dx, int dy) {
		healthBarRectangle.move(dx, dy);
		healthBarMaxRectangle.move(dx, dy);
	}

    public void moveToLocation(int x, int y) {
		healthBarRectangle.moveToLocation(x, y);
		healthBarMaxRectangle.moveToLocation(x, y);
	}

}
