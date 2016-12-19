package chessGame;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
	
	public King(int pieceLocation, PieceColor pieceColor) {
		super(pieceLocation, pieceColor);
	}

	// calculate all the legal moves given a direction of moving
	// the direction is determined by the rowDirection and colDirection 
	// that are passed in as arguments.
	@Override
	public List<Move> computeLegalMove(Board board) {
		List<Move> legalMoves = new ArrayList<>();
		//Move North
		legalMoves = this.addMovesOnDirection(board, legalMoves, -1, 0);
		//Move Northeast
		legalMoves = this.addMovesOnDirection(board, legalMoves, -1, 1);
		//Move East
		legalMoves = this.addMovesOnDirection(board, legalMoves, 0, 1);
		//Move Southeast
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, 1);
		//Move South
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, 0);
		//Move Southwest
		legalMoves = this.addMovesOnDirection(board, legalMoves, 1, -1);
		//Move West
		legalMoves = this.addMovesOnDirection(board, legalMoves, 0, -1);
		//Move Northwest
		legalMoves = this.addMovesOnDirection(board, legalMoves, -1, -1);
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
	public King movePiece(Move move) {
		return new King(move.getDestLocation(), move.getActivePiece().getPieceColor());
	}
	
	// toString method written to override the default toString method
	// toString method is used to identify the piece from other pieces
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "K";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "King";
	}

}
