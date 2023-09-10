package tictactoe.gamestate;

import java.util.Arrays;

public class BoardAnalysis {
    static GameState.GameResult result;
    static GameState gameState;
    static final char xPlayer = 'X';
    static final char oPlayer = 'O';
    static final char emptySpace = '_';
    static int xWinningRows = 0;
    static int oWinningRows = 0;
    static int numXSpots = 0;
    static int numOSpots = 0;
    static int numEmptySpots = 0;
    public BoardAnalysis(GameState gameState) {
        BoardAnalysis.gameState = gameState;
        result = GameState.GameResult.NOT_FINISHED;
    }
    /**
     * Prints a string to System.out with the analyzed board game state. The following are
     * all possible results of the game state:
     *      <p> "Game not finished" -> neither side has three in a row but the grid still has empty (_) cells </p>
     *      <p> "Draw" -> no side has three in a row and the grid has no empty cells </p>
     *      <p> "X wins" -> grid has three X's in a row (incl. diagonals) </p>
     *      <p> "O Wins" -> when the grid has three O's in a row (incl. diagonals) </p>
     *      <p> "Impossible" -> when the grid has three X's in a row as well as three O's in a row
     *                           or there are a lot more X's than O's or vice versa. Essentially, someone cheated. </p>
     *
     */
    public void analyzeGameState() throws Exception {
        numEmptySpots = 0;
        numOSpots = 0;
        numXSpots = 0;
        /*
         * By getting the transpose of our gameState matrix, we
         * can determine if any columns are winners.
         */
        char[][] transposedState = gameState.getTranspose();

        /*
         * Iterate over the gameState array and mark the number of
         * each type of character. Then, determine if any row is
         * a winning row. (all X's or all O's)
         */
        char[][] gameBoard = gameState.getGameStateMatrix();
        for (char[] chars : gameBoard) {
            for (char token : chars) {
                switch (token) {
                    case emptySpace -> numEmptySpots++;
                    case xPlayer -> numXSpots++;
                    case oPlayer -> numOSpots++;
                    // This default clause is redundant, but it feels right to have.
                    default -> throw new Exception("Invalid game state token: " + token);
                }
            }
            // Analyze each row to see if it contains three of the same character.
            analyzeRowWinner(chars);
        }

        // Analyze each column to see if it contains three of the same character.
        for (char[] chars : transposedState) {
            analyzeRowWinner(chars);
        }

        // Analyze the diagonals
        char[] diag1 = new char[3];
        char[] diag2 = new char[3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                diag1[i] = gameBoard[i][j];
                i++;
            }
        }
        for (int i = 2; i > 0; i--) {
            for (int j = 0; j < 3; j++) {
                diag2[i] = gameBoard[i][j];
                i--;
            }
        }
        analyzeRowWinner(diag1);
        analyzeRowWinner(diag2);

        /*
         * The final big daddy of checks to determine the outcome of the game and
         * display it to the user.
         */

        switch (getGameResult()) {
            case DRAW -> System.out.println("Draw");
            case IMPOSSIBLE -> System.out.println("Impossible");
            case X_WINS -> System.out.println("X wins");
            case O_WINS -> System.out.println("O wins");
        }


    }

    public boolean isNewGame() {
        int emptyTokens = 0;
        for (char[] chars : gameState.getGameStateMatrix()) {
            for (char token : chars) {
                if (token == emptySpace) {
                    emptyTokens++;
                }
            }
        }

        return emptyTokens == 9;

    }

    public GameState.GameResult getGameResult() {
        if (isNewGame()) {
            result = GameState.GameResult.NOT_FINISHED;
            numEmptySpots = 9;
        }
        if (xWinningRows == 1 && oWinningRows == 0) {
            result = GameState.GameResult.X_WINS;
        } else if (oWinningRows == 1 && xWinningRows == 0) {
            result = GameState.GameResult.O_WINS;
        }

        /*
         * Check to see if the game is impossible
         */
        if ((xWinningRows > 1 || oWinningRows > 1) || (xWinningRows == 1 && oWinningRows == 1)) {
            result = GameState.GameResult.IMPOSSIBLE;
        } else if ((Math.abs(numXSpots - numOSpots)) >= 2) {
            result = GameState.GameResult.IMPOSSIBLE;
        }

        if ((result != GameState.GameResult.X_WINS && result != GameState.GameResult.O_WINS)
                && numEmptySpots == 0) {
            result = GameState.GameResult.DRAW;
        }

        return result;
    }

    private static void analyzeRowWinner(char[] row) {
        // Analyze each row to see if it contains three of the same character.
        if (Arrays.equals(row, new char[] {xPlayer, xPlayer, xPlayer})) {
            xWinningRows++;
        } else if (Arrays.equals(row, new char[] {oPlayer, oPlayer, oPlayer})) {
            oWinningRows++;
        }
    }
}
