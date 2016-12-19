package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Bishop;
import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Board.Builder;

public class WhitePlayerTest {

	@Test
	public void test() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Bishop(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		assertTrue(board.getWhitePlayer().getOpponent().equals(board.getBlackPlayer()));
		assertTrue(board.getWhitePlayer().getPlayerColor().equals(PieceColor.WHITE));
		assertEquals(board.getWhitePlayer().getActivePiece().size(), 1);
	}

}
