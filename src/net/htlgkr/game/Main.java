package net.htlgkr.game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        loop:
        for (int i = 0; i < 100; i++) {

            continue loop;
        }

        Game game = new Game();
        Scanner scanner = new Scanner(System.in, "UTF-8");
        do {
            game.printMap();
            Direction direction = Direction.valueOf(
                    scanner.next().toUpperCase()
            );
            boolean moved = game.movePlayer(direction);


        }while (true);
    }
}
