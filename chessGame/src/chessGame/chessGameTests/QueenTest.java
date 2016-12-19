package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Move;
import chessGame.Queen;
import chessGame.Board.Builder;

public class QueenTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Queen(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Queen queen = (Queen) board.getTile(35).getPieceOnTile();
		assertEquals(queen.computeLegalMove(board).size(), 27);		
		//test illegal move
		Move move1 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 3, 1);
		Move move2 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 1, 7);
		Move move3 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 6, 4);
		assertTrue(Move.isNullMove(move1));
		assertTrue(Move.isNullMove(move2));
		assertTrue(Move.isNullMove(move3));
		
		//test legal moves
		Move move4 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 4, 5);
		Move move5 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 3, 3);
		Move move6 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 6, 5);
		Move move7 = Move.MoveFactory.createCoordinateMove(board, 4, 3, 6, 3);
		assertFalse(Move.isNullMove(move4));
		assertFalse(Move.isNullMove(move5));
		assertFalse(Move.isNullMove(move6));
		assertFalse(Move.isNullMove(move7));
	}
	
	@Test 
	public void BlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Queen(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Queen(43, PieceColor.WHITE));
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Queen queen = (Queen) board.getTile(35).getPieceOnTile();
		assertEquals(queen.computeLegalMove(board).size(), 25);		
	}

	@Test 
	public void CornerTest() {
		
	}
	
	@Test
	public void AttackTest() {
		
	}
}
