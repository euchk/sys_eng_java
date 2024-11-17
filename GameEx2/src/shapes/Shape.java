package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
	
	public enum STATUS {
		HIDE, SHOW
	}
		

	private final String id;
	private STATUS status;
	private Color color = Color.BLUE;
	private int weight = 2;
	
	public Shape(String id) {
		this.id = id;
		this.status = STATUS.SHOW;
	}
	public String getId() {
		return id;
	}
	public STATUS getStatus() {
		return status;
	}
	public void setStatus(STATUS status) {
		this.status = status;
	}
	
	public void setColor (Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	/*
	 * The following methods must be implemented by the classes (specific shapes) that
	 * extend this class
	 */
	public void draw(Graphics2D g) {
		g.setColor(this.color);
		g.setStroke(new BasicStroke(weight));
	}
	
	public abstract void move(int dx, int dy);
	public abstract void moveToLocation(int x, int y);
	public abstract boolean isInArea(int x, int y);
	
		

}
