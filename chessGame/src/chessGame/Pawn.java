package chessGame;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

	// Pawn has this private variable to track whether or not
	// this is the first time A pawn has moved.
	private boolean firstMove = true;
	
	public Pawn(int pieceLocation, PieceColor pieceColor) {
		super(pieceLocation, pieceColor);
	}

	@Override
	public List<Move> computeLegalMove(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		// Make normal moves
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, 0);
		// Make jump moves
		legalMoves = this.addMovesOnDirection(board, legalMoves, 2, 0);
		// Make attack moves
		// Attack right
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, 1);
		// Attack left
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, -1);
		return legalMoves;
	}
	
	@Override
	public List<Move> addMovesOnDirection(Board board, List<Move> legalMoves, int rowDirection, int colDirection) {
		int[] rowColIndex = Board.getRowColIndex(this.pieceLocation);
		int rowDest = rowColIndex[0] + this.getPieceColor().getDirection()*rowDirection;
		int colDest = rowColIndex[1] + colDirection;
		if(Board.isOnBoard(rowDest, colDest)) {
			int destLocation = Board.getBoardIndex(rowDest, colDest);
			Tile destTile = board.getTile(destLocation);
			//If the tile is not occupied, the piece is moving one step, and
			//the piece is not moving sideways, then it's a normal one step move.
			if(!destTile.isTileOccupied() && rowDirection == 1 && colDirection == 0) {
				legalMoves.add(new Move.PawnNormalMove(board, this, destLocation));
			}
			//If the tile is not occupied, the piece is first time moving, and
			//the piece is making a two steps, and it's not moving sideways,
			//then it's a jump move
			else if(!destTile.isTileOccupied() && this.isFirstMove() && rowDirection == 2 &&
					colDirection == 0) {
				legalMoves.add(new Move.PawnJumpMove(board, this, destLocation));
			}
			//If the tile is occupied and the color is different,
			//then the moving piece is able to capture the piece on the destination tile
			//however, we won't be able to move pass the enemy piece 
			else if(destTile.isTileOccupied() &&
					this.getPieceColor() != destTile.getPieceOnTile().getPieceColor() &&
					rowDirection == 1 && colDirection != 0){
				legalMoves.add(new Move.PawnAttackMove(board, this, destLocation, destTile.getPieceOnTile()));
			}
			else {
				//do nothing
			}
		}
		return legalMoves;
	}

	// Return whether or not the pawn has been moved before
	public boolean isFirstMove() {
		return firstMove;
	}

	// Setter method to set the pawn first move to false after being moved
	public void setFirstMoveFalse() {
		this.firstMove = false;
	}
	
	@Override
	public Pawn movePiece(Move move) {
		Pawn pawn = new Pawn(move.getDestLocation(), move.getActivePiece().getPieceColor());
		pawn.setFirstMoveFalse();
		return pawn;
	}
	
	// toString method written to override the default toString method
	// toString method is used to identify the piece from other pieces
	@Override
	public String toString() {
		return "P";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Pawn";
	}
}
