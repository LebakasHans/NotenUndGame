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
            Scanner scanner = new Scanner(System.in);
            int pressedKey = System.in.read();
            game.setInputFromListener(pressedKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
