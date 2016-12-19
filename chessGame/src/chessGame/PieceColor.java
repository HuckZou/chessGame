package chessGame;

//This class is used to identify two players.
//White chess piece will start at the bottom of the chess board
//Black chess piece will start at the top of the chess board
public enum PieceColor {
	WHITE {
		// White pieces are placed on the bottom of the game board
		// White pieces move up, so the direction is -1.
		public int getDirection() {
			return -1;
		}

		public boolean isWhite() {
			return true;
		}

		public boolean isBlack() {
			return false;
		}

		@Override
		public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
			// TODO Auto-generated method stub
			return whitePlayer;
		}

		@Override
		public String toString() {
			return "white";
		}
	},
	BLACK {
		// Black pieces are placed on the top of the game board
		// Black pieces move up, so the direction is 1.
		public int getDirection() {
			return 1;
		}

		public boolean isWhite() {
			return false;
		}

		public boolean isBlack() {
			return true;
		}

		@Override
		public Player choosePlayer(Player whitePlayer, Player blackPlayer) {
			// TODO Auto-generated method stub
			return blackPlayer;
		}

		@Override
		public String toString() {
			return "black";
		}
	};

	// Abstract methods for enums to implement.
	public abstract int getDirection();

	public abstract boolean isWhite();

	public abstract boolean isBlack();

	public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);
}
