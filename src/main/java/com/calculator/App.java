package com.calculator;

import java.util.Scanner;


public class App {

    public static double sqrt(double x){
        return Math.sqrt(x);
    }

    public static long factorial(int x){
        long f = 1;
        for(int i=1;i<=x;i++){
            f *= i;
        }
        return f;
    }

    public static double log(double x){
        return Math.log(x);
    }

    public static double power(double x,double y){
        return Math.pow(x,y);
    }
    public static double devide(double x,double y){
        return x/y;
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        while(true){
            
            System.out.println("1. Square Root");
            System.out.println("2. Factorial");
            System.out.println("3. Natural Log");
            System.out.println("4. Power");
            System.out.println("5. Devide(new)");
            System.out.println("6. Exit");

            int ch = sc.nextInt();

            switch(ch){

                case 1:
                    System.out.println("Enter number:");
                    System.out.println(sqrt(sc.nextDouble()));
                    break;

                case 2:
                    System.out.println("Enter number:");
                    System.out.println(factorial(sc.nextInt()));
                    break;

                case 3:
                    System.out.println("Enter number:");
                    System.out.println(log(sc.nextDouble()));
                    break;

                case 4:
                    System.out.println("Enter base and power:");
                    System.out.println(power(sc.nextDouble(),sc.nextDouble()));
                    break;

                case 5:
                    System.out.println("Enter two numbers:");
                    System.out.println(devide(sc.nextDouble(),sc.nextDouble()));
                    break;  
                case 6:
                    System.exit(0);
            }
        }
    }
}

