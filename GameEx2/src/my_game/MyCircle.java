package my_game;

public class MyCircle {
    private MyPoint center;
    private int radius;
    
    // Constructor with 3 parameters
    public MyCircle(int x, int y, int radius){
        if (radius < 0){
            throw new IllegalArgumentException("Radius must be non-negative");
        }
        this.radius = radius;
        this.center = new MyPoint(x, y);
        
    }

    // Constructor with 2 parameters
    public MyCircle(MyPoint center, int radius){
        if (radius < 0){
            throw new IllegalArgumentException("Radius must be non-negative");
        }
        this.center = center;
        this.radius = radius;
    }

    // Getter and setter
    public MyPoint getCenter(){
        return this.center;
    }

    public int getRadius(){
        return this.radius;
    }

    public void setCenter(MyPoint center){
        this.center = center;
    }

    public void setRadius(int radius){
        if (radius < 0){
            throw new IllegalArgumentException("Radius must be non-negative");
        }
        this.radius = radius;
    }

    // Overide toString to change formatting
    @Override
    public String toString() {
        return "[" + center + ", " + radius + "]";
    }

    public boolean pointInCircle(MyPoint p){
        /* Check if the distance between the center of the circle
           and the input point is less than the radius
        */
        return center.getDistance(p) <= radius;
    }

    public boolean intersects(MyCircle c){
        /* Check if the distance between the centers of the circles
           is less than the the sum of the radii
        */
        return center.getDistance(c.center) <= radius + c.radius;
    }

    public static void main(String[] args) {
        /*
        // Testing constructions and toString
        MyPoint p1 = new MyPoint();
        MyCircle c1 = new MyCircle(p1, 1);
        System.out.println("c1: " + c1);

        MyCircle c2 = new MyCircle(1, -5, 1);
        System.out.println("c2: " + c2);

        try{
            MyCircle c_minus = new MyCircle(0, 0, -1);
            System.out.println("c_minus: " + c_minus);
        }
        catch(IllegalArgumentException e){
            System.out.println("Failed creating instance: " + e.getMessage());
        }
        
        // Testing getter and setter
        System.out.println("Editing c1");
        p1.setX(1);
        p1.setY(5);
        c1.setCenter(p1);
        c1.setRadius(2);
        System.out.println("c1: " + c1);

        // Testing pointInCircle()
        System.out.println("p1: " + p1);
        System.out.println("p1 is in c1? " + c1.pointInCircle(p1)); // Expects True
        MyPoint p2 = new MyPoint(1, 2);
        System.out.println("p2: " + p2);
        System.out.println("p2 is in c1? " + c1.pointInCircle(p2)); // Expects False

        // Testing intersects()
        System.out.println("c1 and c2 intersect? " + c1.intersects(c2)); // Expects False
        System.out.println("Editing c2");
        c2.setRadius(8);
        System.out.println("c2: " + c2);
        System.out.println("c1 and c2 intersect? " + c1.intersects(c2)); // Expects True
        
         */
    }

}