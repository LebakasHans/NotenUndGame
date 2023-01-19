package net.htlgkr.notenundgame;

public class Punktezahlen {
    final static String marklist = """
            +-----+---------------+
            |Note |   Punkte      |
            +-----+---------------+
            |5,0  |   0 - 49,5    |
            |4,7  |   50 - 59,5   |
            |4,0  |   59,5 - 64,5 |
            |3,7  |   65 - 69,5   |
            |3,3  |   70 - 74,5   |
            |3,0  |   75 - 79,5   |
            |2,7  |   80 - 84,5   |
            |2,3  |   85 - 89,5   |
            |2,0  |   90 - 94,5   |
            |1,7  |   95 - 99,5   |
            |1,3  |   100 - 104,5 |
            |1,0  |   105 - 120   |
            +---------------------+""";

    public static double getMarkFromPoints(double points){
        double mark = switch ((int) Math.floor(points/5)){
            case 0, 1, 2 ,3 ,4, 5, 6, 7, 8 , 9 -> 5;
            case 10, 11 -> 4.7;
            case 12 -> 4.0;
            case 13 -> 3.7;
            case 14 -> 3.3;
            case 15 -> 3.0;
            case 16 -> 2.7;
            case 17 -> 2.3;
            case 18 -> 2.0;
            case 19 -> 1.7;
            case 20 -> 1.3;
            case 21, 22, 23, 24 -> 1;
            default -> throw new IllegalArgumentException("Invalid points");
        };
        return mark;
    }
}