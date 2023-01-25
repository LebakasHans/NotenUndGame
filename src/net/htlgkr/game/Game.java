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
    private boolean gameRunning = true;

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
                #     ########## ######  ##########    #
                #@    ########## ######  ##########    #
                ########################################""";

    List<StringBuilder> mapLines = Arrays.stream(map.split("\n"))
            .map(StringBuilder::new)
            .collect(Collectors.toList());

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
        if(nextToEnemy()){
            battle();
        }

        moveEnemies();

        //did an enemy move next to the player
        if(nextToEnemy()){
            battle();
        }
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

    }

    private boolean nextToEnemy() {
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
}
