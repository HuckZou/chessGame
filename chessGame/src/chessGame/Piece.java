package chessGame;

import java.util.List;

//This is an abstract class for chess piece, all the pieces in the chess game
//will inherit from this class.
public abstract class Piece {

	// pieceLocation tracks the location of a piece on the game board
	// it has values from 0 to 63.
	protected int pieceLocation;
	// pieceColor tracks the color of piece the current piece is representing
	// it is used to identify if two pieces are ally pieces or enemy pieces.
	protected PieceColor pieceColor;

	Piece(int pieceLocation, PieceColor pieceColor) {
		assert (pieceLocation >= 0 && pieceLocation < Tile
				.getTotalNumTiles()) : "Piece location is out of index range of the chess board.";
		this.pieceLocation = pieceLocation;
		this.pieceColor = pieceColor;
	}

	// Different piece has different legal moves.
	// This method is to compute all possible moves for a piece given its
	// current location in the chess board.
	public abstract List<Move> computeLegalMove(Board Board);

	// This method is to define the output string for each type of pieces
	public abstract String toString();

	// This method is to define the name of the piece
	public abstract String getName();
	
	// This method is used to move a piece on a game board
	public abstract Piece movePiece(Move move);

	// This method is used to calculate the legal moves for different type of
	// pieces
	public abstract List<Move> addMovesOnDirection(Board board, List<Move> legalMoves, int rowDirection, int colDirection);
	
	// we override the default object.equals
	// this method is used to identify whether or not two pieces are equal
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Piece)) {
			return false;
		}
		Piece otherPiece = (Piece) other;
		boolean result = pieceLocation == otherPiece.getPieceLocation() && this.toString() == otherPiece.toString()
				&& pieceColor == otherPiece.getPieceColor();
		if (this instanceof Pawn) {
			return result && ((Pawn) this).isFirstMove() == ((Pawn) otherPiece).isFirstMove();
		}
		return result;
	}

	// get method for getting the color of the piece
	public PieceColor getPieceColor() {
		return this.pieceColor;
	}

	// get method for get the location of the piece
	public int getPieceLocation() {
		return this.pieceLocation;
	}
}
