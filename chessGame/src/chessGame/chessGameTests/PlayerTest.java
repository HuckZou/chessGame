package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.Queen;
import chessGame.King;
import chessGame.Move;
import chessGame.Rook;
import chessGame.Board.Builder;

public class PlayerTest {

	@Test
	public void InCheckMateTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(63, PieceColor.WHITE));
		builder.setPiece(new Rook(1, PieceColor.WHITE));
		builder.setPiece(new Rook(7, PieceColor.WHITE));
		
		builder.setMover(PieceColor.WHITE);
		Board board = builder.build();
		
		assertTrue(board.getBlackPlayer().isInCheck());
		
		Move move1 = Move.MoveFactory.createMove(board, 7, 15);
		board = move1.execute();
		assertTrue(board.getBlackPlayer().isInCheckMate());
		
	}
	
	@Test
	public void InStaleMateTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(7, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(13, PieceColor.WHITE));
		builder.setPiece(new Queen(30, PieceColor.WHITE));
		
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		assertTrue(board.getBlackPlayer().isInStaleMate());
	}

}
