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

        MyCircle mc1 = new MyCircle(180, 200, 50);
        MyCircle mc2 = new MyCircle(200, 100, 60);
        MyCircle mc3 = new MyCircle(350, 190, 70);
        MyPoint mp1 = new MyPoint(190, 240);
        MyPoint mp2 = new MyPoint(60, 160);

        // Fill in your details
        String student1 = "Yaacov Levy" + ", " + "201638640";
        String student2 = "Matan Jefidoff" + ", " + "039986633";
        // String student3 = "FullName" + ", " + "ID";

        // Write here the code that performs all the checks and prints the results

        // Check if mp1 is inside mc1
        if (mc1.pointInCircle(mp1)){
            System.out.println("mp1 " + mp1 + " is indeed inside mc1 " + mc1);
        }
        else{
            System.out.println("mp1 " + mp1 + " is NOT inside mc1 " + mc1);
        }

        // Check if mp2 is inside mc2
        if (mc2.pointInCircle(mp2)){
            System.out.println("mp2 " + mp2 + " is indeed inside mc2 " + mc2);
        }
        else{
            System.out.println("mp2 " + mp2 + " is NOT inside mc2 " + mc2);
        }

        // Check if mc1 intersects with mc2
        if (mc1.intersects(mc2)){
            System.out.println("mc1 " + mc1 + " intersects with mc2 " + mc2);
        }
        else{
            System.out.println("mc1 " + mc1 + " DOES NOT intersects with mc2 " + mc2);
        }

        // Check if mc2 intersects with mc3
        if (mc2.intersects(mc3)){
            System.out.println("mc2 " + mc2 + " intersects with mc3 " + mc3);
        }
        else{
            System.out.println("mc2 " + mc2 + " DOES NOT intersects with mc3 " + mc3);
        }

        System.out.println("Submited by: ");
        System.out.println(student1);
        System.out.println(student2);
        
    
        //--------------------------------------------------------------------------
        // -------------- This is where your code ends -----------------------------
        //--------------------------------------------------------------------------

        // The following code visualizes your shapes using a part of the game infrastructure
        // You have to read, understand and modify the visual representation of the elements ...

        // Visualize your circles with the infrastructure circle shapes
        // Add the center of each circle as points

        Circle c1 = new Circle("c1", mc1.getCenter().getX(), mc1.getCenter().getY(), mc1.getRadius());
        c1.setColor(Color.BLUE);
        Circle pc1 = new Circle("pc1", mc1.getCenter().getX(), mc1.getCenter().getY(),3);
        pc1.setColor(Color.BLUE);
        pc1.setIsFilled(true);

        Circle c2 = new Circle("c2", mc2.getCenter().getX(), mc2.getCenter().getY(), mc2.getRadius());
        c2.setColor(Color.GRAY);
        Circle pc2 = new Circle("pc2", mc2.getCenter().getX(), mc2.getCenter().getY(),3);
        pc2.setColor(Color.GRAY);
        pc2.setIsFilled(true);

        Circle c3 = new Circle("c3", mc3.getCenter().getX(), mc3.getCenter().getY(), mc3.getRadius());
        c3.setColor(Color.ORANGE);
        Circle pc3 = new Circle("pc3", mc3.getCenter().getX(), mc3.getCenter().getY(),3);
        pc3.setColor(Color.ORANGE);
        pc3.setIsFilled(true);


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
        canvas.addShape(pc1);
        canvas.addShape(new Text("mc1_t", mc1.getCenter().toString(), pc1.getPosX(), pc1.getPosY()));

		canvas.addShape(c2);
        canvas.addShape(pc2);
        canvas.addShape(new Text("mc2_t", mc2.getCenter().toString(), pc2.getPosX(), pc2.getPosY()));

		canvas.addShape(c3);
        canvas.addShape(pc3);
        canvas.addShape(new Text("mc3_t", mc3.getCenter().toString(), pc3.getPosX(), pc3.getPosY()));

		canvas.addShape(p1);
        canvas.addShape(new Text("p1_t", mp1.toString(), p1.getPosX(), p1.getPosY()));
        canvas.addShape(p2);
        canvas.addShape(new Text("p2_t", mp2.toString(), p2.getPosX(), p2.getPosY()));

        canvas.addShape(l1);
        
        
        
        // Texts are also shapes. Show your details as texts at the bottom of the screen.
        canvas.addShape(new Text("st1", student1, 50, 400));
        canvas.addShape(new Text("st2", student2, 50, 450));
        // canvas.addShape(new Text("st3", student3, 50, 500));
    }
}