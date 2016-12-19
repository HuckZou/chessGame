package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Bishop;
import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Move;
import chessGame.Board.Builder;

public class KingTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(9, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(63, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		King king = (King) board.getTile(9).getPieceOnTile();
		assertEquals(king.computeLegalMove(board).size(), 8);
		
		Move move1 = Move.MoveFactory.createMove(board, 9, 10);
		Move move2 = Move.MoveFactory.createMove(board, 9, 18);
		Move move3 = Move.MoveFactory.createMove(board, 9, 17);
		
		assertFalse(Move.isNullMove(move1));
		assertFalse(Move.isNullMove(move2));
		assertFalse(Move.isNullMove(move3));
	}
	
	@Test
	public void BlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(0, PieceColor.BLACK));
		builder.setPiece(new Bishop(1, PieceColor.BLACK));
		//set white pieces
		builder.setPiece(new King(63, PieceColor.WHITE));
		builder.setPiece(new Bishop(8, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		King king = (King) board.getTile(0).getPieceOnTile();
		assertEquals(king.computeLegalMove(board).size(), 2);
		
		//Border blocking
		Move move1 = Move.MoveFactory.createMove(board, 0, -1);
		Move move2 = Move.MoveFactory.createMove(board, 0, 2);
		Move move3 = Move.MoveFactory.createMove(board, 0, 18);
		
		assertTrue(Move.isNullMove(move1));
		assertTrue(Move.isNullMove(move2));
		assertTrue(Move.isNullMove(move3));
		
		//Piece blocking
		Move move4 = Move.MoveFactory.createMove(board, 0, 1);
		Move move5 = Move.MoveFactory.createMove(board, 0, 8);
		
		assertTrue(Move.isNullMove(move4));
		assertFalse(Move.isNullMove(move5));
		assertTrue(move5 instanceof Move.AttackingMove);
		
	}	

}
