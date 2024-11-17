package my_game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import shapes.Circle;
import shapes.Line;
import shapes.Shape;



/**
 * A 2D screen that displays graphical shapes and enables to set their location at runtime, causing an animation effect.
 * 
 * <h1>Properties:</h1>
 * <ul>
 * <li>int width - the screen width, in pixels<br>
 * <li>int height - the screen hight, in pixels<br>
 * <li>borderColor - the color of the screen border, from a list of given colors [red, black, yellow, gray, white]<br>
 * <li>borderWidth - the width of the screen border, in pixels (0 if no border)<br>
 * <li>int positionX, positionY - the screen position, in pixels<br>
 * </ul>
 * 
 * <h1>Methods:</h1>
 * <ul>
 * 	<li>void addShape(shape) - adds a graphical shape, identified by id, to the screen.<br>
 * 	<li>void moveShape(id, dx, dy) - moves a shape identified by id by dx and dy pixels respectively.
 * 		For animation effect, one can execute the moveShape(id, dx, dy) in a loop with a time-based wait condition between each moveObject call.
 * 	<br>
 * 	<li>void moveToLocation(id, cordX, cordY) - moves an shape identified by id to coordinates cordX and cordY respectively.<br>
 * 	<li>void deleteShape(id) - permanently removes a shape identified by id from the screen.<br>
 * 	<li>void flipStatus(id) - changes the status of a shape and shows or hide it accordingly.<br>
 *  <il>void show(id) - shows a shape identified by id.<br>
 *  <il>void hide(id) - hides a shape identified by id.<br>
 *  <il>void showAll() shows all shapes.<br>
 *  <il>void hideAll() - hides all shapes.<br>
 *  <il>void deleteAll() - deletes all shapes from the screen.<br>
 * </ul>
 * 
 */
public class GameCanvas extends JPanel  {
	
	private static final long serialVersionUID = 1L;
	
	private final Map<String, Shape> shapes;

	int borderWidth;
	
	int positionX;
	int positionY;
		
	public GameCanvas() {
		super();
		this.setBackground(Color.WHITE);
		this.shapes = new HashMap<>();
		// Not relevant -> will be assigned by the default values in the 
		this.setLayout(null);
	}

	public void addShape(Shape shape) {
		shapes.put(shape.getId(), shape);
		this.repaint();
	}
	
	public Shape getShape(String id) {
		return shapes.get(id);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		 super.paintComponent(g);

		 // While iterating over a hashmap, we are not allowed to modify it.
		 // Store shapes hashmap into an array so that there will be no concurrent actions
		 // in the hashmap.
		 Shape[] tempShapes = shapes.values().toArray(new Shape[0]);
		 	 
		 for (int i = 0; i < tempShapes.length; i++) {
			tempShapes[i].draw((Graphics2D) g);
		}
	}


	public static void main(String[] args) {
		
		//Create a frame window and set its name, size and behavior when clicking the X
		JFrame frame = new JFrame("My Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 1000);
		
		//Create a canvas
		GameCanvas screen = new GameCanvas();
		//Add the canvas to the frame and show it
		frame.getContentPane().add(screen);
		frame.setVisible(true);
	
		
		//Add shapes
		Circle c1 = new Circle("c1", 100,100,100);
		c1.setIsFilled(true);
		c1.setFillColor(Color.BLUE);
		screen.addShape(c1);
		screen.addShape(new Line("l1", 20,20,120,120));	
	
	}

}
