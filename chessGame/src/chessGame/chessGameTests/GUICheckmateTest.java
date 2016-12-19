package chessGame.chessGameTests;

import chessGUI.WindowView;
import chessGame.Board;

public class GUICheckmateTest {
	public static void main(String[] args) {
		//set up the chess board so that check mate condition is triggered
		Board board = Board.createCheckmateChessBoard();
		WindowView view = new WindowView(board);
		view.startGame(view);
	}
}
