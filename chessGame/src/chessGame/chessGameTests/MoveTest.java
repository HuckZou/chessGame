package chessGame.chessGameTests;

import static org.junit.Assert.*;

import org.junit.Test;

import chessGame.Board;
import chessGame.PieceColor;
import chessGame.King;
import chessGame.Knight;
import chessGame.Move;
import chessGame.Board.Builder;

public class MoveTest {

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
		
		
		//valid move
		Move move1 = Move.MoveFactory.createMove(board, 4, 12);
		assertFalse(Move.isNullMove(move1));
		
		//invalid move
		Move move2 = Move.MoveFactory.createMove(board, 4, 14);
		assertTrue(Move.isNullMove(move2));
		
		//test execute
		board = move1.execute();
		assertTrue(board.getTile(12).getPieceOnTile() instanceof King);
	}

}
