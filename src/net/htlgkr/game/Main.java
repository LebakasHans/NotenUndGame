package net.htlgkr.game;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);

    /**
     *  I do not see any kind of problem with this method
     */
    public static void main(String[] args){
        Difficulty difficulty = chooseDifficulty();
        Game game = new Game(difficulty);
        game.printLegend();

        game.printMap();
        String input;
        Direction direction;
        do {
            try {
                input = scanner.nextLine();
                if (!input.equals("")) {

                    if (useFastFunction(input)) {
                        int moves = Integer.parseInt(input.split(" ")[1]);
                        direction = Direction.valueOf(
                                input.split(" ")[0].toUpperCase().trim()
                        );
                        for (int i = 0; i < moves; i++) {
                            game.movePlayer(direction);
                        }
                    } else {
                        direction = Direction.valueOf(
                                input.toUpperCase().trim()
                        );
                        game.movePlayer(direction);
                    }

                }
            }catch (IllegalArgumentException e){
                System.err.println("Invalid Input try again");
            }
        }while (game.isGameRunning());

        scanner.close();
    }

    private static Difficulty chooseDifficulty() {
        Difficulty difficulty = null;
        do {
            try {
                System.out.println("Which Difficulty would you like to play");
                System.out.println("Easy ... if you are looser");
                System.out.println("Medium ... still a looser");
                System.out.println("Hard ... you are getting bullied");
                System.out.println("Masochist ... you are the bully");
                difficulty = Difficulty.valueOf(
                        scanner.nextLine().toUpperCase().trim()
                );
            } catch (IllegalArgumentException e){
                System.err.println("Invalid Input try again");
            }
        }while (difficulty == null);
        return difficulty;
    }

    private static boolean useFastFunction(String input) {
        return input.split(" ")
                .length > 1 ? true : false;
    }
}
