package chessGame;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

	public Rook(int pieceLocation, PieceColor pieceColor) {
		super(pieceLocation, pieceColor);
	}

	@Override
	public List<Move> computeLegalMove(Board board) {
		// This list variable is used to store all the legal moves of the piece
		List<Move> legalMoves = new ArrayList<>();
		
		// Move North
		legalMoves = addMovesOnDirection(board, legalMoves, -1, 0);
		// Move East
		legalMoves = addMovesOnDirection(board, legalMoves, 0, 1);
		// Move South
		legalMoves = addMovesOnDirection(board, legalMoves, 1, 0);
		// Move West
		legalMoves = addMovesOnDirection(board, legalMoves, 0, -1);
		return legalMoves;
	}
	
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
	public Rook movePiece(Move move) {
		return new Rook(move.getDestLocation(), move.getActivePiece().getPieceColor());
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Rook";
	}
}
