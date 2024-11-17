// name: Yaacov Levy
// ID: 201638640

package main.java;

import java.util.Scanner;

public class Ex1 {

    // Create a shared Scanner instance
    static Scanner scn = new Scanner(System.in);

    public static void unitTransformer() {
        
        // Get input from user
        System.out.println("Enter a measurement in inches:");
        double x_inches = scn.nextDouble();

        // Calculate millimeters
        double x_millimeters = x_inches * 25.4; // 1 inch = 25.4 millimeters
        
        // Print output
        System.out.println(x_inches + " inches are " + x_millimeters + " millimeters.");
        
    }

    public static void calcVAT() {
        
        // Get input from user
        System.out.println("Enter product price without VAT:");
        double priceWithoutVAT = scn.nextDouble();
        
        // Calculate the VAT
        double priceWithVAT = priceWithoutVAT * 1.17; // 1.18 after 1/1/25...
        double VAT = priceWithVAT - priceWithoutVAT;

        // Print output and close scanner
        System.out.println("The VAT is " +  VAT + " and the price including the VAT is "
                            + priceWithVAT + ".");
    }

    public static double rideCost(double distanceInKm, double fuelCostPerLiter, double kmPerLiter){
        
        // Calculate the ride Cost
        return (distanceInKm / kmPerLiter) * fuelCostPerLiter;
    }

    public static int seriesSum(int[] series) {

        // Check for a valid input
        if (series.length > 0){
            
            // Add the first number to the sum seperately 
            int sum = series[0]; 

            // Add the next number to the sum as long as the series is rising
            for (int i = 1; i < series.length; i++) {  
                if (series[i] > series[i-1]){
                    sum += series[i];
                }
                else{
                    return sum; // Series is no longer rising
                }                
            }
            return sum; 
        }
        else{
            return 0; // Invalid input
        }
    }
        
    public static void main(String[] args) {
        
        // 1. unitTransformer
        unitTransformer(); 

        // 2. calcVAT
        calcVAT();

        // 3. rideCost
        double distanceInKm = 17;
        double fuelCostPerLiter = 6.20;
        double kmPerLiter = 8.5;
        double rideCost = rideCost(distanceInKm, fuelCostPerLiter, kmPerLiter);
        System.out.println("The cost of a " + distanceInKm + "km ride will be "
        + rideCost);

        // 4. seriesSum
        int[] series = {2, 4, 6, 5};
        int sum = seriesSum(series);
        System.out.println("The sum of the series is: " + sum);

        // Close the scanner
        scn.close();

    }
}
    