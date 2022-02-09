package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Field {

    private int numberOfMines;
    private int sizeOfField = 11;
    char [][] field;
    char [][] tmp;
    private int marks;
    private int badMoves = 0;

    public int getBadMoves() {
        return badMoves;
    }

    public int getNumberOfMines() {
        return numberOfMines;
    }

    public int getMarks() {
        return marks;
    }

    public Field() {
        field = new char[sizeOfField][sizeOfField];
        setMines();
        addObjects();
    }

    public void setMines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        this.numberOfMines = scanner.nextInt();
        this.marks = this.numberOfMines;
    }

    public void addObjects() {
        Random random = new Random();
        while(numberOfMines != 0) {
            int x = random.nextInt(sizeOfField-2)+1;
            int y = random.nextInt(sizeOfField-2)+1;
            if(field[x][y] == 0) {
                field[x][y] = 'X';
                numberOfMines--;
            }
        }
    }

    public char minesArround(int x, int y) {
        int count = 0;
        for(int i = x-1; i < x+2;i++) {
            for(int j = y-1; j < y+2;j++) {
                if(field[i][j] == 'X') {
                    count++;
                }
            }
        }
        return count == 0 ? '.' : (char)(count+'0') ;
    }

    public void fillField() {
        for (int i = 1; i < sizeOfField-1; i++) {
            for (int j = 1; j < sizeOfField-1; j++) {
                if(field[i][j] != 'X') {
                    field[i][j] = minesArround(i,j);
                }
            }
        }
        tmp = field;
    }

    public void printField() {
        System.out.println(" |123456789|\n" +
                            "-|---------|");
        for (int i = 1; i < sizeOfField-1; i++) {
            System.out.print(i +"|");
            for (int j = 1; j < sizeOfField-1; j++) {
                if(tmp[i][j] == 'X') {
                    System.out.print('.');
                }else
                System.out.print(tmp[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.println("-|---------|\n");
    }


    public boolean addMark(int x, int y) {
        if(Character.isDigit(tmp[x][y])) {
            System.out.println("There is a number here!");
            return false;
        }else {
            if(tmp[x][y] == '*') {
                if(field[x][y] == 'X') {
                    marks++;
                }else {
                    badMoves--;
                }
                tmp[x][y] = '.';
            }else {
                if(field[x][y] == 'X'){
                    marks--;
                }else {
                    badMoves++;
                }
                this.tmp[x][y] = '*';
            }
            return true;
        }
    }

}
