package chessGame.chessGameTests;

import chessGUI.WindowView;
import chessGame.Board;

public class GUIStalemateTest {
	public static void main(String[] args) {
		//set up the chess board so that check mate condition is triggered
		Board board = Board.createStalemateChessBoard();
		WindowView view = new WindowView(board);
		view.startGame(view);
	}
}
