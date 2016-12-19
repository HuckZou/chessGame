package chessGame;

import chessGame.Piece;
import java.util.HashMap;
import java.util.Map;

/* This is an abstract class used for representing the tiles in the chess board.
 * Two classes are implemented: EmptyTile class and OccupiedTile class
 * Both EmptyTile class and OccupiedTile class are declared as static 
 * so that we can enable the immutable feature in Java. More concisely, 
 * the instances created by these two classes can be reused to reduce memory
 * and garbage collection requirement.
 */
public abstract class Tile {
	// The variable tileLocation is set to be protected so that it can only
	// be accessed within the class.
	// The variable tileLocation is set also to be so that it can only
	// be set once and after that it cannot be changed.
	protected int tileLocation;
	// This variable is set so that we could change number of tiles on the
	// chess board later.
	protected static int totalNumTiles = Board.getTotalNumTiles();
	// A HashMap is created to store all empty tiles that may be used in the
	// chess game.
	private static Map<Integer, EmptyTile> EMPTY_TILES = createAllEmptyTiles(totalNumTiles);

	// The constructor for Tile class
	private Tile(int tileLocation) {
		assert (tileLocation >= 0) : "The location index for" + "tile has to be greater or equal to zero";
		this.tileLocation = tileLocation;
	}

	public abstract boolean isTileOccupied();

	public abstract Piece getPieceOnTile();

	public abstract String toString();

	// This function initializes all possible empty tiles and map it to all
	// tiles in the game.
	private static Map<Integer, EmptyTile> createAllEmptyTiles(int totalNumTiles) {
		Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

		for (int i = 0; i < totalNumTiles; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return emptyTileMap;
	}

	// Get the total number of tiles in the game board
	public static int getTotalNumTiles() {
		return totalNumTiles;
	}

	public int getTileLocation() {
		return this.tileLocation;
	}
	// This function create a tile as requested.
	// If an empty tile needs to be created,
	// it returns our cached EmptyTile instance
	// If a non-empty tile needs to be created, it returns a new instance of
	// OccupiedTile instance
	public static Tile createTile(int tileLocation, Piece assignedPiece) {
		if (assignedPiece == null) {
			return EMPTY_TILES.get(tileLocation);
		} else {
			return new OccupiedTile(tileLocation, assignedPiece);
		}
	}

	// This is the class for representing an empty tile
	// where no chess piece is present.
	public static class EmptyTile extends Tile {

		private EmptyTile(int tileLocation) {
			super(tileLocation);
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean isTileOccupied() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Piece getPieceOnTile() {
			// TODO Auto-generated method stub
			return null;
		}

		// this method is to used to represent empty tiles on
		// a printed out game board. It marks an empty tile as
		// a letter 'X'
		@Override
		public String toString() {
			return "X";
		}
	}

	// This is the class for representing an occupied tile
	// where one kind of chess piece is present
	public static class OccupiedTile extends Tile {

		// Variable currentPiece is declared as private so that
		// it cannot be changed later.
		private Piece currentPiece;

		private OccupiedTile(int tileLocation, Piece assignedPiece) {
			super(tileLocation);
			this.currentPiece = assignedPiece;
		}

		@Override
		public boolean isTileOccupied() {
			return true;
		}

		// get method for getting the piece on the tile
		@Override
		public Piece getPieceOnTile() {
			return currentPiece;
		}

		// This method is to used to print out the occupied tile and identify
		// the type of piece on a tile is.
		// Also, it prints out black pieces as lower case
		// letters and .white pieces as upper case letters.
		@Override
		public String toString() {
			return currentPiece.getPieceColor().isBlack() ? currentPiece.toString().toLowerCase()
					: currentPiece.toString();
		}
	}
}