package my_game;

import shapes.Circle;
import shapes.Line;
import shapes.Text;

import java.awt.Color;

import javax.swing.JFrame;

public class Ex2 {

    private JFrame frame;
    private GameCanvas canvas;

    private void loadCanvas() {
        // Create a frame window and set its name, size and behavior when clicking the X
        frame = new JFrame("My Screen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        canvas = new GameCanvas();

        // Add the canvas to the frame and show it
        frame.getContentPane().add(canvas);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        /*
         * Create a canvas and load it - Do not touch !
         */
        Ex2 ex2 = new Ex2();
        ex2.loadCanvas();
        GameCanvas canvas = ex2.canvas;
        
        //--------------------------------------------------------------------------
        // -------------- This is where your code starts ---------------------------
        //--------------------------------------------------------------------------

        // Remove the nulls and create 3 circles and 2 points (assume an area of 400 X 400)

        MyCircle mc1 = new MyCircle(200, 200, 50);
        MyCircle mc2 = new MyCircle(200, 100, 60);
        MyCircle mc3 = new MyCircle(350, 350, 70);
        MyPoint mp1 = new MyPoint(200, 220);
        MyPoint mp2 = new MyPoint(60, 60);

        // Fill in your details
        String student1 = "FullName" + ", " + "ID";
        String student2 = "FullName" + ", " + "ID";
        String student3 = "FullName" + ", " + "ID";

        // Write here the code that performs all the checks and prints the results

    
        //--------------------------------------------------------------------------
        // -------------- This is where your code ends -----------------------------
        //--------------------------------------------------------------------------

        // The following code visualizes your shapes using a part of the game infrastructure
        // You have to read, understand and modify the visual representation of the elements ...

        // Visualize your circles with the infrastructure circle shapes
        Circle c1 = new Circle("c1", mc1.getCenter().getX(), mc1.getCenter().getY(), mc1.getRadius());
        Circle c2 = new Circle("c2", mc2.getCenter().getX(), mc2.getCenter().getY(), mc2.getRadius());
        Circle c3 = new Circle("c3", mc3.getCenter().getX(), mc3.getCenter().getY(), mc3.getRadius());

        // Visualize the points with red filled circles
        Circle p1 = new Circle("p1", mp1.getX(), mp1.getY(),3);
        p1.setColor(Color.RED);
        p1.setIsFilled(true);
        Circle p2 = new Circle("p2", mp2.getX(), mp2.getY(),3);
        p2.setColor(Color.RED);
        p2.setIsFilled(true);

        // Visualize a line between the two intersecting circles
        Line l1 = new Line("l1", c1.getPosX(), c1.getPosY(), c2.getPosX(), c2.getPosY());
        l1.setColor(Color.green);

        // Add all the shapes to the canvas
		canvas.addShape(c1);
		canvas.addShape(c2);
		canvas.addShape(c3);
		canvas.addShape(p1);
        canvas.addShape(p2);
        canvas.addShape(l1);
        
        // Texts are also shapes. Show your details as texts at the bottom of the screen.
        canvas.addShape(new Text("st1", student1, 50, 400));
        canvas.addShape(new Text("st2", student2, 50, 450));
        canvas.addShape(new Text("st3", student3, 50, 500));
    }
}