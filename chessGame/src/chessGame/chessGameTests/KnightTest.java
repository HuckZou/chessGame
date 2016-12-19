package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Knight;
import chessGame.Move;
import chessGame.Board.Builder;

public class KnightTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Knight(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Knight knight = (Knight) board.getTile(35).getPieceOnTile();
		assertEquals(knight.computeLegalMove(board).size(), 8);
		
		Move move1 = Move.MoveFactory.createMove(board, 35, 18);
		Move move2 = Move.MoveFactory.createMove(board, 35, 20);
		Move move3 = Move.MoveFactory.createMove(board, 35, 52);
		
		assertFalse(Move.isNullMove(move1));
		assertFalse(Move.isNullMove(move2));
		assertFalse(Move.isNullMove(move3));
	}
	
	@Test
	public void BlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(46, PieceColor.BLACK));
		builder.setPiece(new Knight(63, PieceColor.BLACK));
		//set white pieces
		builder.setPiece(new King(22, PieceColor.WHITE));
		builder.setPiece(new Knight(53, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Knight Knight = (Knight) board.getTile(63).getPieceOnTile();
		assertEquals(Knight.computeLegalMove(board).size(), 1);
		
		//Border blocking
		Move move1 = Move.MoveFactory.createMove(board, 63, 73);
		//Piece blocking
		Move move2 = Move.MoveFactory.createMove(board, 63, 46);
		Move move3 = Move.MoveFactory.createMove(board, 63, 53);
		
		assertTrue(Move.isNullMove(move1));
		assertTrue(Move.isNullMove(move2));
		assertFalse(Move.isNullMove(move3));
	
		assertTrue(move3 instanceof Move.AttackingMove);
	}

}
