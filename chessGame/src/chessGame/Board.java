package chessGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	// Set the total number of Tiles of the chess board.
	private static int totalNumTiles = 64;
	// Set the number of Tiles per row on the chess board.
	private static int tilesPerRow = 8;
	// This variable represents the chess board in any game condition
	private List<Tile> gameBoard;
	// This variable tracks all white pieces on the chess board.
	private List<Piece> whitePieces;
	// This variable tacks all black pieces on the chess board.
	private List<Piece> blackPieces;
	// This variable represents the white piece player on the board
	private WhitePlayer whitePlayer;
	// This variable represents the black piece player on the board
	private BlackPlayer blackPlayer;
	// This variable represents the player who plays the current turn
	private Player currentPlayer;
	
	// This is the constructor for the board
	private Board(Builder builder) {
		this.gameBoard = createGameBoard(builder);
		this.whitePieces = computeNumOfPiecesRemaining(gameBoard, PieceColor.WHITE);
		this.blackPieces = computeNumOfPiecesRemaining(gameBoard, PieceColor.BLACK);

		List<Move> whitePieceLegalMoves = computeLegalMoves(this.whitePieces);
		List<Move> blackPieceLegalMoves = computeLegalMoves(this.blackPieces);

		this.whitePlayer = new WhitePlayer(this, whitePieceLegalMoves, blackPieceLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whitePieceLegalMoves, blackPieceLegalMoves);
		this.currentPlayer = builder.nextMover.choosePlayer(whitePlayer, blackPlayer);
	}

	// This method counts all the remaining pieces of a given color on the game
	// board
	// and returns a list of all the remaining Piece instances.
	private List<Piece> computeNumOfPiecesRemaining(List<Tile> gameBoard, PieceColor color) {
		List<Piece> totalPieces = new ArrayList<>();
		for (int i = 0; i < totalNumTiles; i++) {
			Piece piece = gameBoard.get(i).getPieceOnTile();
			if (piece != null && piece.getPieceColor() == color) {
				totalPieces.add(piece);
			}
		}
		return totalPieces;
	}

	// This method computes all legal moves for a given list of pieces
	// and then appends all the moves to a list.
	private List<Move> computeLegalMoves(List<Piece> pieces) {
		List<Move> legalMoves = new ArrayList<>();
		for (Piece piece : pieces) {
			legalMoves.addAll(piece.computeLegalMove(this));
		}
		return legalMoves;
	}

	// This method creates all tiles in a game board based on the given
	// game board configuration.
	private static List<Tile> createGameBoard(Builder builder) {
		List<Tile> tiles = new ArrayList<>();
		for (int i = 0; i < totalNumTiles; i++) {
			tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
		}
		return tiles;
	}

	// This subclass is for helping build the chess board
	// This is used to track all the different piece locations of
	// the board after game starts.
	public static class Builder {
		Map<Integer, Piece> boardConfig;
		PieceColor nextMover;

		public Builder() {
			this.boardConfig = new HashMap<>();
		}

		public Builder setPiece(Piece piece) {
			this.boardConfig.put(piece.getPieceLocation(), piece);
			return this;
		}

		public Builder setMover(PieceColor nextMover) {
			this.nextMover = nextMover;
			return this;
		}

		public Board build() {
			return new Board(this);
		}
	}

	// This public method setups a standard pieces layout for a
	// chess game.
	public static Board createInitialChessBoard() {
		Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new Rook(0, PieceColor.BLACK));
		builder.setPiece(new Knight(1, PieceColor.BLACK));
		builder.setPiece(new Bishop(2, PieceColor.BLACK));
		builder.setPiece(new Queen(3, PieceColor.BLACK));
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Bishop(5, PieceColor.BLACK));
		builder.setPiece(new Knight(6, PieceColor.BLACK));
		builder.setPiece(new Rook(7, PieceColor.BLACK));
		builder.setPiece(new Pawn(8, PieceColor.BLACK));
		builder.setPiece(new Pawn(9, PieceColor.BLACK));
		builder.setPiece(new Pawn(10, PieceColor.BLACK));
		builder.setPiece(new Pawn(11, PieceColor.BLACK));
		builder.setPiece(new Pawn(12, PieceColor.BLACK));
		builder.setPiece(new Pawn(13, PieceColor.BLACK));
		builder.setPiece(new Pawn(14, PieceColor.BLACK));
		builder.setPiece(new Pawn(15, PieceColor.BLACK));
		// White Layout
		builder.setPiece(new Pawn(48, PieceColor.WHITE));
		builder.setPiece(new Pawn(49, PieceColor.WHITE));
		builder.setPiece(new Pawn(50, PieceColor.WHITE));
		builder.setPiece(new Pawn(51, PieceColor.WHITE));
		builder.setPiece(new Pawn(52, PieceColor.WHITE));
		builder.setPiece(new Pawn(53, PieceColor.WHITE));
		builder.setPiece(new Pawn(54, PieceColor.WHITE));
		builder.setPiece(new Pawn(55, PieceColor.WHITE));
		builder.setPiece(new Rook(56, PieceColor.WHITE));
		builder.setPiece(new Knight(57, PieceColor.WHITE));
		builder.setPiece(new Bishop(58, PieceColor.WHITE));
		builder.setPiece(new King(60, PieceColor.WHITE));
		builder.setPiece(new Queen(59, PieceColor.WHITE));
		builder.setPiece(new Bishop(61, PieceColor.WHITE));
		builder.setPiece(new Knight(62, PieceColor.WHITE));
		builder.setPiece(new Rook(63, PieceColor.WHITE));

		// set white to move first.
		builder.setMover(PieceColor.WHITE);
		return builder.build();
	}

	// This method creates a board with our special pieces
	public static Board createInitialCrazyChessBoard() {
		Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new Rook(0, PieceColor.BLACK));
		builder.setPiece(new Knight(1, PieceColor.BLACK));
		builder.setPiece(new Bishop(2, PieceColor.BLACK));
		builder.setPiece(new Queen(3, PieceColor.BLACK));
		builder.setPiece(new King(4, PieceColor.BLACK));
		builder.setPiece(new Bishop(5, PieceColor.BLACK));
		builder.setPiece(new Knight(6, PieceColor.BLACK));
		builder.setPiece(new Rook(7, PieceColor.BLACK));
		builder.setPiece(new Hadoop(8, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(9, PieceColor.BLACK));
		builder.setPiece(new Hadoop(10, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(11, PieceColor.BLACK));
		builder.setPiece(new Hadoop(12, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(13, PieceColor.BLACK));
		builder.setPiece(new Hadoop(14, PieceColor.BLACK));
		builder.setPiece(new FlounderKnight(15, PieceColor.BLACK));
		builder.setPiece(new Pawn(16, PieceColor.BLACK));
		builder.setPiece(new Pawn(17, PieceColor.BLACK));
		builder.setPiece(new Pawn(18, PieceColor.BLACK));
		builder.setPiece(new Pawn(19, PieceColor.BLACK));
		builder.setPiece(new Pawn(20, PieceColor.BLACK));
		builder.setPiece(new Pawn(21, PieceColor.BLACK));
		builder.setPiece(new Pawn(22, PieceColor.BLACK));
		builder.setPiece(new Pawn(23, PieceColor.BLACK));
		// White Layout
		builder.setPiece(new Pawn(40, PieceColor.WHITE));
		builder.setPiece(new Pawn(41, PieceColor.WHITE));
		builder.setPiece(new Pawn(42, PieceColor.WHITE));
		builder.setPiece(new Pawn(43, PieceColor.WHITE));
		builder.setPiece(new Pawn(44, PieceColor.WHITE));
		builder.setPiece(new Pawn(45, PieceColor.WHITE));
		builder.setPiece(new Pawn(46, PieceColor.WHITE));
		builder.setPiece(new Pawn(47, PieceColor.WHITE));
		builder.setPiece(new FlounderKnight(48, PieceColor.WHITE));
		builder.setPiece(new Hadoop(49, PieceColor.WHITE));
		builder.setPiece(new FlounderKnight(50, PieceColor.WHITE));
		builder.setPiece(new Hadoop(51, PieceColor.WHITE));
		builder.setPiece(new FlounderKnight(52, PieceColor.WHITE));
		builder.setPiece(new Hadoop(53, PieceColor.WHITE));
		builder.setPiece(new FlounderKnight(54, PieceColor.WHITE));
		builder.setPiece(new Hadoop(55, PieceColor.WHITE));
		builder.setPiece(new Rook(56, PieceColor.WHITE));
		builder.setPiece(new Knight(57, PieceColor.WHITE));
		builder.setPiece(new Bishop(58, PieceColor.WHITE));
		builder.setPiece(new Queen(59, PieceColor.WHITE));
		builder.setPiece(new King(60, PieceColor.WHITE));
		builder.setPiece(new Bishop(61, PieceColor.WHITE));
		builder.setPiece(new Knight(62, PieceColor.WHITE));
		builder.setPiece(new Rook(63, PieceColor.WHITE));

		// set white to move first.
		builder.setMover(PieceColor.WHITE);
		return builder.build();
	}
	
	// This method creates a board with stalemate condition
	public static Board createStalemateChessBoard() {
		Builder builder = new Builder();
		// Black Layout
		builder.setPiece(new King(7, PieceColor.BLACK));
		// White Layout
		builder.setPiece(new King(13, PieceColor.WHITE));
		builder.setPiece(new Queen(30, PieceColor.WHITE));
		
		// set white to move first.
		builder.setMover(PieceColor.WHITE);
		return builder.build();
	}
	
	// This method creates a board with stalemate condition
		public static Board createCheckmateChessBoard() {
			Builder builder = new Builder();
			//set black pieces
			builder.setPiece(new King(4, PieceColor.BLACK));
			
			//set white pieces
			builder.setPiece(new King(63, PieceColor.WHITE));
			builder.setPiece(new Rook(1, PieceColor.WHITE));
			builder.setPiece(new Rook(7, PieceColor.WHITE));
			
			// set white to move first.
			builder.setMover(PieceColor.WHITE);
			return builder.build();
	}
	
	// This class prints the game board every time players make a move
	// This class uses StringBuilder object in java to help print the
	// game board in a string of characters.
	public String printBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < totalNumTiles; i++) {
			String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s", tileText));
			if ((i + 1) % tilesPerRow == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}

	// Get method for the tile of the game board based on a given tile index
	public Tile getTile(int tileLocation) {
		return gameBoard.get(tileLocation);
	}

	// Get method for the total number of tiles on the game board
	public static int getTotalNumTiles() {
		return totalNumTiles;
	}

	// Get method for all white pieces
	public List<Piece> getWhitePieces() {
		return whitePieces;
	}

	// Get method for all black pieces
	public List<Piece> getBlackPieces() {
		return blackPieces;
	}

	// Get method for white player
	public Player getWhitePlayer() {
		return whitePlayer;
	}

	// Get method for black player
	public Player getBlackPlayer() {
		return blackPlayer;
	}

	// Get method for current turn player
	public Player getCurrentPlayer() {
		// TODO Auto-generated method stub
		return currentPlayer;
	}

	// Get method for getting all legal moves for white pieces
	public List<Move> getWhiteLegalMoves() {
		return whitePlayer.getLegalMoves();
	}

	// Get method for getting all legal moves for black pieces
	public List<Move> getBlackLegalMoves() {
		return blackPlayer.getLegalMoves();
	}

	// Get method for getting all legal moves for both players
	public List<Move> getAllLegalMoves() {
		List<Move> allLegalMoves = new ArrayList<>();
		allLegalMoves.addAll(getWhiteLegalMoves());
		allLegalMoves.addAll(getBlackLegalMoves());
		return allLegalMoves;
	}

	// This method determines whether or not the destination index is
	// out of legal range.
	public boolean isLegalMove(int destination) {
		return destination >= 0 && destination < totalNumTiles;
	}

	// this method finds the column index given the tileLocation.
	// column 1 - column 8 are represented by 0 - 7 respectively
	public static int getColumnNum(int tileLocation) {
		return tileLocation % 8;
	}

	// this method finds the row index given the tileLocation.
	// row 1 - row 8 are represented by 0 - 7 respectively
	public static int getRowNum(int tileLocation) {
		return tileLocation / 8;
	}

	// this method converts the board index into corresponding row and column 
	// coordinate on the board
	public static int[] getRowColIndex(int location) {
		return new int[]{getRowNum(location), getColumnNum(location)};
	}
	
	// this method takes a row index and a col index, and returns 
	// its corresponding board index
	public static int getBoardIndex(int rowIndex, int colIndex) {
		return 8 * rowIndex + colIndex;
	}
	
	// this method takes in the current row and col coordinate of the piece
	// and determine whether or not it's off the board
	public static boolean isOnBoard(int rowIndex, int colIndex) {
		return (rowIndex >= 0 && rowIndex < 8) && (colIndex >= 0 && colIndex < 8);
	}
}
