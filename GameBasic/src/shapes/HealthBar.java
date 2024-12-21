package shapes;

import java.awt.Color;

public class HealthBar extends Rectangle {    
    private int maxWidth;
    private int maxHealth;
    private int currentHealth;

    public HealthBar(String id, int x, int y, int width, int height, int maxHealth) {
        super(id, x, y, width, height);
        this.maxWidth = width;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        setColor(Color.GREEN); // Initial color for full health
    }

    public void reduceHealth(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) {
            currentHealth = 0;
        }
        updateHealthBar();
    }

    public void setHealth(int health) {
        this.currentHealth = Math.min(maxHealth, Math.max(0, health));
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
}
