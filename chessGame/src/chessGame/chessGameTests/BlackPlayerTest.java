package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Bishop;
import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Board.Builder;

public class BlackPlayerTest {

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
		
		assertTrue(board.getBlackPlayer().getOpponent().equals(board.getWhitePlayer()));
		assertTrue(board.getBlackPlayer().getPlayerColor().equals(PieceColor.BLACK));
		assertEquals(board.getBlackPlayer().getActivePiece().size(), 2);
	}

}
