package net.htlgkr.game;

import java.util.Scanner;

public class Main {
    /**
     *  I do not see any kind of problem with this method
     */
    public static void main(String[] args){
        char test = 10;
        Scanner scanner = new Scanner(System.in);
        Game game = new Game();
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

    private static boolean useFastFunction(String input) {
        return input.split(" ")
                .length > 1 ? true : false;
    }
}
