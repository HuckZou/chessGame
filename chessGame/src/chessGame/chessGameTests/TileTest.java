package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Piece;
import chessGame.Rook;
import chessGame.Board.Builder;

public class TileTest {

	@Test
	public void test() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(3, PieceColor.BLACK));
		builder.setPiece(new Rook(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(54, PieceColor.WHITE));
		builder.setPiece(new Rook(63, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Piece rook1 = board.getTile(35).getPieceOnTile();
		assertTrue(rook1 instanceof Rook);
		
		assertTrue(board.getTile(35).isTileOccupied());
	}

}
