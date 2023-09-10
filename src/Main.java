import tictactoe.gamestate.BoardAnalysis;
import tictactoe.gamestate.GameState;

public class Main {
    public static void main(String[] args) {
        GameState gameState = new GameState();
        BoardAnalysis analysis = new BoardAnalysis(gameState);
        GameState.GameResult result = analysis.getGameResult();
        try {
            gameState.printBoard();
        } catch (Exception e) {
            System.out.println("Error in game state: " + e);
        }

        while (result == GameState.GameResult.NOT_FINISHED) {
            try {
                gameState.getUserMove();
                gameState.printBoard();
                analysis = new BoardAnalysis(gameState);
                analysis.analyzeGameState();
                result = analysis.getGameResult();
            } catch (Exception e) {
                System.out.println("Error in game state: " + e);
            }
        }

    }
}
