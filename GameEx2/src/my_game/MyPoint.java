package my_game;

public class MyPoint {

    private int x;
    private int y;

    // Constructor without parameters
    public MyPoint(){
        this.x = 0;
        this.y = 0;
    }

    // Constructor with parameters
    public MyPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    // Getter and setter
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    // Overide toString to change formatting
    @Override
    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }

    public double getDistance(MyPoint p){
        return Math.sqrt(Math.pow(this.x - p.x, 2) + Math.pow(this.y - p.y, 2));
    }

    // Tests
    public static void main(String[] args) {
        /* 
        // Testing constructions and toString
        MyPoint p1 = new MyPoint();
        MyPoint p2 = new MyPoint(3, 4);
        MyPoint p3 = new MyPoint(-4, -3);

        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p3: " + p3);

        // Testing getDistance
        System.out.println("Distance between p1 and p2: " + p1.getDistance(p2));   
        System.out.println("Distance between p1 and p3: " + p1.getDistance(p3)); 
        System.out.println("Distance between p2 and p3: " + p2.getDistance(p3));
        
        */
    }
}