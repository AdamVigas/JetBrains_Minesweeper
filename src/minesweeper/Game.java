package minesweeper;

import java.util.Scanner;

public class Game {
    final static Scanner scanner = new Scanner(System.in);
    Field field;
    boolean gameOver = false;


    /**
     * Main constructor that starts and ends game.
     */
    public Game() {
        field = new Field();
        field.fillField();
        field.fillTmp();
        field.printArr(field.getTmp());
        while(!gameOver){
            scanCoordinates();
            field.printArr(field.getTmp());
            if(field.getMarks() == 0 && field.getBadMoves() == 0) {
                gameOver = true;
                System.out.println("Congratulations! You found all the mines!");
            }
        }
    }


    /**
     * Scanning Coordinates and option free/mine
     */
    public void scanCoordinates() {
        System.out.print("Set/delete mines marks (x and y coordinates): ");
        int x = scanner.nextInt() -1;
        int y = scanner.nextInt() -1;
        String input = scanner.next();
        field.addEmpty(y,x,input);
    }
}
