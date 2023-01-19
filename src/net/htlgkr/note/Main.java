package net.htlgkr.notenundgame;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);;
        System.out.println(Punktezahlen.marklist);
        System.out.println("Enter points: ");
        double points = input.nextDouble();
        double mark = Punktezahlen.getMarkFromPoints(points);
        System.out.println("Mark: " + mark);
    }
}
