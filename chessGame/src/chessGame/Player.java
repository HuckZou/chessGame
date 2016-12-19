package chessGame;

import java.util.ArrayList;
import java.util.List;

//Abstract class for Player object, the class defines the basic behavior for
//both the black player and white player
public abstract class Player {

	// Each player has four privates fields
	// an bard object is passed into player so that
	// the player knows which board it's playing on
	protected Board board;
	// Each player needs to keep track of its king
	// It's used to determine game-ending conditions
	private King king;
	// A list of moves that a player is able to perform
	private List<Move> legalMoves;
	// a boolean value indicates whether or not this player
	// is put in check by its opponent
	private boolean isInCheck;
	// an integer value to track how many points has this player scored
	private int playerScore;
	// a string that represents the name of the chess player
	private String playerName;

	public Player(Board board, List<Move> legalMoves, List<Move> opponentMoves) {
		this.board = board;
		this.legalMoves = legalMoves;
		this.king = getKingPiece();
		this.isInCheck = getIsInCheck(opponentMoves);
	}

	// abstract methods for each player to implement
	public abstract List<Piece> getActivePiece();

	public abstract PieceColor getPlayerColor();

	public abstract Player getOpponent();

	// public method to get king piece from the board
	public King getKingPiece() {
		// TODO Auto-generated method stub
		List<Piece> activePieces = getActivePiece();
		for (Piece activePiece : activePieces) {
			if (activePiece instanceof King) {
				return (King) activePiece;
			}
		}
		throw new RuntimeException("There is no king on the game board.");
	}

	// get the king piece of the player
	public King getKing() {
		return this.king;
	}

	// get all legal moves of the player is able to perform
	public List<Move> getLegalMoves() {
		return this.legalMoves;
	}

	// helper method to determine whether or not a move is
	// legal for the player to perform
	public boolean isLegalMove(Move move) {
		return this.legalMoves.contains(move);
	}

	// get method for getting the in-check condition for the player
	public boolean isInCheck() {
		return this.isInCheck;
	}

	// get method for getting the check mate condition for the player
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	// get method for getting the stale mate condition for the player
	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}

	// get method for getting the player's name
	public String getPlayerName() {
		return playerName;
	}
	// set method for setting the player's name
	public void setPlayerName(String name) {
		this.playerName = name;
	}
	// get method for getting the player's current score
	public int getPlayerScore() {
		return this.playerScore;
	}
	// set method for setting the score for the player
	public void setPlayerScore(int score) {
		this.playerScore = score;
	}
	
	// helper method to get all possible escape moves for a piece
	public List<Move> getEscapeMoves() {
		List<Move> escapeMoves = new ArrayList<>();
		for (Move move : this.legalMoves) {
			if (canMoveEscape(move)) {
				escapeMoves.add(move);
			}
		}
		return escapeMoves;
	}

	// private helper method to get a boolean value of whether or not
	// the player is able to rescue the king
	private boolean hasEscapeMoves() {
		// TODO Auto-generated method stub
		return !getEscapeMoves().isEmpty();
	}

	// private helper method for making a move on a temporary game board
	// and returns the result of whether or not the move is good enough
	// to make the king survive
	private boolean canMoveEscape(Move move) {
		Board tempBoard = move.execute();
		List<Move> kingAttacks = Player.computeAttacksOnTile(computeKingLocationOnTempBoard(tempBoard),
				tempBoard.getCurrentPlayer().getLegalMoves());
		return kingAttacks.isEmpty();
	}

	// private wrapper method for computing the location of king
	// on a temporary game board.
	private int computeKingLocationOnTempBoard(Board tempBoard) {
		return tempBoard.getCurrentPlayer().getOpponent().getKing().getPieceLocation();
	}
	
	// private wrapper method for finding out whether or not the player is in
	// check condition.
	private boolean getIsInCheck(List<Move> opponentMoves) {
		return !Player.computeAttacksOnTile(this.king.getPieceLocation(), opponentMoves).isEmpty();
	}
	
	// private helper method to determine whether or not
	// there are possible attack moves on the king.
	private static List<Move> computeAttacksOnTile(int pieceLocation, List<Move> opponentMoves) {
		// TODO Auto-generated method stub
		List<Move> attackMoves = new ArrayList<>();
		for (Move move : opponentMoves) {
			if (pieceLocation == move.getDestLocation()) {
				attackMoves.add(move);
			}
		}
		return attackMoves;
	}
}
