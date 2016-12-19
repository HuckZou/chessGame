package chessGame.chessGameTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


import chessGame.Board;
import chessGame.King;
import chessGame.Move;
import chessGame.PieceColor;
import chessGame.Board.Builder;
import chessGame.FlounderKnight;

public class FlounderKingTest {

	@Test
	public void NonBlockingTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//test the total number of legal moves for Bishop
		List<Move> legalMoves = board.getTile(35).getPieceOnTile().computeLegalMove(board);
		assertEquals(legalMoves.size(), 4);

		//test illegal moves
		Move move1 = Move.MoveFactory.createMove(board, 35, 34);
		assertTrue(Move.isNullMove(move1));
		Move move2 = Move.MoveFactory.createMove(board, 35, 43);
		assertTrue(Move.isNullMove(move2));
		
		//test legal moves
		Move move3 = Move.MoveFactory.createMove(board, 35, 42);
		assertFalse(Move.isNullMove(move3));
	}
	
	@Test
	public void BlockingTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(1, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(0, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//test the total number of legal moves for Bishop
		List<Move> legalMoves = board.getTile(0).getPieceOnTile().computeLegalMove(board);
		assertEquals(legalMoves.size(), 1);

		//test legal moves
		Move move3 = Move.MoveFactory.createMove(board, 0, 9);
		assertFalse(Move.isNullMove(move3));
	}

}
