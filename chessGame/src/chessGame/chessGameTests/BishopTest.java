package chessGame.chessGameTests;
import org.junit.Test;
import chessGame.Board.*;
import chessGame.Bishop;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Move;
import chessGame.Pawn;
import chessGame.Board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;


public class BishopTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Bishop(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//test the total number of legal moves for Bishop
		List<Move> bishopLegalMoves = board.getTile(35).getPieceOnTile().computeLegalMove(board);
		assertEquals(bishopLegalMoves.size(), 13);

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
	public void BlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(42, PieceColor.BLACK));
		builder.setPiece(new Bishop(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//test block moves
		Move move1 = Move.MoveFactory.createMove(board, 35, 49);
		assertTrue(Move.isNullMove(move1));
		
	}
	
	@Test
	public void AttackTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Bishop(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Pawn(56, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//Test attack
		Move move1 = Move.MoveFactory.createMove(board, 35, 56);
		assertFalse(Move.isNullMove(move1));
		assertTrue(move1 instanceof Move.AttackingMove);

	}

}
