package net.htlgkr.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    static final char ENEMY_SYMBOL = 'X';
    static final char PLAYER_SYMBOL = '@';
    static final char TREASURE_SYMBOL = 'S';
    static final int BATTLE_PREPARE_TIME = 2000;
    static final int BATTLE_TIME = 500000000;
    static final int TICK_TIME = 100;
    static final String GAME_OVER = """
            ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
            ███▀▀▀██┼███▀▀▀███┼███▀█▄█▀███┼██▀▀▀
            ██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼█┼┼┼██┼██┼┼┼
            ██┼┼┼▄▄▄┼██▄▄▄▄▄██┼██┼┼┼▀┼┼┼██┼██▀▀▀
            ██┼┼┼┼██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██┼┼┼
            ███▄▄▄██┼██┼┼┼┼┼██┼██┼┼┼┼┼┼┼██┼██▄▄▄
            ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
            ███▀▀▀███┼▀███┼┼██▀┼██▀▀▀┼██▀▀▀▀██▄┼
            ██┼┼┼┼┼██┼┼┼██┼┼██┼┼██┼┼┼┼██┼┼┼┼┼██┼
            ██┼┼┼┼┼██┼┼┼██┼┼██┼┼██▀▀▀┼██▄▄▄▄▄▀▀┼
            ██┼┼┼┼┼██┼┼┼██┼┼█▀┼┼██┼┼┼┼██┼┼┼┼┼██┼
            ███▄▄▄███┼┼┼─▀█▀┼┼─┼██▄▄▄┼██┼┼┼┼┼██▄
            ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼██┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼████▄┼┼┼▄▄▄▄▄▄▄┼┼┼▄████┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼▀▀█▄█████████▄█▀▀┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼█████████████┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼██▀▀▀███▀▀▀██┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼██┼┼┼███┼┼┼██┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼█████▀▄▀█████┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼┼███████████┼┼┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼▄▄▄██┼┼█▀█▀█┼┼██▄▄▄┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼▀▀██┼┼┼┼┼┼┼┼┼┼┼██▀▀┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼▀▀┼┼┼┼┼┼┼┼┼┼┼
            ┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼┼""";

    private String map = """
                ########################################
                ####  ##########    ### S   ###        #
                ####  ##########    ###     X          #
                ####  ##########    ###     ###        #
                ####  ########## X  ###########        #
                ####  ##########    ########### X      #
                ####  ##########    ###########        #
                #     ##########                       #
                #     ##########             X         #
                #                      X               #
                # X              ######  ##########    #
                #     ########## ######  ##########    #
                #     ########## ###### X##########    #
                #     ########## ######  ##########    #
                #X    ########## ######  ##########    #
                #@X   ########## ######  ##########    #
                ########################################""";

    List<StringBuilder> mapLines = Arrays.stream(map.split("\n"))
            .map(StringBuilder::new)
            .collect(Collectors.toList());
    private boolean gameRunning = true;
    private int inputFromListener = -1;

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setInputFromListener(int inputFromListener) {
        this.inputFromListener = inputFromListener;
    }

    public boolean movePlayer(Direction direction){
        return switch (direction) {
            case UP -> movePlayerTo(0, -1);
            case RIGHT -> movePlayerTo(1, 0);
            case DOWN -> movePlayerTo(0, 1);
            case LEFT ->  movePlayerTo(-1, 0);
        };
    }

    //moveSpacesToLeftOrRight positive value = Right negative value = Left
    //movesSpacesUpOrDown positive value = Down negative value = Up
    private boolean movePlayerTo(int moveSpacesToLeftOrRight, int movesSpacesUpOrDown) {
        boolean movePossible;
        int xPositionOfPlayer = getXPositionOfPlayer();
        int yPositionOfPlayer = getYPositionOfPlayer();
        char charOfPlaceToMove = getCharOfPosition(xPositionOfPlayer + moveSpacesToLeftOrRight, yPositionOfPlayer + movesSpacesUpOrDown);

        if (charOfPlaceToMove == ' '){
            movePossible = true;
            mapLines.get(yPositionOfPlayer).setCharAt(xPositionOfPlayer, ' ');
            mapLines.get(yPositionOfPlayer + movesSpacesUpOrDown).setCharAt(xPositionOfPlayer + moveSpacesToLeftOrRight, PLAYER_SYMBOL);

            if (charOfPlaceToMove == TREASURE_SYMBOL){
                gameRunning = false;
                //TODO end game method maybe
            }
        } else {
            movePossible = false;
        }
        afterMoveProcess();
        return movePossible;
    }

    private char getCharOfPosition(int x, int y){
        return mapLines
                .get(y)
                .toString()
                .charAt(x);
    }

    private void setCharOfPosition(int x, int y, char replacement){
        mapLines.get(y)
                .setCharAt(x, replacement);
    }

    private void afterMoveProcess() {
        //did the player move next to an enemy
        if(isPlayerNextToEnemy()){
            printMap();
            battle();
        }

        moveEnemies();

        //did an enemy move next to the player
        if(isPlayerNextToEnemy()){
            printMap();
            battle();
        }
        if(gameRunning)
            printMap();
    }

    private void moveEnemies() {
        List<String> enemyPositions = getEnemyPositions();
        deleteEnemiesFromMap(enemyPositions);

        int x;
        int y;
        Direction randomDirection;
        for (String position : enemyPositions) {
            boolean movePossible;
            do {
                y = Integer.parseInt(position.split(";")[0]);
                x = Integer.parseInt(position.split(";")[1]);

                randomDirection = randomDirection();
                movePossible = switch (randomDirection) {
                    case UP -> moveEnemyTo(x, y - 1);
                    case DOWN -> moveEnemyTo(x, y + 1);
                    case LEFT -> moveEnemyTo(x - 1, y);
                    case RIGHT -> moveEnemyTo(x + 1, y);
                };

            } while (!movePossible);
        }
    }

    private boolean moveEnemyTo(int x, int y) {
        if (getCharOfPosition(x, y) == ' '){
            setCharOfPosition(x, y, ENEMY_SYMBOL);
            return true;
        }else{
            return false;
        }
    }

    private Direction randomDirection() {
        Random rand = new Random();
        int randomNum = rand.nextInt(4) + 1;
        return switch (randomNum){
            case 1 -> Direction.UP;
            case 2 -> Direction.RIGHT;
            case 3 -> Direction.DOWN;
            case 4 -> Direction.LEFT;
            default -> null;
        };
    }

    private void deleteEnemiesFromMap(List<String> enemyPositions) {
        int x;
        int y;
        for (String position : enemyPositions){
            y = Integer.parseInt(position.split(";")[0]);
            x = Integer.parseInt(position.split(";")[1]);
            setCharOfPosition(x, y, ' ');
        }
    }

    private List<String> getEnemyPositions() {
        String line;
        List<String> enemyPositions = new ArrayList<>();
        for (int y = 0; y < mapLines.size(); y++) {
            line = mapLines.get(y).toString();
            for (int x = 0; x < line.length(); x++) {
                if (line.charAt(x) == ENEMY_SYMBOL){
                    enemyPositions.add(y + ";" + x);
                }
            }
        }
        return enemyPositions;
    }

    private void battle() {
        System.out.println("You have encountered an Enemy");
        System.out.println("You will have to fight in " + BATTLE_PREPARE_TIME /1000 + " seconds");
        myWait(BATTLE_PREPARE_TIME);
        System.out.println("The epic battle starts");
        Random rand = new Random();
        char randomLetter = (char)(rand.nextInt(26) + 'a');
        System.out.println("You have to press the letter " + randomLetter + " in " + BATTLE_TIME /1000 + " seconds to win the battle");
        long startTime = System.currentTimeMillis();
        Thread inputListener = new Thread(new InputListener(this));
        inputListener.start();
        boolean wonBattle;
        while (System.currentTimeMillis() < startTime + BATTLE_TIME) {
            if (inputFromListener == randomLetter) {
                wonBattle = true;
                break;
            } else if (inputFromListener > 0 && inputFromListener != randomLetter) {
                wonBattle = false;
                break;
            }
            myWait(TICK_TIME);
        }
        inputFromListener = -1;
        inputListener.interrupt();
        if (System.currentTimeMillis() >= startTime + BATTLE_TIME) {
            lostBattle();
        }
    }

    private void lostBattle() {
        System.out.println("You have lost the battle and died");
        System.out.println(GAME_OVER);
        gameRunning = false;
    }

    private void wonBattle() {
        System.out.println("You have won the battle");
        deleteDefeatedEnemy();
    }

    private void deleteDefeatedEnemy() {
        if(getCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() -1) == ENEMY_SYMBOL)
            setCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() - 1, ' ');
        else if(getCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() + 1) == ENEMY_SYMBOL)
            setCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() + 1, ' ');
        else if(getCharOfPosition(getXPositionOfPlayer() + 1, getYPositionOfPlayer()) == ENEMY_SYMBOL)
            setCharOfPosition(getXPositionOfPlayer() + 1, getYPositionOfPlayer(), ' ');
        else if(getCharOfPosition(getXPositionOfPlayer() - 1, getYPositionOfPlayer()) == ENEMY_SYMBOL)
            setCharOfPosition(getXPositionOfPlayer() - 1, getYPositionOfPlayer(), ' ');
        if (isPlayerNextToEnemy()){
            battle();
        }
    }

    private void myWait(int battlePrepareTime) {
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() < startTime + battlePrepareTime);
    }

    private boolean isPlayerNextToEnemy() {
        boolean enemyAbove = getCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() -1) == ENEMY_SYMBOL ? true : false;
        boolean enemyBelow = getCharOfPosition(getXPositionOfPlayer(), getYPositionOfPlayer() + 1) == ENEMY_SYMBOL ? true : false;
        boolean enemyRight = getCharOfPosition(getXPositionOfPlayer() + 1, getYPositionOfPlayer()) == ENEMY_SYMBOL ? true : false;
        boolean enemyLeft = getCharOfPosition(getXPositionOfPlayer() - 1, getYPositionOfPlayer()) == ENEMY_SYMBOL ? true : false;
        if (enemyAbove || enemyBelow || enemyRight || enemyLeft){
            return true;
        }else {
            return false;
        }
    }

    private int getXPositionOfPlayer() {
        return mapLines.stream()
                .filter(line -> line.toString().contains(
                        String.valueOf(PLAYER_SYMBOL)
                ))
                .collect(Collectors.toList()).get(0).indexOf(
                        String.valueOf(PLAYER_SYMBOL)
                );
    }

    private int getYPositionOfPlayer() {
        for (int i = 0; i < mapLines.size(); i++) {
            if(mapLines.get(i).toString().contains(
                    String.valueOf(PLAYER_SYMBOL)
            )){
                return i;
            }
        }
        return -1;
    }

    public void printMap(){
        mapLines.stream()
                .forEach(System.out::println);
    }

    public void printLegend() {
        System.out.println("UP ... Go up");
        System.out.println("DOWN ... Go down");
        System.out.println("LEFT ... Go left");
        System.out.println("RIGHT ... Go right");
        System.out.println("[DIRECTION] [Number of spaces to go] ... Example RIGHT 5");
        System.out.println("If you use the fast move function the enemies will also move n times");
        System.out.println("Tipp .. you don't need to write in upper case");
    }
}
