package net.htlgkr.note;

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        System.out.println(Punktezahlen.MARKLIST);

        for (double points = 0; points <= 120; points = points+0.5){
            System.out.println("Points: " + points + " -> Mark: " + Punktezahlen.getMarkFromPoints(points));
        }
    }
}