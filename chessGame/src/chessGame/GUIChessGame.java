package chessGame;
import chessGUI.WindowView;

public class GUIChessGame {
	public static void main(String[] args) {
		// Setup the initial chess board and put all chess pieces at proper
		// positions
		Board board = Board.createInitialChessBoard();
//		Board board = Board.createInitialCrazyChessBoard();
		WindowView view = new WindowView(board);
		view.startGame(view);
	}
}
