package chessGame;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
	
	public Bishop(int pieceLocation, PieceColor pieceColor) {
		super(pieceLocation, pieceColor);
	}

	@Override
	public List<Move> computeLegalMove(Board board) {
		// This list variable is used to store all the legal moves of the piece
		List<Move> legalMoves = new ArrayList<>();
		//First let's move northwest
		legalMoves = addMovesOnDirection(board, legalMoves, -1, -1);
		//Second let's move northeast
		legalMoves = addMovesOnDirection(board, legalMoves, -1, 1);
		//Third let's move southeast
		legalMoves = addMovesOnDirection(board, legalMoves, 1, 1);
		//Fourth let's move southwest
		legalMoves = addMovesOnDirection(board, legalMoves, 1, -1);		
		return legalMoves;
	}
	
	// calculate all the legal moves given a direction of moving
	// the direction is determined by the rowDirection and colDirection 
	// that are passed in as arguments.
	@Override
	public List<Move> addMovesOnDirection(Board board, List<Move> legalMoves, int rowDirection, int colDirection) {
		int[] rowColIndex = Board.getRowColIndex(this.pieceLocation);
		int rowDest = rowColIndex[0] + rowDirection;
		int colDest = rowColIndex[1] + colDirection;
		while(Board.isOnBoard(rowDest, colDest)) {
			int destLocation = Board.getBoardIndex(rowDest, colDest);
			Tile destTile = board.getTile(destLocation);
			//If the tile is not occupied, then it's legal and safe to move there
			if(!destTile.isTileOccupied())
				legalMoves.add(new Move.NormalMove(board, this, destLocation));
			//If the tile is occupied with a piece that has the same color as the moving piece
			//then it is not a legal move and the moving piece will get blocked.
			else if(this.getPieceColor() == destTile.getPieceOnTile().getPieceColor()) {
				break;
			}
			//If the tile is occupied and the color is different,
			//then the moving piece is able to capture the piece on the destination tile
			//however, we won't be able to move pass the enemy piece 
			else{
				legalMoves.add(new Move.AttackingMove(board, this, destLocation, destTile.getPieceOnTile()));
				break;
			}
			rowDest += rowDirection;
			colDest += colDirection;
		}
		return legalMoves;
	}
	
	@Override
	public Bishop movePiece(Move move) {
		return new Bishop(move.getDestLocation(), move.getActivePiece().getPieceColor());
	}
	
	// toString method written to override the default toString method
	// toString method is used to identify the piece from other pieces
	@Override
	public String toString() {
		return "B";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Bishop";
	}

}
