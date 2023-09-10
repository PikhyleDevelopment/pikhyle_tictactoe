package tictactoe.gamestate;

import java.util.Scanner;

public class GameState {
    public enum GameResult {
        DRAW,
        IMPOSSIBLE,
        X_WINS,
        O_WINS,
        NOT_FINISHED,
    }
    enum PlayerTurn {
        X,
        O
    }
    static PlayerTurn turn = PlayerTurn.X;
    static final int numRows = 3;
    static final int numCols = 3;
    static final char xPlayer = 'X';
    static final char oPlayer = 'O';
    static final char emptySpace = '_';
    private static char[][] gameStateMatrix;

    public GameState() {
        gameStateMatrix = generateGameState();
    }

    public char[][] getGameStateMatrix() {
        return gameStateMatrix;
    }

    public void getUserMove() {
        Scanner sc = new Scanner(System.in);
        int xInput;
        int yInput;
        inputLoop:
        while (true) {
            System.out.print(turn + "'s move > ");
            // Make sure user input are ints
            try {
                xInput = Integer.parseInt(sc.next());
                yInput = Integer.parseInt(sc.next());
            } catch (NumberFormatException nfe) {
                System.out.println("You should enter numbers!");
                continue;
            }
            // If the user input is out of range:
            if ((xInput < 1 || xInput > 3) || (yInput < 1 || yInput > 3)) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (gameStateMatrix[xInput - 1][yInput - 1] == xPlayer
                    || gameStateMatrix[xInput - 1][yInput - 1] == oPlayer) {
                // The spot is occupied, choose another spot.
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                // This is a valid move. Update gameState with token and switch turns.
                switch (turn) {
                    case X -> {
                        gameStateMatrix[xInput - 1][yInput - 1] = xPlayer;
                        turn = PlayerTurn.O;
                        break inputLoop;
                    }
                    case O -> {
                        gameStateMatrix[xInput - 1][yInput - 1] = oPlayer;
                        turn = PlayerTurn.X;
                        break inputLoop;
                    }
                }
            }
        }
    }

    public char[][] getTranspose() {
        char[][] transposed = new char[numRows][numCols];
        for (int i = 0; i < gameStateMatrix.length; i++) {
            for (int j = 0; j < gameStateMatrix[i].length; j++) {
                transposed[j][i] = gameStateMatrix[i][j];
            }
        }

        return transposed;
    }

    public void printBoard() throws Exception {
        String vertBoundary = "---------";
        int row1End = numRows;

        if (gameStateMatrix.length == 0) {
            throw new Exception("Game state must be greater than 0 characters!");
        }
        for (char[] chars : gameStateMatrix) {
            for (char token : chars) {
                if (token != emptySpace && token != xPlayer && token != oPlayer) {
                    throw new Exception("Invalid token in game state: " + token);
                }
            }
        }

        System.out.println(vertBoundary);

        do {
            printRow(gameStateMatrix);
            row1End += 3;
        } while (row1End < gameStateMatrix.length + 1);


        System.out.println(vertBoundary);
    }

    public static void printRow(char[][] gameState) {
        char sideBoundary = '|';
        for (char[] chars : gameState) {
            System.out.print(sideBoundary + " ");
            for (char token : chars) {
                System.out.print(token + " ");
            }
            System.out.println(sideBoundary);
        }
    }

    private char[][] generateGameState() {
        char[][] gameStateArray = new char[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                gameStateArray[i][j] = emptySpace;
            }
        }

        return gameStateArray;
    }
}
