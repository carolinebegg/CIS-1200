package org.cis1200.twentyfourtyeight;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class TwentyFourtyEight {

    /** Instance Variables **/
    private int[][] board;
    private int score;
    private int turn;
    private boolean turnSuccess;
    private static final String FILE_PATH = "files/saved_game.csv";
    private final List<String> csv = new LinkedList<>();
    private BufferedReader br;
    private static LinkedList<int[][]> game = new LinkedList<>();

    /** Functions **/
    public TwentyFourtyEight() {
        saved();
    }

    public void addNewTile() {
        int r = (int) (Math.random() * 4);
        int c = (int) (Math.random() * 4);
        while (board[r][c] != 0) {
            r = (int) (Math.random() * 4);
            c = (int) (Math.random() * 4);
        }
        board[r][c] = 2;
    }

    public void addNewTile(int r, int c) {
        board[r][c] = 2;
    }

    public void add4Tile(int r, int c) {
        board[r][c] = 4;
    }

    public void add2048Tile(int r, int c) {
        board[r][c] = 2048;
    } // for testing

    public boolean slideRight() {
        turnSuccess = false;
        for (int r = 0; r < 4; r++) {
            boolean[] merged = new boolean[4];
            for (int c = 3; c > -1; c--) {
                if (board[r][c] != 0) {
                    int col = c;
                    while ((col < 3) && (board[r][col + 1] == 0)) {
                        board[r][col + 1] = board[r][col];
                        board[r][col] = 0;
                        col++;
                        turnSuccess = true;
                    }
                    if ((col < 3) && (board[r][col] == board[r][col + 1]) && !(merged[col + 1])) {
                        board[r][col + 1] = 2 * board[r][col];
                        merged[col + 1] = true;
                        board[r][col] = 0;
                        score += board[r][col + 1];
                        turnSuccess = true;
                    }
                }
            }
        }
        return turnSuccess;
    }

    public boolean slideLeft() {
        turnSuccess = false;
        for (int r = 0; r < 4; r++) {
            boolean[] merged = new boolean[4];
            for (int c = 1; c < 4; c++) {
                if (board[r][c] != 0) {
                    int col = c;
                    while ((col > 0) && (board[r][col - 1] == 0)) {
                        board[r][col - 1] = board[r][col];
                        board[r][col] = 0;
                        col--;
                        turnSuccess = true;
                    }
                    if ((col > 0) && (board[r][col] == board[r][col - 1]) && !(merged[col - 1])) {
                        board[r][col - 1] = 2 * board[r][col];
                        merged[col - 1] = true;
                        board[r][col] = 0;
                        score += board[r][col - 1];
                        turnSuccess = true;
                    }
                }
            }
        }
        return turnSuccess;
    }

    public boolean slideUp() {
        turnSuccess = false;
        for (int c = 0; c < 4; c++) {
            boolean[] merged = new boolean[4];
            for (int r = 1; r < 4; r++) {
                if (board[r][c] != 0) {
                    int row = r;
                    while ((row > 0) && (board[row - 1][c] == 0)) {
                        board[row - 1][c] = board[row][c];
                        board[row][c] = 0;
                        row--;
                        turnSuccess = true;
                    }
                    if ((row > 0) && (board[row][c] == board[row - 1][c]) && !(merged[row - 1])) {
                        board[row - 1][c] = 2 * board[row][c];
                        merged[row - 1] = true;
                        board[row][c] = 0;
                        score += board[row - 1][c];
                        turnSuccess = true;
                    }
                }
            }
        }
        return turnSuccess;
    }

    public boolean slideDown() {
        turnSuccess = false;
        for (int c = 0; c < 4; c++) {
            boolean[] merged = new boolean[4];
            for (int r = 3; r > -1; r--) {
                if (board[r][c] != 0) {
                    int row = r;
                    while ((row < 3) && (board[row + 1][c] == 0)) {
                        board[row + 1][c] = board[row][c];
                        board[row][c] = 0;
                        row++;
                        turnSuccess = true;
                    }
                    if ((row < 3) && (board[row][c] == board[row + 1][c]) && !(merged[row + 1])) {
                        board[row + 1][c] = 2 * board[row][c];
                        merged[row + 1] = true;
                        board[row][c] = 0;
                        score += board[row + 1][c];
                        turnSuccess = true;
                    }
                }
            }
        }
        return turnSuccess;
    }

    public boolean getTurnSuccess() {
        return turnSuccess;
    }

    public void addBoardState() {
        int[][] arr = new int[4][4];
        for (int r = 0; r < 4; r++) {
            System.arraycopy(board[r], 0, arr[r], 0, 4);
        }
        game.add(arr);
    }

    public boolean isSlidePossible() {
        for (int[] ints : board) {
            for (int c = 0; c < board[0].length; c++) {
                if (ints[c] == 0) {
                    return true;
                }
            }
        }
        return slideUp() || slideDown() || slideRight() || slideLeft();
    }

    public boolean checkWinner() {
        for (int[] ints : board) {
            for (int c = 0; c < board[0].length; c++) {
                if (ints[c] == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        return checkWinner() || isSlidePossible();
    }

    public void gameOver() {
        if (checkGameOver()) {
            if (checkWinner()) {
                System.out.println("\n\nCongratulations! You reached the 2048 tile!");
                System.out.println("Final Score: " + score);
            } else {
                System.out.println("\n\nGame Over!");
                System.out.println("Final Score: " + score);
            }
        }
    }

    public void updateSavedGame() {
        gridToStringRows(board, score);
        writeStringsToFile(csv, FILE_PATH, false);
    }

    public void reset() {
        game.clear();
        csv.clear();
        board = new int[4][4];
        score = 0;
        turn = 0;
        addNewTile();
        addNewTile();
        addBoardState();
        updateSavedGame();
    }

    public void saved() {
        readStringsFromFile(FILE_PATH);
        game = listOfStringsToLinkedList(csvDataToString(br));
        board = game.getLast();
        turn = 0;
    }

    public void undo() {
        if (game.size() > 1) {
            game.removeLast();
            board = game.getLast();
            score = score / 2;
        }
    }

    public void readStringsFromFile(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("filePath is null");
        }
        try {
            br = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("filePath not found");
        }
    }

    public List<String> csvDataToString(BufferedReader br) {
        List<String> entries = new LinkedList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                entries.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    public LinkedList<int[][]> listOfStringsToLinkedList(List<String> csv) {
        LinkedList<int[][]> list = new LinkedList<>();
        for (String s : csv) {
            list.add(csvLinetoBoard(s));
        }
        return list;
    }

    public int[][] csvLinetoBoard(String csvLine) {
        String[] slice = csvLine.split(",");
        score = Integer.parseInt(slice[0]);
        int index = 1;
        int[][] arr = new int[4][4];
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                arr[r][c] = Integer.parseInt(slice[index]);
                index++;
            }
        }
        return arr;
    }

    public int[][] stringsToGrid(List<String> csv) {
        int i = 0;
        int[] index = new int[17];
        int[][] previous = new int[4][4];
        for (String s : csv) {
            index[i] = Integer.parseInt(s);
            i++;
        }
        score = index[0];
        i = 1;
        for (int r = 0; r < previous.length; r++) {
            for (int c = 0; c < previous[0].length; c++) {
                previous[r][c] = index[i];
                i++;
                System.out.println();
            }
        }
        return previous;
    }

    public void gridToStringRows(int[][] grid, int score) {
        StringBuilder line = new StringBuilder();
        line.append(score);
        line.append(",");
        int index = 0;
        for (int[] ints : grid) {
            for (int c = 0; c < grid[0].length; c++) {
                line.append(ints[c]);
                if (index < 15) {
                    line.append(",");
                }
                index++;
            }
        }
        csv.add(line.toString());
    }

    public void writeStringsToFile(List<String> tilesToWrite, String filePath, boolean append) {
        File file = Paths.get(filePath).toFile();
        BufferedWriter bw;
        try {
            FileWriter f = new FileWriter(file, append);
            bw = new BufferedWriter(f);
            for (String t : tilesToWrite) {
                bw.write(t, 0, t.length());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getCell(int r, int c) {
        return board[r][c];
    }

    public int getScore() {
        return score;
    }

    public int[][] getBoard() {
        return board;
    }

    public void testing() {
        board = new int[4][4];
        score = 0;
    }

    public void printGameState() {
        System.out.println("\n\nTest #" + turn);
        System.out.println("Score: " + score + "\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 3) {
                    System.out.print(" | ");
                }
            }
            if (i < 3) {
                System.out.println("\n--------------");
            }
        }
        if (checkGameOver()) {
            gameOver();
        }
    }

    public static void main(String[] args) {
        TwentyFourtyEight tfe = new TwentyFourtyEight();

        // Create Original Board
        tfe.printGameState();

        tfe.slideLeft();
        tfe.addNewTile(1, 3);
        tfe.printGameState();

        tfe.slideDown();
        tfe.addNewTile(0, 3);
        tfe.printGameState();

        tfe.slideUp();
        tfe.addNewTile(2, 2);
        tfe.printGameState();

        tfe.slideRight();
        tfe.addNewTile(2, 2);
        tfe.printGameState();
    }
}
