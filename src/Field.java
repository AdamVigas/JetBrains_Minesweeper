package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Field {

    private int numberOfMines;
    private int sizeOfField = 11;
    private Mine [][] field;
    public Field() {
        field = new Mine[sizeOfField][sizeOfField];
        setMines();
        addObjects();
        printField();
    }

    public void setMines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        this.numberOfMines = scanner.nextInt();
    }

    public void addObjects() {
        Random random = new Random();
        while(numberOfMines != 0) {
            int x = random.nextInt(sizeOfField-2)+1;
            int y = random.nextInt(sizeOfField-2)+1;
            //System.out.println("x: " + x + " y: " + y);
            if(field[x][y] == null) {
                field[x][y] = new Mine();
                numberOfMines--;
            }
        }
    }

    public char minesArround(int x, int y) {
        int count = 0;
        for(int i = x-1; i < x+2;i++) {
            for(int j = y-1; j < y+2;j++) {
                if(field[i][j] instanceof Mine) {
                    count++;
                }
            }
        }
        return count == 0 ? '.' : (char)(count+'0') ;
    }

    public void printField() {
        for (int i = 1; i < sizeOfField-1; i++) {
            for (int j = 1; j < sizeOfField-1; j++) {
                if(field[i][j] instanceof Mine) {
                    System.out.print(field[i][j].describe());
                }else System.out.print(minesArround(i,j));
            }
            System.out.println();
        }
    }
}
