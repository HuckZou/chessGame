package chessGame;

import chessGame.Move;

import java.util.ArrayList;
import java.util.List;

//This class implements the class for Knight chess piece
public class Knight extends Piece {

	// constructor for knight class
	public Knight(int pieceLocation, PieceColor pieceColor) {
		super(pieceLocation, pieceColor);
	}

	@Override
	public List<Move> computeLegalMove(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		// Move Northeast high
		legalMoves = this.addMovesOnDirection(board, legalMoves, -2, 1);
		// Move Northeast low
		legalMoves = this.addMovesOnDirection(board, legalMoves, -1, 2);
		//Move Southeast high
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, 2);
		//Move Southeast low
		legalMoves = this.addMovesOnDirection(board, legalMoves, 2, 1);
		//Move Southwest high
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, -2);
		//Move Southwest low
		legalMoves = this.addMovesOnDirection(board, legalMoves, 2, -1);
		//Move Northwest high
		legalMoves = this.addMovesOnDirection(board, legalMoves, -2, -1);
		//Move Northwest low
		legalMoves = this.addMovesOnDirection(board, legalMoves, -1, -2);
		return legalMoves;
	}

	@Override
	public List<Move> addMovesOnDirection(Board board, List<Move> legalMoves, int rowDirection, int colDirection) {
		int[] rowColIndex = Board.getRowColIndex(this.pieceLocation);
		int rowDest = rowColIndex[0] + rowDirection;
		int colDest = rowColIndex[1] + colDirection;
		if(Board.isOnBoard(rowDest, colDest)) {
			int destLocation = Board.getBoardIndex(rowDest, colDest);
			Tile destTile = board.getTile(destLocation);
			//If the tile is not occupied, then it's legal and safe to move there
			if(!destTile.isTileOccupied())
				legalMoves.add(new Move.NormalMove(board, this, destLocation));
			//If the tile is occupied with a piece that has the same color as the moving piece
			//then it is not a legal move and the moving piece will get blocked.
			else if(this.getPieceColor() == destTile.getPieceOnTile().getPieceColor()) {
				//do nothing
			}
			//If the tile is occupied and the color is different,
			//then the moving piece is able to capture the piece on the destination tile
			//however, we won't be able to move pass the enemy piece 
			else{
				legalMoves.add(new Move.AttackingMove(board, this, destLocation, destTile.getPieceOnTile()));
			}
		}
		return legalMoves;
	}
	
	@Override
	public Knight movePiece(Move move) {
		return new Knight(move.getDestLocation(), move.getActivePiece().getPieceColor());
	}
	
	// toString method written to override the default toString method
	// toString method is used to identify the piece from other pieces
	@Override
	public String toString() {
		return "N";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Knight";
	}
}
