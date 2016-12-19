package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Knight;
import chessGame.Board.Builder;

public class PieceTest {

	@Test
	public void test() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Knight(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Knight(63, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Knight knight = (Knight) board.getTile(35).getPieceOnTile();
		assertEquals(knight.getPieceLocation(), 35);
		assertTrue(knight.getPieceColor().equals(PieceColor.BLACK));
	}

}
