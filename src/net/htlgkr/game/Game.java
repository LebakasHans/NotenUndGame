package net.htlgkr.game;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
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
            .map(StringBuilder::new).collect(Collectors.toList());

    public boolean movePlayer(Directions direction){
        return switch (direction) {
            case UP -> movePlayerUpwards();
            case RIGHT -> movePlayerToRight();
            case DOWN -> movePlayerDownwards();
            case LEFT ->  movePlayerToLeft();
        };
    }

    //moveSpacesToLeftOrRight positive value = Right negative value = Left
    //movesSpacesUpOrDown positive value = Down negative value = Up
    private boolean movePlayerTo(int moveSpacesToLeftOrRight, int movesSpacesUpOrDown) {
        boolean movePossible;
        int indexOfPlayer = getIndexOfPlayer();
        int indexOfLinePlayer = -1;
        //just some initialization so the if below works
        char charOfPlaceToMove = '!';
        for (int i = 0; i < mapLines.size(); i++) {
            if(mapLines.get(i).toString().contains("@")){
                indexOfLinePlayer = i;
                charOfPlaceToMove = mapLines.get(indexOfLinePlayer + movesSpacesUpOrDown).charAt(indexOfPlayer + moveSpacesToLeftOrRight);
            }
        }
        if (charOfPlaceToMove != ' '){
            movePossible = true;
            mapLines.get(indexOfLinePlayer + movesSpacesUpOrDown).setCharAt(indexOfPlayer + moveSpacesToLeftOrRight, ' ');
            mapLines.get(indexOfLinePlayer + movesSpacesUpOrDown).setCharAt(indexOfPlayer + moveSpacesToLeftOrRight, '@');
        }else {
            movePossible = false;
        }
        return movePossible;
    }

    private int getIndexOfPlayer() {
        return mapLines.stream()
                .filter(line -> line.toString().contains("@"))
                .collect(Collectors.toList()).get(0).indexOf("@");
    }
}
