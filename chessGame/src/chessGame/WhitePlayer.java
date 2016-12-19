package chessGame;

import java.util.List;

//This is the white pieces player of the chess game
public class WhitePlayer extends Player {

	public WhitePlayer(Board board, List<Move> whitePieceLegalMoves, List<Move> blackPieceLegalMoves) {
		// TODO Auto-generated constructor stub
		super(board, whitePieceLegalMoves, blackPieceLegalMoves);
	}

	@Override
	public List<Piece> getActivePiece() {
		// TODO Auto-generated method stub
		return this.board.getWhitePieces();
	}

	@Override
	public PieceColor getPlayerColor() {
		// TODO Auto-generated method stub
		return PieceColor.WHITE;
	}

	@Override
	public Player getOpponent() {
		// TODO Auto-generated method stub
		return this.board.getBlackPlayer();
	}

}
