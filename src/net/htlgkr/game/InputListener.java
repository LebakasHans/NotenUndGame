package net.htlgkr.game;

import java.io.IOException;
import java.util.Scanner;

public class InputListener implements Runnable{
    private Game game;

    public InputListener(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        try {
            do{
                int pressedKey = System.in.read();
                if (Character.isLetter((char) pressedKey)) {
                    game.setInputFromListener(pressedKey);
                    break;
                }
            }while (true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
