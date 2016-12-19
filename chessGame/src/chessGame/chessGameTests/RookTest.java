package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Rook;
import chessGame.Board.Builder;

public class RookTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(3, PieceColor.BLACK));
		builder.setPiece(new Rook(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(54, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Rook rook= (Rook) board.getTile(35).getPieceOnTile();
		assertEquals(rook.computeLegalMove(board).size(), 14);
		
		//test corner
		Rook rook2 = (Rook) board.getTile(63).getPieceOnTile();
		assertEquals(rook2.computeLegalMove(board).size(), 14);
		
		
	}

}
