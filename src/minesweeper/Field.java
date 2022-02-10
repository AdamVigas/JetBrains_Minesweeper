package minesweeper;

import java.util.Random;
import java.util.Scanner;

public class Field {

    private int numberOfMines;
    private final int sizeOfField = 9;
    final private char [][] field;
    final private char [][] tmp;
    private int marks;
    private int badMoves = 0;


    public char[][] getTmp() {
        return tmp;
    }

    public int getBadMoves() {
        return badMoves;
    }

    public int getMarks() {
        return marks;
    }

    public Field() {
        field = new char[sizeOfField][sizeOfField];
        tmp = new char[sizeOfField][sizeOfField];
        setMines();
        addObjects();
    }

    /**
     * Method for setting how many mines we want in our field
     */
    public void setMines() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("How many mines do you want on the field?");
        this.numberOfMines = scanner.nextInt();
        this.marks = this.numberOfMines;
    }

    /**
     * Randomly placing mines on field
     */
    public void addObjects() {
        Random random = new Random();
        while(numberOfMines != 0) {
            int x = random.nextInt(sizeOfField);
            int y = random.nextInt(sizeOfField);
            if(field[x][y] == 0) {
                field[x][y] = 'X';
                numberOfMines--;
            }
        }
    }

    /**
     * Setting empty lines and lines that are neighbours with mines, if they are neighbours they are printed with number
     */
    public char minesAround(int x, int y) {
        int count = 0;
        for(int i = x-1; i < x+2;i++) {
            for(int j = y-1; j < y+2;j++) {
                try {
                    if (field[i][j] == 'X') {
                        count++;
                    }
                }catch (ArrayIndexOutOfBoundsException ignore){}
            }
        }
        return count == 0 ? '.' : (char)(count+'0') ;
    }

    /**
     * Filling cells that are not mines , this field is backend of our game
     */
    public void fillField() {
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                if(field[i][j] != 'X') {
                    field[i][j] = minesAround(i,j);
                }
            }
        }
    }

    /**
     * Temporary field should start empty, this field is printed when game starts
     */
    public void fillTmp() {
        for (int i = 0; i < sizeOfField; i++) {
            for (int j = 0; j < sizeOfField; j++) {
                    tmp[i][j] = '.';
            }
        }
    }

    /**
     * Method for printing field
     */
    public void printArr(char [][] arr) {
        System.out.println(" |123456789|\n" +
                "-|---------|");
        for (int i = 0; i < sizeOfField; i++) {
            System.out.print(i+1 +"|");
            for (int j = 0; j < sizeOfField; j++) {
                System.out.print(arr[i][j]);
            }
            System.out.print("|\n");
        }
        System.out.println("-|---------|\n");
    }

    /**
     * Method for adding/removing mines,numbers or recursive backslash
     */
    public void addEmpty(int x, int y, String input) {
        if(field[x][y] == 'X' && input.equals("mine")) {
            if(tmp[x][y] != '*') {
                tmp[x][y] = '*';
                marks++;
            }else {
                tmp[x][y] = '.';
                marks--;
            }
        }else if(field[x][y] == 'X' && input.equals("free")) {
            printArr(field);
            System.out.println("You stepped on a mine and failed!");
            System.exit(0);
        } else if(field[x][y] != 'X' && input.equals("mine")) {
            if(tmp[x][y] != '*') {
                tmp[x][y] = '*';
                badMoves++;
            }else {
                tmp[x][y] = '.';
                badMoves--;
            }

        }else if(Character.isDigit(field[x][y])) {
            tmp[x][y] = field[x][y];

        }else {
            floodFill(field, x, y);

        }

    }

    /**
     * Method checking if there is number arround , including diagonal cells
     */
    public void checkNumbers(int x, int y) {
        for(int i = x-1; i < x+2;i++) {
            for(int j = y-1; j < y+2;j++) {
                try {
                    if (Character.isDigit(field[i][j])) {
                        tmp[i][j] = field[i][j];
                    }
                }catch (ArrayIndexOutOfBoundsException ignore){}
            }
        }
    }

    /**
     * Setting backslash recursively
     */
    void floodFillUtil(char [][] screen, int x, int y, int prevC) {
        // Base cases
        if (x < 0 || x >= sizeOfField || y < 0 || y >= sizeOfField)
            return;
        if (screen[x][y] != prevC)
            return;

        field[x][y] = '/';
        tmp[x][y] = '/';
        checkNumbers(x,y);

        for(int i = x-1; i < x+2;i++) {
            for(int j = y-1; j < y+2;j++) {
                try {
                    floodFillUtil(screen, i, j, prevC);
                }catch (ArrayIndexOutOfBoundsException ignore){}
            }
        }


    }

    /**
     * Method that checks if there is no backslash, if it is not we start recursion
     */
    void floodFill(char [][] screen, int x, int y) {
        char prevC = screen[x][y];
        if(prevC=='/') return;
        floodFillUtil(screen, x, y, prevC);

    }


}
