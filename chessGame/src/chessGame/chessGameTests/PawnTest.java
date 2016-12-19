package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Move;
import chessGame.Pawn;
import chessGame.Board.Builder;

public class PawnTest {

	@Test
	public void NonBlockTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Pawn(35, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Pawn(62, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		Pawn pawn= (Pawn) board.getTile(35).getPieceOnTile();
		assertEquals(pawn.computeLegalMove(board).size(), 2);
		
		//test normal move
		Move move1 = Move.MoveFactory.createMove(board, 35, 43);
		//test jump move
		Move move2 = Move.MoveFactory.createMove(board, 35, 51);
		
		assertFalse(Move.isNullMove(move1));
		assertFalse(Move.isNullMove(move2));
		assertTrue(move1 instanceof Move.PawnNormalMove);
		assertTrue(move2 instanceof Move.PawnJumpMove);
		//test illegal moves
		//test attacking empty tile
		Move move3 = Move.MoveFactory.createMove(board, 35, 42);
		//test move backwards one step
		Move move4 = Move.MoveFactory.createMove(board, 35, 27);
		//test move backwards two steps
		Move move5 = Move.MoveFactory.createMove(board, 35, 19);
		
		assertTrue(Move.isNullMove(move3));
		assertTrue(Move.isNullMove(move4));
		assertTrue(Move.isNullMove(move5));
	}
	
	@Test 
	public void JumpMoveTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Pawn(0, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Pawn(62, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//test jump move
		Move move1 = Move.MoveFactory.createMove(board, 0, 16);
		board = move1.execute();
		Move move2 = Move.MoveFactory.createMove(board, 57, 58);
		board = move2.execute();
		//can't jump twice
		Move move3 = Move.MoveFactory.createMove(board, 16, 32);
		assertTrue(Move.isNullMove(move3));
	}
	
	@Test
	public void AttackMoveTest() {
		Builder builder = new Builder();
		//set black pieces
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Pawn(0, PieceColor.BLACK));
		
		//set white pieces
		builder.setPiece(new King(57, PieceColor.WHITE));
		builder.setPiece(new Pawn(9, PieceColor.WHITE));
		
		builder.setMover(PieceColor.BLACK);
		Board board = builder.build();
		
		//Test attack move
		Move move1 = Move.MoveFactory.createMove(board, 0, 9);
		assertFalse(Move.isNullMove(move1));
		assertTrue(move1 instanceof Move.PawnAttackMove);
		board = move1.execute();
		assertTrue(board.getTile(9).getPieceOnTile().getPieceColor().equals(PieceColor.BLACK));
	}
	
	
}
