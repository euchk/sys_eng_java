package my_game;

public class MyCircle {
    private MyPoint center;
    private double radius;
    
    // Constructor with 3 parameters
    public MyCircle(double x, double y, double radius){
        this.center = new MyPoint(x, y);
        this.radius = radius;
    }

    // Constructor with 2 parameters
    public MyCircle(MyPoint center, double radius){
        this.center = center;
        this.radius = radius;
    }

    // Getter and setter
    public MyPoint getCenter(){
        return this.center;
    }

    public double getradius(){
        return this.radius;
    }

    public void setCenter(MyPoint center){
        this.center = center;
    }

    public void setradius(double radius){
        if (radius >= 0){
            this.radius = radius;
        }
        else{
            System.out.println("Radius can't be negative"); // TODO: change to expection
        }
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

    // Tests
    public static void main(String[] args) {
        MyCircle c1 = new MyCircle(1, -1, 14);
        MyPoint p1 = new MyPoint();
        MyCircle c2 = new MyCircle(p1, 1);

        System.out.println("c1: " + c1);
        System.out.println("c2: " + c2);

        MyPoint p2 = new MyPoint(-1, -1);
        c1.setCenter(p2);
        c1.setradius(100);
        System.out.println("c1: " + c1);

        c1.setradius(-9);
        System.out.println("c1: " + c1 + ", p1: " + p1);
        System.out.println("p1 is in c1? " + c1.pointInCircle(p1));
        c1.setradius(0.1);
        System.out.println("c1: " + c1 + ", p1: " + p1);
        System.out.println("p1 is in c1? " + c1.pointInCircle(p1));


    }

}