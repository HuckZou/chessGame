package chessGame;

import java.util.ArrayList;
import java.util.List;

import chessGame.Board.Builder;

//This class implements all the moves of chess pieces
//Two subclasses will be implemented: NormalMove and AttackingMove
public abstract class Move {
	Board board;
	protected Piece activePiece;
	protected int destLocation;
	protected int currentLocation;
	public static Move NULL_MOVE = new NullMove();

	// Constructor for building the Move object
	Move(Board board, Piece activePiece, int destLocation) {
		this.board = board;
		this.activePiece = activePiece;
		this.destLocation = destLocation;
		this.currentLocation = activePiece.getPieceLocation();
	}

	// Constructor for building the NullMove
	Move(Board board, int destLocation) {
		this.board = board;
		this.activePiece = null;
		this.destLocation = destLocation;
		this.currentLocation = -1;
	}

	// we override the default Object.equals method to test whether two moves
	// are the same
	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Move)) {
			return false;
		}
		Move otherMove = (Move) other;
		return getDestLocation() == otherMove.getDestLocation() && getActivePiece().equals(otherMove.getActivePiece());
	}

	// execute method executes a move based on the current board and returns
	// a new board after the move is executed or returns the current board if
	// the move is not legal to execute.
	public Board execute() {
		// TODO Auto-generated method stub
		Builder builder = new Builder();
		List<Piece> currentPlayerPieces = this.board.getCurrentPlayer().getActivePiece();
		List<Piece> opponentPlayerPieces = this.board.getCurrentPlayer().getOpponent().getActivePiece();
		List<Piece> allActivePieces = new ArrayList<>();
		allActivePieces.addAll(currentPlayerPieces);
		allActivePieces.addAll(opponentPlayerPieces);
		for (Piece piece : allActivePieces) {
			if (!this.activePiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		builder.setPiece(this.activePiece.movePiece(this));
		builder.setMover(this.board.getCurrentPlayer().getOpponent().getPlayerColor());
		return builder.build();
	}

	// Class for NormalMove. It is to represent a move
	// where the activePiece is moving to an empty tile
	public static class NormalMove extends Move {

		public NormalMove(Board board, Piece activePiece, int destLocation) {
			super(board, activePiece, destLocation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean equals(Object other) {
			return this == other || other instanceof NormalMove && super.equals(other);
		}

	}

	// Class for AttackingMove where the activePiece is attacking the opponent's
	// piece on the destination tile.
	public static class AttackingMove extends Move {

		Piece beingAttackedPiece;

		public AttackingMove(Board board, Piece activePiece, int destLocation, Piece beingAttackedPiece) {
			super(board, activePiece, destLocation);
			this.beingAttackedPiece = beingAttackedPiece;
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
			if (!(other instanceof AttackingMove))
				return false;
			AttackingMove otherAttackMove = (AttackingMove) other;
			return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
		}

		// In attacking move, isAttack returns true.
		@Override
		public boolean isAttack() {
			return true;
		}

		// Get method for getting the piece that is being attacked.
		@Override
		public Piece getAttackedPiece() {
			return this.beingAttackedPiece;
		}
	}

	// Class for handling Pawn moves
	public static class PawnNormalMove extends Move {
		public PawnNormalMove(Board board, Piece activePiece, int destLocation) {
			super(board, activePiece, destLocation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean equals(Object other) {
			return this == other || other instanceof PawnNormalMove && super.equals(other);
		}
	}

	// Class for handling Pawn attack moves
	public static class PawnAttackMove extends AttackingMove {
		public PawnAttackMove(Board board, Piece activePiece, int destLocation, Piece beingAttackedPiece) {
			super(board, activePiece, destLocation, beingAttackedPiece);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean equals(Object other) {
			return this == other || other instanceof PawnAttackMove && super.equals(other);
		}
	}

	// Class for handling Pawn Jump moves
	public static class PawnJumpMove extends Move {
		public PawnJumpMove(Board board, Piece activePiece, int destLocation) {
			super(board, activePiece, destLocation);
		}

		@Override
		public boolean equals(Object other) {
			return this == other || other instanceof PawnJumpMove && super.equals(other);
		}
	}

	// Class for handling NullMove, nullmove is generated when the players enter
	// illegal moves.
	public static class NullMove extends Move {
		public NullMove() {
			super(null, -1);
		}

		// illegal moves cannot be executed.
		@Override
		public Board execute() {
			throw new RuntimeException("this move cannot be executed.");
		}

		@Override
		public String toString() {
			return "Null Move";
		}
	}

	// static methods for creating player-specified moves
	// it contains methods for creating moves for both normal moves
	// and escape moves in case of in-check condition.
	public static class MoveFactory {
		// this creates a normal move when there is not in-check condition
		public static Move createMove(Board board, int currentLocation, int destLocation) {
			List<Move> moves = board.getCurrentPlayer().getLegalMoves();
			return createMove(board, currentLocation, destLocation, moves);
		}

		// this creates an escape move when there is an in-check condition
		public static Move createEscapeMove(Board board, int currentLocation, int destLocation) {
			List<Move> escapeMoves = board.getCurrentPlayer().getEscapeMoves();
			return createMove(board, currentLocation, destLocation, escapeMoves);

		}
		
		// this is a helper function for user to input coordinates instead of board index
		public static Move createCoordinateMove(Board board, int currRow, int currCol, int destRow, int destCol) {
			int currentLocation = Board.getBoardIndex(currRow, currCol);
			int destLocation = Board.getBoardIndex(destRow, destCol);
			return createMove(board, currentLocation, destLocation);
		}

		// this is a helper function for user to input coordinates instead of board index
		public static Move createCoordinateEscapeMove(Board board, int currRow, int currCol, int destRow, int destCol) {
			int currentLocation = Board.getBoardIndex(currRow, currCol);
			int destLocation = Board.getBoardIndex(destRow, destCol);
			return createEscapeMove(board, currentLocation, destLocation);
		}		
		
		// wrapper method for creating the above two kinds of moves.
		private static Move createMove(Board board, int currentLocation, int destLocation, List<Move> moveList) {
			for (Move move : moveList) {
				if (move.getCurrentLocation() == currentLocation && move.getDestLocation() == destLocation) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}

	// This method is to determine whether a move is NULL_MOVE or not
	public static boolean isNullMove(Move move) {
		return move.equals(NULL_MOVE);
	}
	
	// In general, normal moves are not attacking moves.
	// This method return false.
	public boolean isAttack() {
		return false;
	}

	// In general, normal moves do not attack other pieces
	// This method return null.
	public Piece getAttackedPiece() {
		return null;
	}

	// Get method for getting the piece that will be moved.
	public Piece getActivePiece() {
		return this.activePiece;
	}

	// Get method for getting the destination of where the
	// active piece is moving to.
	public int getDestLocation() {
		return this.destLocation;
	}

	// Get method for getting the current location of the
	// active piece.
	public int getCurrentLocation() {
		return this.currentLocation;
	}
	
}
